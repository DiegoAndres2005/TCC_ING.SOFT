/**
 * Lógica del Sistema de Inventario y Ventas (Simulación Local)
 *
 * NOTA: Esta versión utiliza arrays de JavaScript para simular la base de datos (CRUD local).
 * Reemplaza estas funciones con llamadas a tu API/Base de Datos (Firestore, MongoDB, etc.)
 * cuando implementes tu backend.
 */

// --- Variables de Estado y Datos Similados ---
let inventoryItems = [
  {
    id: "1",
    name: "Laptop Pro X",
    quantity: 25,
    price: 1200.0,
    supplierId: "101", // Nuevo: ID del proveedor
    status: "Disponible", // NUEVO: Estado del producto
    updatedAt: new Date().toISOString(),
  },
  {
    id: "2",
    name: "Mouse Inalámbrico",
    quantity: 8,
    price: 25.5,
    supplierId: "102", // Nuevo: ID del proveedor
    status: "Bajo Pedido", // NUEVO: Estado del producto
    updatedAt: new Date().toISOString(),
  },
  {
    id: "3",
    name: 'Monitor Curvo 27"',
    quantity: 15,
    price: 350.99,
    supplierId: "101", // Nuevo: ID del proveedor
    status: "Disponible", // NUEVO: Estado del producto
    updatedAt: new Date().toISOString(),
  },
  {
    id: "4",
    name: "Cable USB-C a Lightning",
    quantity: 150,
    price: 5.99,
    supplierId: "103", // Nuevo: ID del proveedor
    status: "Disponible", // NUEVO: Estado del producto
    updatedAt: new Date().toISOString(),
  },
  {
    id: "5",
    name: "Webcam HD",
    quantity: 5,
    price: 45.0,
    supplierId: "102", // Nuevo: ID del proveedor
    status: "Descontinuado", // NUEVO: Estado del producto
    updatedAt: new Date().toISOString(),
  },
];

let salesHistory = [
  {
    id: "s1",
    itemId: "1",
    itemName: "Laptop Pro X",
    quantitySold: 1,
    unitPrice: 1200.0,
    totalRevenue: 1200.0,
    timestamp: new Date(Date.now() - 3600000).toISOString(),
  },
  {
    id: "s2",
    itemId: "3",
    itemName: 'Monitor Curvo 27"',
    quantitySold: 2,
    unitPrice: 350.99,
    totalRevenue: 701.98,
    timestamp: new Date(Date.now() - 7200000).toISOString(),
  },
];

// --- NUEVA DATA SIMULADA DE PROVEEDORES ---
let suppliers = [
  {
    id: "101",
    name: "Global Tech S.A.",
    contact: "Juan Pérez",
    phone: "555-1234",
    email: "contacto@globaltech.com",
  },
  {
    id: "102",
    name: "ElectroFast Ltda.",
    contact: "María Gómez",
    phone: "555-5678",
    email: "ventas@electrofast.net",
  },
  {
    id: "103",
    name: "Accesorios Top",
    contact: "Carlos Ruiz",
    phone: "555-9012",
    email: "carlos.ruiz@topacc.com",
  },
];

let currentEditingId = null;
let currentEditingSupplierId = null; // Nuevo: Para edición de proveedores
let isSidebarOpen = true;
const LOW_STOCK_THRESHOLD = 10;
let nextInventoryId = 6;
let nextSaleId = 3;
let nextSupplierId = 104; // Nuevo: Siguiente ID de proveedor
// Chart instances
let revenueChart = null;
let productPieChart = null;

// --- Funciones de Utilidad (Modals) ---

const showMessage = (title, message) => {
  const modal = document.getElementById("messageModal");
  document.getElementById("modalTitle").textContent = title;
  document.getElementById("modalMessage").textContent = message;
  modal.classList.remove("hidden");
};

let resolveConfirmation;
const confirmationModal = document.getElementById("confirmationModal");
const showConfirmation = (title, message) => {
  document.getElementById("confirmationTitle").textContent = title;
  document.getElementById("confirmationMessage").textContent = message;
  confirmationModal.classList.remove("hidden");
  return new Promise((resolve) => {
    resolveConfirmation = resolve;
  });
};

