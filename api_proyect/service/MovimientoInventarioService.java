package com.universidadcartagena.api_proyect.service;

import com.universidadcartagena.api_proyect.model.MovimientoInventario;
import java.util.List;

public interface MovimientoInventarioService {

    // 1. Registrar y actualizar stock (CRÍTICO: RF-04.3)
    MovimientoInventario registrarMovimiento(MovimientoInventario movimiento);

    // 2. Consultar historial (RF-04.4)
    List<MovimientoInventario> listarTodos();

    // (Los métodos de consulta por fecha/producto/tipo se agregarán después)
}