package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime; // ¡Importación sugerida si la necesitas para otros métodos!

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

    /**
     * RF-07.1c: Consulta para encontrar la última fecha de un movimiento de SALIDA/VENTA
     * CORRECCIÓN: Usamos 'fechaHora' y la convertimos a solo la fecha.
     */
    @Query("SELECT MAX(CAST(m.fechaHora AS LocalDate)) FROM MovimientoInventario m WHERE m.producto.id = :productoId AND m.tipoMovimiento = 'SALIDA'")
    LocalDate findUltimaFechaSalidaByProductoId(Long productoId);
}