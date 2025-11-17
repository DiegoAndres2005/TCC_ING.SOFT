package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.service.ProductoService;
import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Producto guardarProducto(Producto producto) {
        // LÓGICA DE NEGOCIO: El precio de venta debe ser mayor al costo de compra
        if (producto.getPrecioVenta().compareTo(producto.getCostoCompra()) <= 0) {
            throw new IllegalArgumentException("ERROR: El precio de venta debe ser mayor al costo de compra. Revise los datos.");
        }
        return productoRepository.save(producto);
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        // Usamos findById() del repositorio y .orElse(null) para devolver un Producto o null.
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto actualizarStock(Long id, Integer cantidad, String tipoMovimiento) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado."));

        Integer nuevoStock;

        if ("ENTRADA".equals(tipoMovimiento)) {
            nuevoStock = producto.getStock() + cantidad;
        } else if ("SALIDA".equals(tipoMovimiento) || "VENTA".equals(tipoMovimiento)) {
            nuevoStock = producto.getStock() - cantidad;

            // REGLA DE NEGOCIO CRÍTICA (RF-INV-002): No se permite stock negativo
            if (nuevoStock < 0) {
                throw new IllegalStateException("ERROR: La salida de " + cantidad + " unidades excede el stock disponible (" + producto.getStock() + "). Operación cancelada.");
            }
        } else {
            throw new IllegalArgumentException("Tipo de movimiento de stock no válido.");
        }

        producto.setStock(nuevoStock);
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}