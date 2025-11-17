package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.Venta;
import com.universidadcartagena.api_proyect.model.MetodoPago; // ¡Importación faltante!
import java.util.List;
import java.math.BigDecimal; // ¡Importación faltante!
import java.time.LocalDateTime; // ¡Importación faltante!

public interface VentaService {

    // 1. Registrar una venta, descontar stock, y registrar movimientos (RF-05.3)
    Venta registrarVenta(Venta venta);

    // 2. Consultar ventas (RF-05.4)
    List<Venta> listarTodas();

    // RF-05.4: Consultar por rango de fechas
    List<Venta> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // RF-05.4: Consultar por método de pago
    List<Venta> buscarPorMetodoPago(MetodoPago metodoPago);

    // RF-05.5: Calcular total de ventas en un periodo
    BigDecimal calcularTotalVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}