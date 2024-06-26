package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.auxiliary.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
}
