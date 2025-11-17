package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.MovimientoInventario;
import com.universidadcartagena.api_proyect.service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventario/movimientos")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService movimientoService;

    // POST: RF-04.3 Registrar un movimiento y actualizar el stock
    @PostMapping
    public ResponseEntity<MovimientoInventario> registrarMovimiento(@RequestBody MovimientoInventario movimiento) {
        try {
            MovimientoInventario nuevoMovimiento = movimientoService.registrarMovimiento(movimiento);
            return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED); // 201 Created
        } catch (RuntimeException e) {
            // Captura errores específicos (ej. Stock insuficiente)
            // Aquí podríamos devolver un 400 Bad Request en lugar de 500
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // GET: RF-04.4 Consultar historial completo
    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listarMovimientos() {
        List<MovimientoInventario> movimientos = movimientoService.listarTodos();
        return new ResponseEntity<>(movimientos, HttpStatus.OK); // 200 OK
    }
}