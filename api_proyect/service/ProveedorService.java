package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorService {

    // 1. Crear Proveedor (RF-03.1)
    Proveedor guardar(Proveedor proveedor);

    // 2. Listar todos los Proveedores (RF-03.2)
    List<Proveedor> obtenerTodos();

    // 3. Consultar Proveedor por ID (RF-03.2)
    Optional<Proveedor> obtenerPorId(Long id);

    // 4. Actualizar Proveedor (RF-03.2)
    Proveedor actualizar(Long id, Proveedor proveedorActualizado);

    // 5. Eliminar Proveedor (o Desactivar, aunque no se use el campo 'estado' por ahora) (RF-03.2)
    void eliminar(Long id);
}