// --- 1. Lógica de Navegación y Sidebar ---

const navigateTo = (sectionId) => {
  const sections = document.querySelectorAll(".app-section");
  sections.forEach((section) => {
    section.classList.add("hidden");
  });
  document.getElementById(sectionId).classList.remove("hidden");

  // Actualizar la clase 'active' en la navegación
  const navLinks = document.querySelectorAll(".nav-link");
  navLinks.forEach((link) => {
    link.classList.remove("active");
  });
  const activeLink = document.querySelector(
    `.nav-link[data-section="${sectionId}"]`
  );
  if (activeLink) activeLink.classList.add("active");

  // Cargar el selector de ventas si vamos a esa sección
  if (sectionId === "sales") {
    populateSalesForm();
    updateSaleCalculation(); // Reiniciar el cálculo al navegar
  }
  // Si vamos a Estadísticas, renderizar las métricas
  if (sectionId === "stats") {
    renderSalesStats();
  }
  // Nuevo: Si vamos a proveedores, renderizar la lista
  if (sectionId === "suppliers") {
    renderSuppliers();
  }
};

const updateSidebarState = () => {
  const sidebar = document.getElementById("sidebar");
  const logo = document.getElementById("sidebarLogo");
  const menuIcon = document.getElementById("menuIcon");

  if (isSidebarOpen) {
    sidebar.classList.remove("collapsed");
    sidebar.classList.add("open");
    logo.classList.remove("hidden");
    menuIcon.classList.add("ml-auto");
  } else {
    sidebar.classList.remove("open");
    sidebar.classList.add("collapsed");
    logo.classList.add("hidden");
    menuIcon.classList.remove("ml-auto");
  }
};

const toggleSidebar = () => {
  isSidebarOpen = !isSidebarOpen;
  updateSidebarState();
};

// --- 2. CRUD de Inventario (Simulado) ---

const saveItem = (e) => {
  e.preventDefault();

  const name = document.getElementById("item_name").value.trim();
  const quantity = parseInt(
    document.getElementById("item_quantity").value.trim(),
    10
  );
  const price = parseFloat(document.getElementById("item_price").value.trim());
  // Nuevo: Capturar el ID del proveedor
  const supplierId = document.getElementById("item_supplier_id").value;
  // NUEVO: Capturar el estado del producto
  const status = document.getElementById("item_status").value;

  if (!name || isNaN(quantity) || isNaN(price) || quantity < 0 || price < 0) {
    return showMessage(
      "Datos Inválidos",
      "Revisa el nombre, cantidad (>=0) y precio (>=0)."
    );
  }
  if (!supplierId) {
    return showMessage("Error de Proveedor", "Debes seleccionar un proveedor.");
  }

  const itemData = {
    name: name,
    quantity: quantity,
    price: price,
    supplierId: supplierId, // Nuevo: Incluir el ID
    status: status, // NUEVO: Incluir el estado
    updatedAt: new Date().toISOString(),
  };

  if (currentEditingId) {
    // Modo Edición
    const index = inventoryItems.findIndex((i) => i.id === currentEditingId);
    if (index !== -1) {
      inventoryItems[index] = { ...inventoryItems[index], ...itemData };
      showMessage("Actualización Exitosa", `"${name}" actualizado.`);
    }
  } else {
    // Modo Nuevo
    const newItem = { id: String(nextInventoryId++), ...itemData };
    inventoryItems.push(newItem);
    showMessage("Artículo Agregado", `"${name}" agregado.`);
  }

  renderInventory();
  cancelEdit();
};

const deleteItem = async (itemId, itemName) => {
  const confirmed = await showConfirmation(
    `Confirmar Eliminación`,
    `¿Eliminar "${itemName}" del inventario?`
  );
  if (!confirmed) return;

  inventoryItems = inventoryItems.filter((item) => item.id !== itemId);
  showMessage("Eliminación Exitosa", `"${itemName}" ha sido eliminado.`);
  renderInventory();
};

