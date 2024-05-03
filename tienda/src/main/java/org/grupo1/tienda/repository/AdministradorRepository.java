package org.grupo1.tienda.repository;


import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {

    Administrador findByEmailAndClave(String email, String clave);
}
