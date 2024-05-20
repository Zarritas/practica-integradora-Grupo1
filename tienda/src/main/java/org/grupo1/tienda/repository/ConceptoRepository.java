package org.grupo1.tienda.repository;

import org.grupo1.tienda.model.catalog.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptoRepository extends JpaRepository<Concepto, Long> {

    Concepto findByDenominacion(String denominacion);

    Boolean existsByDenominacion(String denominacion);

}
