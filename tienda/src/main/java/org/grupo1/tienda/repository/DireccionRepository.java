package org.grupo1.tienda.repository;

import org.grupo1.tienda.model.auxiliary.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
}
