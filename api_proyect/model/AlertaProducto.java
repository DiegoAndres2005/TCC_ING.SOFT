package com.universidadcartagena.api_proyect.model;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDate;

@Data
@Builder // Útil para construir la alerta de forma sencilla
public class AlertaProducto {

    // RF-07.3: Campos requeridos
    private String tipoAlerta;
    private Producto productoAfectado;
    private LocalDate fechaGeneracion = LocalDate.now();
    private String estado = "ACTIVA"; // Por defecto siempre es activa al generarse
    private String prioridad; // Baja, Media, Alta

    // Campos de detalle (opcionales para mayor claridad)
    private String mensaje;
}