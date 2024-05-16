package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.auxiliary.Direccion;
import org.grupo1.tienda.model.auxiliary.TarjetaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {
}
