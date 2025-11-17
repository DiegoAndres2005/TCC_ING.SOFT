package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.EntradaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaInventarioRepository extends JpaRepository<EntradaInventario, Long> {
}