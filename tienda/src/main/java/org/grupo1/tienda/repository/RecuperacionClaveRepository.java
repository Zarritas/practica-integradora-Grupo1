package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecuperacionClaveRepository extends JpaRepository<RecuperacionClave, Long> {

}
