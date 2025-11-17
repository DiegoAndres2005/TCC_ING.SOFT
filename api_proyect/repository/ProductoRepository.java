package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // <-- ¡NUEVA IMPORTACIÓN REQUERIDA!
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método para la validación RF-02.3 (usado en Categoria)
    boolean existsByCategoriaId(Long id);

    // RF-06.2: Encuentra productos cuyo stock actual es menor que el stock mínimo.
    @Query("SELECT p FROM Producto p WHERE p.stock < p.stockMinimo")
    List<Producto> obtenerSugerenciaCompraBajoMinimo();

    /**
     * RF-08.7: Productos Próximos a Vencer
     * Busca productos cuya fecha de vencimiento sea ANTES (o igual) a la fecha límite.
     * Esto resuelve el error en ReporteServiceImpl.java.
     */
    List<Producto> findByFechaVencimientoBefore(LocalDate fechaLimite);
}