const editItem = (itemId) => {
  const item = inventoryItems.find((i) => i.id === itemId);
  if (!item) return;

  currentEditingId = item.id;
  document.getElementById("item_name").value = item.name;
  document.getElementById("item_quantity").value = item.quantity;
  document.getElementById("item_price").value = item.price.toFixed(2);
  // Nuevo: Seleccionar el proveedor actual
  document.getElementById("item_supplier_id").value = item.supplierId;
  // NUEVO: Seleccionar el estado actual
  document.getElementById("item_status").value = item.status;

  // Cambiar modo de formulario
  const formTitle = document.getElementById("invFormTitle");
  const submitBtn = document.getElementById("invSubmitBtn");
  formTitle.textContent = `Editar Artículo: ${item.name}`;
  submitBtn.textContent = "Guardar Cambios";
  submitBtn.classList.remove("bg-indigo-600");
  submitBtn.classList.add("bg-yellow-600");

  navigateTo("inventory");
  window.scrollTo({ top: 0, behavior: "smooth" });
};

const cancelEdit = () => {
  document.getElementById("inventoryForm").reset();
  currentEditingId = null;
  // Nuevo: Asegurar que el select vuelva a 'Selecciona un proveedor...'
  document.getElementById("item_supplier_id").value = ""; 
  // NUEVO: Asegurar que el select de estado tenga un valor por defecto (el primero)
  document.getElementById("item_status").value = "Disponible";

  const formTitle = document.getElementById("invFormTitle");
  const submitBtn = document.getElementById("invSubmitBtn");
  formTitle.textContent = "Añadir Nuevo Artículo";
  submitBtn.textContent = "Añadir Artículo";
  submitBtn.classList.remove("bg-yellow-600");
  submitBtn.classList.add("bg-indigo-600");
};

// --- NUEVO: CRUD de Proveedores (Simulado) ---

const saveSupplier = (e) => {
  e.preventDefault();

  const name = document.getElementById("supplier_name").value.trim();
  const contact = document.getElementById("supplier_contact").value.trim();
  const phone = document.getElementById("supplier_phone").value.trim();
  const email = document.getElementById("supplier_email").value.trim();

  if (!name || !contact) {
    return showMessage(
      "Datos Inválidos",
      "El nombre y el contacto del proveedor son requeridos."
    );
  }

  const supplierData = {
    name: name,
    contact: contact,
    phone: phone,
    email: email,
  };

  if (currentEditingSupplierId) {
    // Modo Edición
    const index = suppliers.findIndex((s) => s.id === currentEditingSupplierId);
    if (index !== -1) {
      suppliers[index] = { ...suppliers[index], ...supplierData };
      showMessage("Actualización Exitosa", `Proveedor "${name}" actualizado.`);
    }
  } else {
    // Modo Nuevo
    const newSupplier = { id: String(nextSupplierId++), ...supplierData };
    suppliers.push(newSupplier);
    showMessage("Proveedor Agregado", `Proveedor "${name}" agregado.`);
  }

  renderSuppliers();
  cancelSupplierEdit();
  // Es importante actualizar el selector del formulario de inventario
  populateSupplierSelects();
};

const deleteSupplier = async (supplierId, supplierName) => {
  // Verificar si hay productos asociados
  const hasItems = inventoryItems.some((item) => item.supplierId === supplierId);
  if (hasItems) {
    return showMessage(
      "Error de Eliminación",
      `No puedes eliminar a "${supplierName}" porque tiene artículos asociados en el inventario.`
    );
    // Nota: El campo 'status' de los productos no impide la eliminación del proveedor,
    // pero el campo 'supplierId' sí.
  }

  const confirmed = await showConfirmation(
    `Confirmar Eliminación`,
    `¿Eliminar al proveedor "${supplierName}"?`
  );
  if (!confirmed) return;

  suppliers = suppliers.filter((s) => s.id !== supplierId);
  showMessage("Eliminación Exitosa", `Proveedor "${supplierName}" eliminado.`);
  renderSuppliers();
  populateSupplierSelects(); // Actualizar el select en inventario
};

