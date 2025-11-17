package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Long> {

}