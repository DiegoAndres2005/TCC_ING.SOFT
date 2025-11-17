package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.Proveedor;
import com.universidadcartagena.api_proyect.repository.ProveedorRepository;
import com.universidadcartagena.api_proyect.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        // Lógica: La validación de que los campos no estén vacíos iría aquí si se necesitara.
        return proveedorRepository.save(proveedor);
    }

    @Override
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Optional<Proveedor> obtenerPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public Proveedor actualizar(Long id, Proveedor proveedorActualizado) {
        Proveedor proveedorExistente = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        // Actualiza solo los campos que no son el ID
        proveedorExistente.setNombre(proveedorActualizado.getNombre());
        proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
        proveedorExistente.setEmail(proveedorActualizado.getEmail());
        proveedorExistente.setDireccion(proveedorActualizado.getDireccion());

        return proveedorRepository.save(proveedorExistente);
    }

    @Override
    public void eliminar(Long id) {
        // Nota: Por ahora, solo usamos la eliminación directa.
        // Si se implementa el RF-03.3 (historial de compras), esta eliminación debería ser una "desactivación" o soft delete.
        proveedorRepository.deleteById(id);
    }
}