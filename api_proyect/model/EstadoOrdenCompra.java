package com.universidadcartagena.api_proyect.model;

public enum EstadoOrdenCompra {

    PENDIENTE,  // Orden creada, esperando recepción
    RECIBIDA,   // Inventario actualizado, orden completada
    CANCELADA   // Orden anulada
}