const editSupplier = (supplierId) => {
  const supplier = suppliers.find((s) => s.id === supplierId);
  if (!supplier) return;

  currentEditingSupplierId = supplier.id;
  document.getElementById("supplier_name").value = supplier.name;
  document.getElementById("supplier_contact").value = supplier.contact;
  document.getElementById("supplier_phone").value = supplier.phone;
  document.getElementById("supplier_email").value = supplier.email;

  // Cambiar modo de formulario
  const formTitle = document.getElementById("suppFormTitle");
  const submitBtn = document.getElementById("suppSubmitBtn");
  formTitle.textContent = `Editar Proveedor: ${supplier.name}`;
  submitBtn.textContent = "Guardar Cambios";
  submitBtn.classList.remove("bg-indigo-600");
  submitBtn.classList.add("bg-yellow-600");

  navigateTo("suppliers");
  window.scrollTo({ top: 0, behavior: "smooth" });
};

const cancelSupplierEdit = () => {
  document.getElementById("supplierForm").reset();
  currentEditingSupplierId = null;
  const formTitle = document.getElementById("suppFormTitle");
  const submitBtn = document.getElementById("suppSubmitBtn");
  formTitle.textContent = "Añadir Nuevo Proveedor";
  submitBtn.textContent = "Añadir Proveedor";
  submitBtn.classList.remove("bg-yellow-600");
  submitBtn.classList.add("bg-indigo-600");
};

// --- 3. Lógica de Ventas (Transacción Simulada) ---

const populateSalesForm = () => {
  const select = document.getElementById("sale_item_id");
  select.innerHTML =
    '<option value="" disabled selected>Selecciona un artículo...</option>';

  if (inventoryItems.length === 0) {
    select.innerHTML +=
      '<option value="" disabled>No hay artículos en inventario</option>';
  }

  inventoryItems.forEach((item) => {
    // Opcional: solo permitir vender si el estado es 'Disponible' o similar
    if (item.status === 'Disponible' || item.status === 'Bajo Pedido') {
        const option = document.createElement("option");
        option.value = item.id;
        option.textContent = `${item.name} (Stock: ${item.quantity})`;
        select.appendChild(option);
    }
  });

  // Resetear el cálculo
  document.getElementById("salesForm").reset();
};

// Nuevo: Función para cargar los proveedores en los select
const populateSupplierSelects = () => {
  const select = document.getElementById("item_supplier_id");
  select.innerHTML =
    '<option value="" disabled selected>Selecciona un proveedor...</option>';

  if (suppliers.length === 0) {
    select.innerHTML +=
      '<option value="" disabled>No hay proveedores registrados</option>';
  }

  suppliers.forEach((s) => {
    const option = document.createElement("option");
    option.value = s.id;
    option.textContent = `${s.name} (${s.contact})`;
    select.appendChild(option);
  });

  // Si estamos en modo edición de inventario, re-seleccionar
  if (currentEditingId) {
     const item = inventoryItems.find((i) => i.id === currentEditingId);
     if (item) document.getElementById("item_supplier_id").value = item.supplierId;
  }
};


const updateSaleCalculation = () => {
  const itemId = document.getElementById("sale_item_id").value;
  const quantityInput = document.getElementById("sale_quantity");
  const quantity = parseInt(quantityInput.value, 10);
  const item = inventoryItems.find((i) => i.id === itemId);

  const priceDisplay = document.getElementById("sale_price_display");
  const totalDisplay = document.getElementById("sale_total_display");

  if (!item || isNaN(quantity) || quantity <= 0) {
    priceDisplay.textContent = "$0.00";
    totalDisplay.textContent = "$0.00";
    quantityInput.classList.remove("border-red-500");
    return;
  }

  // Validar stock
  if (quantity > item.quantity) {
    quantityInput.classList.add("border-red-500");
    showMessage(
      "Error de Stock",
      `Solo hay ${item.quantity} unidades de ${item.name}.`
    );
    priceDisplay.textContent = `$${item.price.toFixed(2)}`;
    totalDisplay.textContent = "---";
    return;
  } else {
    quantityInput.classList.remove("border-red-500");
  }

  const totalPrice = quantity * item.price;
  priceDisplay.textContent = `$${item.price.toFixed(2)}`;
  totalDisplay.textContent = `$${totalPrice.toFixed(2)}`;
};

