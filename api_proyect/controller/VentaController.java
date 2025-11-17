package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.Venta;
import com.universidadcartagena.api_proyect.model.MetodoPago; // ¡Importación necesaria!
import com.universidadcartagena.api_proyect.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat; // ¡Importación necesaria!
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal; // ¡Importación necesaria!
import java.time.LocalDateTime; // ¡Importación necesaria!
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // POST: RF-05.1 Registrar una Venta (CRUD)
    @PostMapping
    public ResponseEntity<?> registrarVenta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.registrarVenta(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // GET: RF-05.4 Consultar historial completo (CRUD)
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listarTodas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    // -------------------------------------------------------------
    // NUEVOS ENDPOINTS DE CONSULTA Y REPORTE (RF-05.4 y RF-05.5)
    // -------------------------------------------------------------

    // RF-05.4: Consulta por Rango de Fechas (Ej: /api/ventas/consulta?inicio=...&fin=...)
    @GetMapping("/consulta")
    public ResponseEntity<List<Venta>> buscarVentasPorRango(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        List<Venta> ventas = ventaService.buscarPorRangoFechas(fechaInicio, fechaFin);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    // RF-05.4: Consulta por Método de Pago (Ej: /api/ventas/consulta/pago?metodo=TARJETA)
    @GetMapping("/consulta/pago")
    public ResponseEntity<List<Venta>> buscarVentasPorMetodo(
            @RequestParam("metodo") String metodoPagoStr) {

        try {
            MetodoPago metodoPago = MetodoPago.valueOf(metodoPagoStr.toUpperCase());
            List<Venta> ventas = ventaService.buscarPorMetodoPago(metodoPago);
            return new ResponseEntity<>(ventas, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Método de pago no válido.", HttpStatus.BAD_REQUEST);
        }
    }

    // RF-05.5: Cálculo de Total de Ventas (Ej: /api/ventas/total?inicio=...&fin=...)
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calcularTotalVentas(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        BigDecimal total = ventaService.calcularTotalVentas(fechaInicio, fechaFin);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}