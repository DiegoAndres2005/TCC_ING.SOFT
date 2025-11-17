package com.universidadcartagena.api_proyect.model;

public enum TipoMovimiento {

    // Compra a proveedor
    ENTRADA,

    // Venta
    SALIDA,

    // Corrección manual (se encontró stock)
    AJUSTE_POSITIVO,

    // Corrección manual (merma, robo, vencido)
    AJUSTE_NEGATIVO
}