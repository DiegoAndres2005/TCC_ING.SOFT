package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.DetalleVenta; // <-- Importación corregida aquí
import com.universidadcartagena.api_proyect.model.dto.ProductoRentabilidadDTO;
import com.universidadcartagena.api_proyect.model.dto.ProductoVendidoDTO; // <-- ¡La importación que faltaba estaba mal escrita!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    // --------------------------------------------------------------------------
    // RF-08.1: Productos Más Vendidos en un Periodo
    // --------------------------------------------------------------------------
    @Query("SELECT new com.universidadcartagena.api_proyect.model.dto.ProductoVendidoDTO(" +
            "d.producto.id, d.producto.nombre, SUM(d.cantidad)) " +
            "FROM DetalleVenta d WHERE d.venta.fechaHora BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY d.producto.id, d.producto.nombre ORDER BY SUM(d.cantidad) DESC")
    List<ProductoVendidoDTO> findProductosMasVendidos(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // --------------------------------------------------------------------------
    // RF-08.6: Rentabilidad por Producto (Usa d.precioUnitario)
    // --------------------------------------------------------------------------
    @Query("SELECT new com.universidadcartagena.api_proyect.model.dto.ProductoRentabilidadDTO(" +
            "d.producto.id, d.producto.nombre, SUM((d.precioUnitario - d.producto.costoCompra) * d.cantidad)) " +
            "FROM DetalleVenta d GROUP BY d.producto.id, d.producto.nombre " +
            "ORDER BY SUM((d.precioUnitario - d.producto.costoCompra) * d.cantidad) DESC")
    List<ProductoRentabilidadDTO> findRentabilidadPorProducto();

    // --------------------------------------------------------------------------
    // RF-08.5: Auxiliar para Análisis ABC (Ingreso Total por Producto)
    // --------------------------------------------------------------------------
    @Query("SELECT d.producto.id, d.producto.nombre, SUM(d.subtotal) " +
            "FROM DetalleVenta d GROUP BY d.producto.id, d.producto.nombre " +
            "ORDER BY SUM(d.subtotal) DESC")
    List<Object[]> findIngresoTotalPorProducto();
}