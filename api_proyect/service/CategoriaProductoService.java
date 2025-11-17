package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.CategoriaProducto;
import java.util.List;
import java.util.Optional;

// Interfaz para la Gestión de Categorías (RF-02)
public interface CategoriaProductoService {

    // RF-02.1: Crear Categoría
    CategoriaProducto guardar(CategoriaProducto categoria);

    // RF-02.2: Listar Categorías
    List<CategoriaProducto> obtenerTodas();

    // Consultar Categoría por ID
    Optional<CategoriaProducto> obtenerPorId(Long id);

    // RF-02.2: Actualizar Categoría
    CategoriaProducto actualizar(Long id, CategoriaProducto categoria);

    // RF-02.2 y RF-02.3: Eliminar Categoría
    void eliminar(Long id);
}