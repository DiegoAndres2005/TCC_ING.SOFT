package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.Proveedor;
import com.universidadcartagena.api_proyect.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores") // Nuevo Endpoint base: /api/proveedores
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    // POST: /api/proveedores (Crea Proveedor - RF-03.1)
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorService.guardar(proveedor);
        return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED); // 201
    }

    // GET: /api/proveedores (Lista Proveedores - RF-03.2)
    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodos() {
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        return new ResponseEntity<>(proveedores, HttpStatus.OK); // 200
    }

    // GET: /api/proveedores/{id} (Consulta Proveedor por ID - RF-03.2)
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable Long id) {
        return proveedorService.obtenerPorId(id)
                .map(proveedor -> new ResponseEntity<>(proveedor, HttpStatus.OK)) // 200
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404
    }

    // PUT: /api/proveedores/{id} (Actualiza Proveedor - RF-03.2)
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(
            @PathVariable Long id,
            @RequestBody Proveedor proveedorActualizado) {

        try {
            Proveedor proveedor = proveedorService.actualizar(id, proveedorActualizado);
            return new ResponseEntity<>(proveedor, HttpStatus.OK); // 200
        } catch (RuntimeException e) {
            // Maneja el error si el ID no se encuentra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // DELETE: /api/proveedores/{id} (Elimina Proveedor - RF-03.2)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }
}