// --- 6. Estadísticas de Ventas ---

const renderSalesStats = () => {
  // Agregar seguridad: si no existe el elemento, salir
  const totalSalesEl = document.getElementById("stats_total_sales");
  const totalRevenueEl = document.getElementById("stats_total_revenue");
  const topProductEl = document.getElementById("stats_top_product");
  const tableBody = document.getElementById("statsByProductTable");
  if (!totalSalesEl || !totalRevenueEl || !topProductEl || !tableBody) return;

  // Agregar agregaciones por producto
  const byProduct = {}; // itemName -> {units, revenue}
  let totalSalesCount = 0;
  let totalRevenue = 0;

  salesHistory.forEach((sale) => {
    const name = sale.itemName || sale.itemId;
    if (!byProduct[name]) byProduct[name] = { units: 0, revenue: 0 };
    byProduct[name].units += sale.quantitySold;
    byProduct[name].revenue += sale.totalRevenue;
    totalSalesCount += sale.quantitySold;
    totalRevenue += sale.totalRevenue;
  });

  // Actualizar tarjetas resumen
  totalSalesEl.textContent = totalSalesCount;
  totalRevenueEl.textContent = `$${totalRevenue.toFixed(2)}`;

  // Determinar producto top por unidades vendidas
  let topName = "—";
  let topUnits = 0;
  Object.keys(byProduct).forEach((name) => {
    if (byProduct[name].units > topUnits) {
      topUnits = byProduct[name].units;
      topName = name;
    }
  });
  topProductEl.textContent = topName === "—" ? "—" : `${topName} (${topUnits})`;

  // Renderizar tabla por producto (ordenada por unidades vendidas desc)
  const rows = Object.keys(byProduct).map((name) => ({
    name,
    units: byProduct[name].units,
    revenue: byProduct[name].revenue,
  }));
  rows.sort((a, b) => b.units - a.units);

  tableBody.innerHTML = "";
  if (rows.length === 0) {
    tableBody.innerHTML =
      '<tr><td colspan="3" class="p-4 text-center text-gray-500">No hay datos de ventas.</td></tr>';
  } else {
    rows.forEach((r) => {
      const tr = document.createElement("tr");
      tr.className = "border-b hover:bg-gray-50 transition duration-150";

      const nameCell = tr.insertCell();
      nameCell.textContent = r.name;

      const unitsCell = tr.insertCell();
      unitsCell.textContent = r.units;
      unitsCell.className = "text-center font-bold";

      const revenueCell = tr.insertCell();
      revenueCell.textContent = `$${r.revenue.toFixed(2)}`;
      revenueCell.className = "text-right font-semibold text-green-700";

      tableBody.appendChild(tr);
    });
  }

  // --- Actualizar gráficas si existen ---
  // 1) Gráfica de ingresos por fecha (línea)
  if (typeof Chart !== "undefined" && revenueChart) {
    const byDate = {};
    salesHistory.forEach((sale) => {
      const d = new Date(sale.timestamp);
      // usar fecha local (sin hora)
      const key = d.toLocaleDateString("es-ES");
      byDate[key] = (byDate[key] || 0) + sale.totalRevenue;
    });
    const dateLabels = Object.keys(byDate).sort(
      (a, b) => new Date(a) - new Date(b)
    );
    const dateData = dateLabels.map((l) => byDate[l]);
    revenueChart.data.labels = dateLabels;
    revenueChart.data.datasets[0].data = dateData;
    revenueChart.update();
  }

  // 2) Gráfica de torta por producto
  if (typeof Chart !== "undefined" && productPieChart) {
    const labels = rows.map((r) => r.name);
    const data = rows.map((r) => r.units);
    productPieChart.data.labels = labels;
    productPieChart.data.datasets[0].data = data;
    productPieChart.update();
  }
};

