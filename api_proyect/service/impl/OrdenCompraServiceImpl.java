package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.*;
import com.universidadcartagena.api_proyect.repository.OrdenCompraRepository;
import com.universidadcartagena.api_proyect.repository.ProductoRepository;
import com.universidadcartagena.api_proyect.repository.ProveedorRepository; // ¡Nueva Importación necesaria!
import com.universidadcartagena.api_proyect.service.MovimientoInventarioService;
import com.universidadcartagena.api_proyect.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Inyección necesaria para la validación
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    // -------------------------------------------------------------
    // RF-06.1: CREAR ORDEN (Corregido: Validaciones y Estado Inicial)
    // -------------------------------------------------------------
    @Override
    @Transactional
    public OrdenCompra crearOrden(OrdenCompra ordenCompra) {

        // 1. CORRECCIÓN DEL ERROR 500: Garantizar que el estado nunca sea nulo
        if (ordenCompra.getEstado() == null) {
            ordenCompra.setEstado(EstadoOrdenCompra.PENDIENTE);
        }

        // 2. VALIDAR Y CARGAR PROVEEDOR
        Proveedor proveedor = proveedorRepository.findById(ordenCompra.getProveedor().getId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + ordenCompra.getProveedor().getId()));
        ordenCompra.setProveedor(proveedor);

        BigDecimal totalOrden = BigDecimal.ZERO;

        // 3. ITERAR, VALIDAR Y CALCULAR ÍTEMS
        for (DetalleOrdenCompra detalle : ordenCompra.getItems()) {

            // Validar y cargar Producto (evita NPE si el ID es inválido)
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getId()));
            detalle.setProducto(producto);

            // Asegurar la relación bidireccional
            detalle.setOrdenCompra(ordenCompra);

            // Calcular subtotal del ítem
            BigDecimal subtotalItem = detalle.getPrecioUnitarioCompra().multiply(new BigDecimal(detalle.getCantidad()));
            detalle.setSubtotal(subtotalItem);

            totalOrden = totalOrden.add(subtotalItem);
        }

        ordenCompra.setTotal(totalOrden);
        return ordenCompraRepository.save(ordenCompra);
    }

    // -------------------------------------------------------------
    // RF-06.3: MARCAR COMO RECIBIDA Y ACTUALIZAR STOCK (CRÍTICO)
    // -------------------------------------------------------------
    @Override
    @Transactional
    public OrdenCompra recibirOrden(Long ordenId) {
        OrdenCompra orden = ordenCompraRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden de Compra no encontrada."));

        if (orden.getEstado() == EstadoOrdenCompra.RECIBIDA) {
            throw new RuntimeException("La orden ya fue marcada como RECIBIDA.");
        }

        if (orden.getEstado() == EstadoOrdenCompra.CANCELADA) {
            throw new RuntimeException("La orden está CANCELADA y no puede ser recibida.");
        }

        for (DetalleOrdenCompra detalle : orden.getItems()) {
            Producto producto = detalle.getProducto();
            Integer cantidadEntrada = detalle.getCantidad();

            // Registrar Movimiento de ENTRADA y actualizar stock (RF-06.3)
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setProducto(producto);
            movimiento.setTipoMovimiento(TipoMovimiento.ENTRADA);
            movimiento.setCantidad(cantidadEntrada);
            movimiento.setObservaciones("Recepción de Orden de Compra #" + orden.getId());

            movimientoInventarioService.registrarMovimiento(movimiento);

            // Opcional: Actualizar el costo de compra del producto con el precio de esta orden
            producto.setCostoCompra(detalle.getPrecioUnitarioCompra());
            productoRepository.save(producto);
        }

        // Cambiar el estado de la orden a RECIBIDA (RF-06.3)
        orden.setEstado(EstadoOrdenCompra.RECIBIDA);
        return ordenCompraRepository.save(orden);
    }

    // -------------------------------------------------------------
    // RF-06.2: SUGERENCIA DE COMPRA
    // -------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerSugerenciaCompra() {
        return productoRepository.obtenerSugerenciaCompraBajoMinimo();
    }

    // -------------------------------------------------------------
    // RF-06.4: LISTADO DE ÓRDENES
    // -------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<OrdenCompra> listarPorEstadoYProveedor(EstadoOrdenCompra estado, Proveedor proveedor) {
        if (estado != null) {
            return ordenCompraRepository.findByEstado(estado);
        }
        return ordenCompraRepository.findAll();
    }
}