package com.universidadcartagena.api_proyect.repository;

import com.universidadcartagena.api_proyect.model.SalidaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalidaInventarioRepository extends JpaRepository<SalidaInventario, Long> {
}