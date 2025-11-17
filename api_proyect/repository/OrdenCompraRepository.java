package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.OrdenCompra;
import com.universidadcartagena.api_proyect.model.EstadoOrdenCompra; // Importación necesaria
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    // RF-06.4: Listar órdenes por estado
    List<OrdenCompra> findByEstado(EstadoOrdenCompra estado);

}