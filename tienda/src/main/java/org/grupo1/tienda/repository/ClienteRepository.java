package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Cliente findByUsuario(Usuario usuario);
}
