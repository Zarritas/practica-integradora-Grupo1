package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioEmpleadoClienteRepository extends JpaRepository<UsuarioEmpleadoCliente, UUID> {

    UsuarioEmpleadoCliente findByEmailAndClave(String email, String clave);
}
