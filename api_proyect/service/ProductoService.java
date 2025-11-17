package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.Producto;
import java.util.List;
import java.util.Optional; // Mantén esta importación si la usas en otros métodos

// Una interfaz es un CONTRATO (solo define métodos, no los implementa)
public interface ProductoService {

    // RF-INV-001: Guardar un nuevo producto
    Producto guardarProducto(Producto producto);

    // RF-INV-003: Obtener lista de productos
    List<Producto> obtenerTodosLosProductos();

    // RF-INV-003: Obtener producto por ID (Método que necesita el Controller)
    Producto obtenerPorId(Long id); // <-- ¡SOLO ESTE MÉTODO DEBE EXISTIR!

    // RF-INV-002: Actualizar el stock
    Producto actualizarStock(Long id, Integer cantidad, String tipoMovimiento);

    // Eliminar producto
    void eliminarProducto(Long id);
}