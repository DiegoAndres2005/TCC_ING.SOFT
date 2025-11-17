package com.universidadcartagena.api_proyect.model.dto;

import com.universidadcartagena.api_proyect.model.Producto; // Necesario para el producto más vendido
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ResumenDiarioDTO {

    // Total de ventas del día (RF-08.8a)
    private BigDecimal totalVentasDia = BigDecimal.ZERO;

    // Cantidad de transacciones (RF-08.8b)
    private Long cantidadTransacciones = 0L;

    // Producto más vendido del día (RF-08.8c)
    private Producto productoMasVendido;
    private Long cantidadProductoMasVendido = 0L;

    // Método de pago más utilizado (RF-08.8d)
    // Nota: Esto asume que tienes un campo o relación de 'metodoPago' en tu entidad Venta
    private String metodoPagoMasUsado;

    private LocalDate fecha;
}