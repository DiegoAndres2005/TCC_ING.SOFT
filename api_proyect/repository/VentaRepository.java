package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.Venta;
import com.universidadcartagena.api_proyect.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Necesario para @Param
import org.springframework.stereotype.Repository;

import java.math.BigDecimal; // Necesario para el retorno del cálculo de suma
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // 1. RF-05.4: Consultar por rango de fechas (Método de consulta simple de Spring Data JPA)
    List<Venta> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // 2. RF-05.4: Consultar por método de pago (Método de consulta simple de Spring Data JPA)
    List<Venta> findByMetodoPago(MetodoPago metodoPago);

    // 3. RF-05.5: Calcular el total de ventas en un rango de fechas
    // Usamos JPQL para sumar el campo 'total' de la entidad Venta.
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.fechaHora BETWEEN :fechaInicio AND :fechaFin")
    BigDecimal sumarTotalVentasEnRango(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}