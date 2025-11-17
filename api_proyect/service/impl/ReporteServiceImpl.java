package com.universidadcartagena.api_proyect.service.impl;

import com.universidadcartagena.api_proyect.model.*;
import com.universidadcartagena.api_proyect.model.dto.*; // Importa todos los DTOs de la carpeta
import com.universidadcartagena.api_proyect.repository.*;
import com.universidadcartagena.api_proyect.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList; // ¡Añadido para el error de línea 76!
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ProductoRepository productoRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final VentaRepository ventaRepository; // Si existe

    // RF-08.1: Productos Más Vendidos
    @Override
    @Transactional(readOnly = true)
    public List<ProductoVendidoDTO> reporteProductosMasVendidos(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(23, 59, 59);
        return detalleVentaRepository.findProductosMasVendidos(inicio, fin);
    }

    // RF-08.2: Productos de Baja Rotación
    @Override
    @Transactional(readOnly = true)
    public List<Producto> reporteProductosBajaRotacion(int dias) {
        LocalDate fechaLimite = LocalDate.now().minusDays(dias);

        // Simulación: Recorre todos los productos y verifica la última salida
        return productoRepository.findAll().stream()
                .filter(p -> {
                    LocalDate ultimaVenta = movimientoInventarioRepository.findUltimaFechaSalidaByProductoId(p.getId());
                    return ultimaVenta == null || ultimaVenta.isBefore(fechaLimite);
                })
                .collect(Collectors.toList());
    }

    // RF-08.3: Valor Total del Inventario
    @Override
    @Transactional(readOnly = true)
    public BigDecimal valorTotalInventarioActual() {
        return productoRepository.findAll().stream()
                .map(p -> p.getCostoCompra().multiply(new BigDecimal(p.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // RF-08.5: Análisis ABC (Pareto) - Lógica Compleja
    @Override
    @Transactional(readOnly = true)
    public List<ProductoABCTO> analisisABC() {
        // 1. Obtener ingresos por producto (ordenado desc)
        List<Object[]> ingresosPorProducto = detalleVentaRepository.findIngresoTotalPorProducto();

        // 2. Calcular el total general de ingresos
        BigDecimal ingresoTotalGeneral = ingresosPorProducto.stream()
                .map(obj -> (BigDecimal) obj[2])
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (ingresoTotalGeneral.compareTo(BigDecimal.ZERO) == 0) {
            return List.of(); // Evitar división por cero
        }

        // ¡Corrección del error de línea 76!
        List<ProductoABCTO> resultados = new ArrayList<>();
        BigDecimal acumulado = BigDecimal.ZERO;

        // Definir los umbrales de Pareto (80%, 95%, 100%)
        BigDecimal umbralA = new BigDecimal("0.80");
        BigDecimal umbralB = new BigDecimal("0.95"); // 80% + 15%

        // 3. Iterar y asignar categoría
        for (Object[] obj : ingresosPorProducto) {
            BigDecimal ingresoProducto = (BigDecimal) obj[2];
            BigDecimal porcentaje = ingresoProducto.divide(ingresoTotalGeneral, 4, RoundingMode.HALF_UP);

            acumulado = acumulado.add(porcentaje);

            ProductoABCTO abc = new ProductoABCTO();
            abc.setProductoId((Long) obj[0]);
            abc.setNombreProducto((String) obj[1]);
            abc.setIngresoTotal(ingresoProducto);
            abc.setPorcentajeAcumuladoIngreso(acumulado.multiply(new BigDecimal("100")));

            if (acumulado.compareTo(umbralA) <= 0) {
                abc.setCategoriaABC("A");
            } else if (acumulado.compareTo(umbralB) <= 0) {
                abc.setCategoriaABC("B");
            } else {
                abc.setCategoriaABC("C");
            }
            resultados.add(abc);
        }

        return resultados;
    }

    // RF-08.6: Reporte de Rentabilidad
    @Override
    @Transactional(readOnly = true)
    public List<ProductoRentabilidadDTO> reporteRentabilidadPorProducto() {
        return detalleVentaRepository.findRentabilidadPorProducto();
    }

    // RF-08.7: Productos Próximos a Vencer
    @Override
    @Transactional(readOnly = true)
    public List<Producto> reporteProductosProximosAVencer(int dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        // El método findByFechaVencimientoBefore ya debe existir en ProductoRepository
        return productoRepository.findByFechaVencimientoBefore(fechaLimite);
    }

    // RF-08.4: Ventas Totales
    @Override
    public List<Venta> reporteVentasTotales(String periodo) {
        // Lógica de filtrado por día, semana, mes, etc. (se asume implementación futura)
        return ventaRepository.findAll();
    }

    // RF-08.8: Resumen Diario de Ventas
    @Override
    public ResumenDiarioDTO resumenDiarioVentas(LocalDate fecha) {
        // Lógica para calcular el resumen (se asume implementación futura)
        return new ResumenDiarioDTO();
    }
}