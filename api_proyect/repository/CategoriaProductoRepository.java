package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
}