// Inicializar las gráficas vacías (se llama en initApp)
const initStatsCharts = () => {
  if (typeof Chart === "undefined") return;
  const revEl = document.getElementById("revenueChart");
  const pieEl = document.getElementById("productPieChart");

  if (revEl && !revenueChart) {
    revenueChart = new Chart(revEl.getContext("2d"), {
      type: "line",
      data: {
        labels: [],
        datasets: [
          {
            label: "Ingresos",
            data: [],
            borderColor: "#10B981",
            backgroundColor: "rgba(16,185,129,0.08)",
            fill: true,
            tension: 0.2,
          },
        ],
      },
      options: {
        // Usar tamaño fijo del canvas (width/height en HTML). Evita que Chart.js redimensione al mostrarse/ocultarse.
        responsive: false,
        maintainAspectRatio: false,
        scales: {
          x: { display: true },
          y: { display: true, beginAtZero: true },
        },
      },
    });
  }

  if (pieEl && !productPieChart) {
    productPieChart = new Chart(pieEl.getContext("2d"), {
      type: "pie",
      data: {
        labels: [],
        datasets: [
          {
            data: [],
            backgroundColor: [
              "#6366F1",
              "#10B981",
              "#F59E0B",
              "#EF4444",
              "#3B82F6",
              "#8B5CF6",
            ],
          },
        ],
      },
      options: { responsive: false, maintainAspectRatio: false },
    });
  }
  // Llenar con datos iniciales si ya existen ventas
  renderSalesStats();
};

const recordSale = (e) => {
  e.preventDefault();

  const itemId = document.getElementById("sale_item_id").value;
  const quantity = parseInt(document.getElementById("sale_quantity").value, 10);
  const item = inventoryItems.find((i) => i.id === itemId);

  if (!item || isNaN(quantity) || quantity <= 0 || quantity > item.quantity) {
    return showMessage(
      "Datos Inválidos",
      "Revisa la selección del artículo y la cantidad vendida."
    );
  }

  // 1. Actualizar Inventario (Restar Stock)
  item.quantity -= quantity;
  item.updatedAt = new Date().toISOString();

  // 2. Registrar Venta (Añadir a Historial)
  const saleData = {
    id: "s" + nextSaleId++,
    itemId: itemId,
    itemName: item.name,
    quantitySold: quantity,
    unitPrice: item.price,
    totalRevenue: quantity * item.price,
    timestamp: new Date().toISOString(),
  };
  salesHistory.push(saleData);

  showMessage(
    "Venta Registrada",
    `Se vendieron ${quantity} unidades de ${
      item.name
    } por $${saleData.totalRevenue.toFixed(2)}.`
  );
  document.getElementById("salesForm").reset();
  updateSaleCalculation(); // Resetear visualización
  renderInventory(); // Actualizar el inventario
  renderSalesHistory(); // Actualizar el historial
};

// --- 4. Renderizado ---

// Nuevo: Función para buscar nombre de proveedor por ID
const getSupplierName = (supplierId) => {
    const supplier = suppliers.find(s => s.id === supplierId);
    return supplier ? supplier.name : 'Desconocido';
};

