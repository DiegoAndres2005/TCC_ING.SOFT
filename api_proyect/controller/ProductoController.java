package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService; // Inyecta la Interfaz del Servicio

    /**
     * Endpoint POST para RF-INV-001: Crear un nuevo producto.
     */
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED); // HTTP 201
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // HTTP 400
        }
    }

    /**
     * Endpoint GET para RF-INV-003: Listar todos los productos.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK); // HTTP 200
    }

    // ----------------------------------------------------------------------
    // RF-INV-003: CONSULTAR PRODUCTO POR ID (AÑADIDO PARA LA PRUEBA C.2)
    // ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {

        // Asumiendo que tu servicio tiene un método obtenerPorId que devuelve Producto o null/excepción
        Producto producto = productoService.obtenerPorId(id);

        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK); // HTTP 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // HTTP 404
        }
    }
}