package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.MovimientoInventario;
import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.repository.MovimientoInventarioRepository;
import com.universidadcartagena.api_proyect.repository.ProductoRepository;
import com.universidadcartagena.api_proyect.service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public MovimientoInventario registrarMovimiento(MovimientoInventario movimiento) {

        Long productoId = movimiento.getProducto().getId();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        Integer cantidadMovimiento = movimiento.getCantidad();

        // ¡CORRECCIÓN CLAVE! Usamos getStock() en lugar de getStockActual()
        Integer nuevoStock = producto.getStock();

        switch (movimiento.getTipoMovimiento()) {
            case ENTRADA:
            case AJUSTE_POSITIVO:
                nuevoStock += cantidadMovimiento;
                break;
            case SALIDA:
            case AJUSTE_NEGATIVO:
                if (nuevoStock < cantidadMovimiento) {
                    throw new RuntimeException("Stock insuficiente para realizar este movimiento (Producto: " + producto.getNombre() + ").");
                }
                nuevoStock -= cantidadMovimiento;
                break;
            default:
                throw new IllegalArgumentException("Tipo de movimiento no válido.");
        }

        // ¡CORRECCIÓN CLAVE! Usamos setStock() en lugar de setStockActual()
        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        return movimientoRepository.save(movimiento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventario> listarTodos() {
        return movimientoRepository.findAll();
    }
}