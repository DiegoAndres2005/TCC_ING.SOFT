package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.AlertaProducto;
import com.universidadcartagena.api_proyect.model.Producto;
import com.universidadcartagena.api_proyect.repository.ProductoRepository;
import com.universidadcartagena.api_proyect.repository.MovimientoInventarioRepository; // ¡Necesario para RF-07.1c!
import com.universidadcartagena.api_proyect.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {

    @Autowired
    private ProductoRepository productoRepository;

    // **Necesitarás este Repositorio para la alerta de baja rotación**
    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;

    // ----------------------------------------------------------------------
    // RF-07.1, RF-07.2: Generar y consultar todas las alertas activas
    // ----------------------------------------------------------------------
    @Override
    public List<AlertaProducto> generarAlertasActivas() {
        List<AlertaProducto> alertas = new ArrayList<>();
        List<Producto> productos = productoRepository.findAll();

        for (Producto p : productos) {

            // RF-07.1a: Stock bajo o igual al mínimo
            alertas.addAll(verificarStockBajo(p));

            // RF-07.1b: Próximo a vencer
            alertas.addAll(verificarVencimiento(p));

            // RF-07.1c: Baja rotación (No implementaremos la consulta SQL compleja aquí,
            // pero si la tuviéramos, usaríamos un método del repositorio como este:
            // if (productoRepository.noTieneVentasEnUltimosDias(p.getId(), 30))
            alertas.addAll(verificarBajaRotacion(p));
        }

        return alertas;
    }

    // --- MÉTODOS PRIVADOS DE LÓGICA DE ALERTA (RF-07.1) ---

    private List<AlertaProducto> verificarStockBajo(Producto p) {
        List<AlertaProducto> alertas = new ArrayList<>();
        if (p.getStock() <= p.getStockMinimo()) {
            alertas.add(
                    AlertaProducto.builder()
                            .tipoAlerta("STOCK_BAJO")
                            .productoAfectado(p)
                            .prioridad("ALTA")
                            .mensaje("Stock actual (" + p.getStock() + ") es menor o igual al mínimo (" + p.getStockMinimo() + ").")
                            .build()
            );
        }
        return alertas;
    }

    private List<AlertaProducto> verificarVencimiento(Producto p) {
        List<AlertaProducto> alertas = new ArrayList<>();
        if (p.getFechaVencimiento() != null) {
            long diasRestantes = LocalDate.now().until(p.getFechaVencimiento()).getDays();

            String prioridad = null;
            String mensaje = null;
            int diasAlerta = 0;

            if (diasRestantes <= 3) {
                diasAlerta = 3;
                prioridad = "ALTA";
            } else if (diasRestantes <= 7) {
                diasAlerta = 7;
                prioridad = "MEDIA";
            } else if (diasRestantes <= 15) {
                diasAlerta = 15;
                prioridad = "BAJA";
            }

            if (prioridad != null) {
                mensaje = "Producto vence en " + diasRestantes + " días (" + p.getFechaVencimiento() + ").";
                alertas.add(
                        AlertaProducto.builder()
                                .tipoAlerta("PROXIMO_VENCIMIENTO")
                                .productoAfectado(p)
                                .prioridad(prioridad)
                                .mensaje(mensaje)
                                .build()
                );
            }
        }
        return alertas;
    }

    private List<AlertaProducto> verificarBajaRotacion(Producto p) {
        List<AlertaProducto> alertas = new ArrayList<>();

        // **Lógica de Baja Rotación (RF-07.1c):**
        // Requiere una consulta al repositorio para buscar la última fecha de movimiento de SALIDA/VENTA

        // Asumiendo que tenemos un método en MovimientoInventarioRepository para esto:
        LocalDate fechaLimite = LocalDate.now().minusDays(30);

        // Necesitas implementar este método en tu MovimientoInventarioRepository.java:
        LocalDate ultimaVenta = movimientoInventarioRepository.findUltimaFechaSalidaByProductoId(p.getId());

        if (ultimaVenta == null || ultimaVenta.isBefore(fechaLimite)) {
            alertas.add(
                    AlertaProducto.builder()
                            .tipoAlerta("BAJA_ROTACION")
                            .productoAfectado(p)
                            .prioridad("BAJA")
                            .mensaje("No hay movimientos de venta en los últimos 30 días.")
                            .build()
            );
        }
        return alertas;
    }
}