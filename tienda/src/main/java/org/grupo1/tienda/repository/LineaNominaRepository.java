package org.grupo1.tienda.repository;

import org.grupo1.tienda.model.auxiliary.LineaNomina;
import org.grupo1.tienda.model.auxiliary.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineaNominaRepository extends JpaRepository<LineaNomina, Long> {
}
