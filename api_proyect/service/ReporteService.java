package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.model.Venta;
import com.universidadcartagena.api_proyect.model.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReporteService {

    // RF-08.1
    List<ProductoVendidoDTO> reporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin);

    // RF-08.2
    List<Producto> reporteProductosBajaRotacion(int dias);

    // RF-08.3
    BigDecimal valorTotalInventarioActual();

    // RF-08.4
    List<Venta> reporteVentasTotales(String periodo); // Simple: devuelve todas las ventas en el periodo

    // RF-08.5
    List<ProductoABCTO> analisisABC();

    // RF-08.6
    List<ProductoRentabilidadDTO> reporteRentabilidadPorProducto();

    // RF-08.7
    List<Producto> reporteProductosProximosAVencer(int dias);

    // RF-08.8
    ResumenDiarioDTO resumenDiarioVentas(LocalDate fecha);
}