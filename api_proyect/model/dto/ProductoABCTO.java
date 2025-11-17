package com.universidadcartagena.api_proyect.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoABCTO {
    private Long productoId;
    private String nombreProducto;
    private BigDecimal ingresoTotal;
    private BigDecimal porcentajeAcumuladoIngreso;
    private String categoriaABC; // A, B o C
}
