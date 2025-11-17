package com.universidadcartagena.api_proyect.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoVendidoDTO {
    private Long productoId;
    private String nombreProducto;
    private Long cantidadTotalVendida;
}