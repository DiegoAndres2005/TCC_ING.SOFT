package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.AlertaProducto;
import com.universidadcartagena.api_proyect.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/alertas") // Endpoint: http://localhost:8080/api/alertas
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    /**
     * RF-07.2: Endpoint para consultar todas las alertas activas
     */
    @GetMapping
    public ResponseEntity<List<AlertaProducto>> obtenerAlertasActivas() {
        List<AlertaProducto> alertas = alertaService.generarAlertasActivas();
        return new ResponseEntity<>(alertas, HttpStatus.OK);
    }
}