const renderInventory = () => {
  inventoryItems.sort((a, b) => a.name.localeCompare(b.name));

  const tableBody = document.getElementById("inventoryTableBody");
  tableBody.innerHTML = "";
  let totalValue = 0;

  if (inventoryItems.length === 0) {
    // Colspan actualizado de 6 a 7
    tableBody.innerHTML =
      '<tr><td colspan="7" class="p-4 text-center text-gray-500">No hay artículos en el inventario.</td></tr>'; 
  }

  inventoryItems.forEach((item) => {
    const rowValue = item.quantity * item.price;
    totalValue += rowValue;

    const row = tableBody.insertRow();
    row.className = "border-b hover:bg-gray-50 transition duration-150";

    if (item.quantity < LOW_STOCK_THRESHOLD) {
      row.classList.add("table-item-low-stock", "border-l-4", "border-red-500");
    }

    // Índice 0: Nombre
    row.insertCell().textContent = item.name;

    // Índice 1: Cantidad
    row.insertCell().textContent = item.quantity;
    row.cells[1].className = `text-center font-bold ${
      item.quantity < LOW_STOCK_THRESHOLD ? "text-red-600" : "text-gray-800"
    }`;

    // Índice 2: Proveedor
    row.insertCell().textContent = getSupplierName(item.supplierId);
    row.cells[2].className = "text-left hidden lg:table-cell text-sm text-gray-600"; 
    
    // Índice 3: Estado (NUEVO)
    const statusCell = row.insertCell();
    statusCell.textContent = item.status;
    statusCell.className = "text-center hidden sm:table-cell font-medium";
    
    // Opcional: Estilos por estado (para visualización más clara)
    if (item.status === "Descontinuado") {
        statusCell.classList.add("text-red-500");
    } else if (item.status === "Bajo Pedido") {
        statusCell.classList.add("text-yellow-600");
    } else {
        statusCell.classList.add("text-green-600");
    }


    // Índice 4: Precio Unitario (Índice anterior 3)
    row.insertCell().textContent = `$${item.price.toFixed(2)}`;
    row.cells[4].className = "text-right hidden sm:table-cell"; 

    // Índice 5: Valor Total (Índice anterior 4)
    row.insertCell().textContent = `$${rowValue.toFixed(2)}`;
    row.cells[5].className = "text-right font-semibold"; 

    // Índice 6: Acciones (Índice anterior 5)
    const actionsCell = row.insertCell();
    actionsCell.className = "flex space-x-2 justify-center py-2"; 

    const editBtn = document.createElement("button");
    editBtn.textContent = "Editar";
    editBtn.className =
      "text-blue-600 hover:text-blue-800 text-sm font-medium transition duration-150";
    editBtn.onclick = () => editItem(item.id);
    actionsCell.appendChild(editBtn);

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Eliminar";
    deleteBtn.className =
      "text-red-600 hover:text-red-800 text-sm font-medium transition duration-150";
    deleteBtn.onclick = () => deleteItem(item.id, item.name);
    actionsCell.appendChild(deleteBtn);
  });

  document.getElementById("totalItems").textContent = inventoryItems.length;
  document.getElementById(
    "inventoryValue"
  ).textContent = `$${totalValue.toFixed(2)}`;

  populateSalesForm(); // Asegurar que el formulario de ventas se actualice
};

// NUEVO: Renderizado de Proveedores
const renderSuppliers = () => {
    suppliers.sort((a, b) => a.name.localeCompare(b.name));

    const tableBody = document.getElementById("suppliersTableBody");
    tableBody.innerHTML = "";

    if (suppliers.length === 0) {
        tableBody.innerHTML =
            '<tr><td colspan="5" class="p-4 text-center text-gray-500">No hay proveedores registrados.</td></tr>';
    }

    suppliers.forEach((supplier) => {
        const row = tableBody.insertRow();
        row.className = "border-b hover:bg-gray-50 transition duration-150";

        // Nombre
        row.insertCell().textContent = supplier.name;

        // Contacto
        row.insertCell().textContent = supplier.contact;

        // Teléfono
        row.insertCell().textContent = supplier.phone || 'N/A';
        row.cells[2].className = "text-center hidden sm:table-cell";

        // Email
        row.insertCell().textContent = supplier.email || 'N/A';
        row.cells[3].className = "text-sm text-gray-600 hidden md:table-cell";

        // Acciones
        const actionsCell = row.insertCell();
        actionsCell.className = "flex space-x-2 justify-center py-2";

        const editBtn = document.createElement("button");
        editBtn.textContent = "Editar";
        editBtn.className =
            "text-blue-600 hover:text-blue-800 text-sm font-medium transition duration-150";
        editBtn.onclick = () => editSupplier(supplier.id);
        actionsCell.appendChild(editBtn);

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Eliminar";
        deleteBtn.className =
            "text-red-600 hover:text-red-800 text-sm font-medium transition duration-150";
        deleteBtn.onclick = () => deleteSupplier(supplier.id, supplier.name);
        actionsCell.appendChild(deleteBtn);
    });
};


