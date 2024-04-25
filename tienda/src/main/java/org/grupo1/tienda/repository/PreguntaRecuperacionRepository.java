package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntaRecuperacionRepository extends JpaRepository<PreguntaRecuperacion, Long> {

}
