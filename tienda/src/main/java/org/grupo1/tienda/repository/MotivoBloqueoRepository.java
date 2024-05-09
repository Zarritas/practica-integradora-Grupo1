package org.grupo1.tienda.repository;

import org.grupo1.tienda.model.catalog.Genero;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotivoBloqueoRepository extends JpaRepository<MotivoBloqueo, Long> {
}