const renderSalesHistory = () => {
  // Ordenar por timestamp (el más reciente primero)
  salesHistory.sort((a, b) => b.timestamp.localeCompare(a.timestamp));

  const tableBody = document.getElementById("salesTableBody");
  tableBody.innerHTML = "";
  let totalRevenue = 0;

  if (salesHistory.length === 0) {
    tableBody.innerHTML =
      '<tr><td colspan="5" class="p-4 text-center text-gray-500">No hay ventas registradas.</td></tr>';
  }

  salesHistory.forEach((sale) => {
    totalRevenue += sale.totalRevenue;

    const row = tableBody.insertRow();
    row.className = "border-b hover:bg-gray-50 transition duration-150";

    // Producto
    row.insertCell().textContent = sale.itemName;

    // Cantidad
    row.insertCell().textContent = sale.quantitySold;
    row.cells[1].className = "text-center";

    // Precio Unitario
    row.insertCell().textContent = `$${sale.unitPrice.toFixed(2)}`;
    row.cells[2].className = "text-right hidden sm:table-cell";

    // Ingreso Total
    row.insertCell().textContent = `$${sale.totalRevenue.toFixed(2)}`;
    row.cells[3].className = "text-right font-semibold text-green-700";

    // Fecha
    const date = new Date(sale.timestamp);
    const dateCell = row.insertCell();
    dateCell.textContent = date.toLocaleDateString("es-ES", {
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
    dateCell.className =
      "text-sm text-gray-500 hidden lg:table-cell text-center";
  });

  document.getElementById("totalSalesCount").textContent = salesHistory.length;
  document.getElementById(
    "totalRevenueValue"
  ).textContent = `$${totalRevenue.toFixed(2)}`;
};

// --- 5. Inicialización de Eventos ---

const initApp = () => {
  // Inicializar selectores de proveedores
  populateSupplierSelects();

  // Inicializar vistas con datos simulados
  renderInventory();
  renderSalesHistory();
  renderSuppliers(); // Nuevo
  initStatsCharts();
  navigateTo("inventory"); // Mostrar inventario por defecto
  updateSidebarState(); // Asegurar estado inicial

  // Event Listeners del formulario de Inventario
  document.getElementById("inventoryForm").addEventListener("submit", saveItem);
  document
    .getElementById("cancelEditBtn")
    .addEventListener("click", cancelEdit);

  // NUEVOS Event Listeners del formulario de Proveedores
  document.getElementById("supplierForm").addEventListener("submit", saveSupplier);
  document
    .getElementById("cancelSupplierEditBtn")
    .addEventListener("click", cancelSupplierEdit);


  // Event Listeners de Modals
  document.getElementById("messageModalClose").addEventListener("click", () => {
    document.getElementById("messageModal").classList.add("hidden");
  });
  document.getElementById("confirmYes").onclick = () => {
    confirmationModal.classList.add("hidden");
    if (resolveConfirmation) resolveConfirmation(true);
  };
  document.getElementById("confirmNo").onclick = () => {
    confirmationModal.classList.add("hidden");
    if (resolveConfirmation) resolveConfirmation(false);
  };

  // Event Listeners de Navegación
  document
    .getElementById("menuButton")
    .addEventListener("click", toggleSidebar);
  document.querySelectorAll(".nav-link").forEach((link) => {
    link.addEventListener("click", (e) => {
      e.preventDefault();
      navigateTo(e.currentTarget.getAttribute("data-section"));
    });
  });

  // Event Listeners para formulario de ventas
  document.getElementById("salesForm").addEventListener("submit", recordSale);
  document
    .getElementById("sale_item_id")
    .addEventListener("change", updateSaleCalculation);
  document
    .getElementById("sale_quantity")
    .addEventListener("input", updateSaleCalculation);
};

// Iniciar la aplicación al cargar la página
window.addEventListener("load", initApp);
