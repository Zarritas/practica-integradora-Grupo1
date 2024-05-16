package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioEmpleadoClienteRepository extends JpaRepository<UsuarioEmpleadoCliente, UUID> {

    UsuarioEmpleadoCliente findByEmailAndClaveAndBajaIsFalse(String email, String clave);

    UsuarioEmpleadoCliente findByEmailAndBajaIsFalse(String email);

    UsuarioEmpleadoCliente findByEmailAndBajaIsTrue(String email);

}
