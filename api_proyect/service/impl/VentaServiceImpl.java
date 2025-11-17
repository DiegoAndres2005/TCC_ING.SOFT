package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.MovimientoInventario;
import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.model.TipoMovimiento;
import com.universidadcartagena.api_proyect.model.Venta;
import com.universidadcartagena.api_proyect.model.DetalleVenta;
import com.universidadcartagena.api_proyect.model.MetodoPago; // Necesaria para el nuevo método
import com.universidadcartagena.api_proyect.repository.ProductoRepository;
import com.universidadcartagena.api_proyect.repository.VentaRepository;
import com.universidadcartagena.api_proyect.service.MovimientoInventarioService;
import com.universidadcartagena.api_proyect.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime; // Necesaria para los nuevos métodos
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    @Override
    @Transactional // CRÍTICO: Todo debe ser atómico.
    public Venta registrarVenta(Venta venta) {
        BigDecimal totalVenta = BigDecimal.ZERO;

        // 1. ITERAR SOBRE LOS ÍTEMS DE VENTA para validar, calcular y descontar stock.
        for (DetalleVenta detalle : venta.getItems()) {

            // 1.1 Obtener el producto real de la BD
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getProducto().getId()));

            // 1.2 Calcular precio/subtotal y fijarlo en el DetalleVenta
            BigDecimal precioUnitario = producto.getPrecioVenta();
            detalle.setPrecioUnitario(precioUnitario);

            BigDecimal subtotalItem = precioUnitario.multiply(new BigDecimal(detalle.getCantidad()));
            detalle.setSubtotal(subtotalItem);

            totalVenta = totalVenta.add(subtotalItem);

            // 1.3 Asignar la Venta y Producto al Detalle
            detalle.setVenta(venta);
            detalle.setProducto(producto);

            // 1.4 Descontar stock y registrar movimiento (RF-05.3)
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setProducto(producto);
            movimiento.setTipoMovimiento(TipoMovimiento.SALIDA); // La venta siempre es SALIDA
            movimiento.setCantidad(detalle.getCantidad());
            movimiento.setObservaciones("Venta registrada (ID pendiente)");

            movimientoInventarioService.registrarMovimiento(movimiento);
        }

        // 2. Fijar totales y guardar el encabezado de la venta
        venta.setSubtotal(totalVenta);
        venta.setTotal(totalVenta);

        Venta ventaGuardada = ventaRepository.save(venta);

        return ventaGuardada;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    // -------------------------------------------------------------
    // MÉTODOS DE CONSULTA ADICIONALES (RF-05.4 y RF-05.5)
    // -------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorMetodoPago(MetodoPago metodoPago) {
        return ventaRepository.findByMetodoPago(metodoPago);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        BigDecimal total = ventaRepository.sumarTotalVentasEnRango(fechaInicio, fechaFin);
        // Devuelve 0 si no hay ventas en el rango
        return total != null ? total : BigDecimal.ZERO;
    }
}