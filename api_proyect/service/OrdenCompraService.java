package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.OrdenCompra;
import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.model.EstadoOrdenCompra;
import com.universidadcartagena.api_proyect.model.Proveedor;

import java.util.List;

public interface OrdenCompraService {

    // 1. Crear Orden de Compra (RF-06.1)
    OrdenCompra crearOrden(OrdenCompra ordenCompra);

    // 2. Marcar como RECIBIDA y actualizar stock (CRÍTICO: RF-06.3)
    OrdenCompra recibirOrden(Long ordenId);

    // 3. Obtener sugerencia de productos (RF-06.2)
    List<Producto> obtenerSugerenciaCompra();

    // 4. Listar órdenes por estado y proveedor (RF-06.4)
    List<OrdenCompra> listarPorEstadoYProveedor(EstadoOrdenCompra estado, Proveedor proveedor);
}