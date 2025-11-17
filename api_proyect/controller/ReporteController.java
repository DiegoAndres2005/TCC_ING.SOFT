package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.model.dto.ProductoABCTO;
import com.universidadcartagena.api_proyect.model.dto.ProductoRentabilidadDTO;
import com.universidadcartagena.api_proyect.model.dto.ProductoVendidoDTO;
import com.universidadcartagena.api_proyect.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes") // URL Base
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // RF-08.1: Productos Más Vendidos
    @GetMapping("/mas-vendidos")
    public ResponseEntity<List<ProductoVendidoDTO>> getProductosMasVendidos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<ProductoVendidoDTO> reporte = reporteService.reporteProductosMasVendidos(fechaInicio, fechaFin);
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

    // RF-08.2: Productos de Baja Rotación
    @GetMapping("/baja-rotacion")
    public ResponseEntity<List<Producto>> getProductosBajaRotacion(
            @RequestParam(defaultValue = "30") int dias) {

        List<Producto> reporte = reporteService.reporteProductosBajaRotacion(dias);
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

    // RF-08.3: Valor Total del Inventario
    @GetMapping("/valor-inventario")
    public ResponseEntity<BigDecimal> getValorTotalInventario() {
        BigDecimal valor = reporteService.valorTotalInventarioActual();
        return new ResponseEntity<>(valor, HttpStatus.OK);
    }

    // RF-08.5: Análisis ABC (Pareto)
    @GetMapping("/analisis-abc")
    public ResponseEntity<List<ProductoABCTO>> getAnalisisABC() {
        List<ProductoABCTO> reporte = reporteService.analisisABC();
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

    // RF-08.6: Rentabilidad por Producto
    @GetMapping("/rentabilidad")
    public ResponseEntity<List<ProductoRentabilidadDTO>> getReporteRentabilidad() {
        List<ProductoRentabilidadDTO> reporte = reporteService.reporteRentabilidadPorProducto();
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

    // RF-08.7: Productos Próximos a Vencer
    @GetMapping("/proximo-vencimiento")
    public ResponseEntity<List<Producto>> getProductosProximosAVencer(
            @RequestParam(defaultValue = "15") int dias) {

        List<Producto> reporte = reporteService.reporteProductosProximosAVencer(dias);
        return new ResponseEntity<>(reporte, HttpStatus.OK);
    }

    // **NOTA: RF-08.4 y RF-08.8 deben implementarse después de validar los anteriores.**
}