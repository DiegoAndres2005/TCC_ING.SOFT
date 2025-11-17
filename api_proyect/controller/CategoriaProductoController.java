package com.universidadcartagena.api_proyect.controller;

import com.universidadcartagena.api_proyect.model.CategoriaProducto;
import com.universidadcartagena.api_proyect.service.CategoriaProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException; // Importación necesaria para manejar duplicados
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias") // Endpoint base: /api/categorias
@RequiredArgsConstructor
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaService;

    // POST: /api/categorias (Crea Categoría - RF-02.1)
    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaProducto categoria) {
        try {
            CategoriaProducto nuevaCategoria = categoriaService.guardar(categoria);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED); // 201
        } catch (DataIntegrityViolationException e) {
            // Maneja el error de nombre duplicado de la base de datos
            return new ResponseEntity<>("ERROR (RF-02.1): El nombre de la categoría ya existe y debe ser único.", HttpStatus.CONFLICT); // 409
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400
        }
    }

    // GET: /api/categorias (Lista Categorías - RF-02.2)
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> obtenerTodas() {
        List<CategoriaProducto> categorias = categoriaService.obtenerTodas();
        return new ResponseEntity<>(categorias, HttpStatus.OK); // 200
    }

    // PUT: /api/categorias/{id} (Actualiza Categoría - RF-02.2)
    @PutMapping("/{id}") // <-- ESTA ANOTACIÓN FALTABA
    public ResponseEntity<CategoriaProducto> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaProducto categoriaActualizada) {

        try {
            CategoriaProducto categoria = categoriaService.actualizar(id, categoriaActualizada);
            return new ResponseEntity<>(categoria, HttpStatus.OK); // 200
        } catch (RuntimeException e) {
            // Maneja el error si el ID no se encuentra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // DELETE: /api/categorias/{id} (Elimina Categoría - RF-02.2 y RF-02.3)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return new ResponseEntity<>("Categoría eliminada con éxito.", HttpStatus.NO_CONTENT); // 204
        } catch (IllegalStateException e) {
            // Captura el error de la Regla de Negocio (RF-02.3: tiene productos asociados)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409
        } catch (Exception e) {
            // Maneja el caso de que el ID no exista o cualquier otro error
            return new ResponseEntity<>("Error al eliminar la categoría.", HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}