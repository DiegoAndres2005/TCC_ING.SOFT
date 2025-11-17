package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.CategoriaProducto;
import com.universidadcartagena.api_proyect.repository.CategoriaProductoRepository;
import com.universidadcartagena.api_proyect.repository.ProductoRepository;
import com.universidadcartagena.api_proyect.service.CategoriaProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;
    private final ProductoRepository productoRepository;

    @Override
    public CategoriaProducto guardar(CategoriaProducto categoria) {
        return categoriaProductoRepository.save(categoria);
    }

    @Override
    public List<CategoriaProducto> obtenerTodas() {
        return categoriaProductoRepository.findAll();
    }

    @Override
    public Optional<CategoriaProducto> obtenerPorId(Long id) {
        return categoriaProductoRepository.findById(id);
    }

    // MÉTODO DE ACTUALIZACIÓN (Implementación requerida)
    @Override
    public CategoriaProducto actualizar(Long id, CategoriaProducto categoriaActualizada) {
        CategoriaProducto categoriaExistente = categoriaProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

        categoriaExistente.setNombre(categoriaActualizada.getNombre());
        // Asumiendo que tienes un campo 'descripcion' en el modelo
        // categoriaExistente.setDescripcion(categoriaActualizada.getDescripcion());

        return categoriaProductoRepository.save(categoriaExistente);
    }

    @Override
    public void eliminar(Long id) {
        // REGLA DE NEGOCIO (RF-02.3)
        boolean tieneProductos = productoRepository.existsByCategoriaId(id);

        if (tieneProductos) {
            throw new IllegalStateException("ERROR (RF-02.3): No se puede eliminar la categoría con ID " + id + " porque tiene productos asociados.");
        }

        categoriaProductoRepository.deleteById(id);
    }
}