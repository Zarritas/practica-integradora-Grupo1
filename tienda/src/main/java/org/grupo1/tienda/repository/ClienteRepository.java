package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.catalog.TipoCliente;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Cliente findByUsuario(Usuario usuario);

    List<Cliente> findByFechaNacimientoIsBetween(LocalDate inicio, LocalDate fin);

    List<Cliente> findByTipoCliente(TipoCliente tipo);

    List<Cliente> findByGastoAcumuladoClienteIsBetween(BigDecimal min, BigDecimal max);

    List<Cliente> findByApellidosContaining(String apellido);

}
