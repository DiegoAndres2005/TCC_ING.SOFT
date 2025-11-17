package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.AlertaProducto;
import java.util.List;

public interface AlertaService {

    // RF-07.2: Endpoint para consultar todas las alertas activas
    List<AlertaProducto> generarAlertasActivas();
}