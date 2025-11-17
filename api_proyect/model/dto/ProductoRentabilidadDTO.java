package com.universidadcartagena.api_proyect.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductoRentabilidadDTO {
    private Long productoId;
    private String nombreProducto;
    private BigDecimal gananciaTotal;
}