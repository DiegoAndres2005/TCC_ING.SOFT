package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}