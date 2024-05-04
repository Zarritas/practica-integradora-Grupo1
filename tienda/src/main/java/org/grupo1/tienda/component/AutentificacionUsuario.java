package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.AdministradorRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component
@RequestScope
@AllArgsConstructor @Data
public class AutentificacionUsuario {
    private final ServicioSesion servicioSesion;
    private UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private AdministradorRepository administradorRepository;

    public Boolean usuarioRegistrado(String email, String clave) {
        servicioSesion.setListaUsuariosEmpleadoCliente(usuarioEmpleadoClienteRepository.findAll());
        servicioSesion.crearMapaUsuarios();
        if (servicioSesion.getMapaUsuariosEmpleadoCliente().containsKey(email)) {
            if (servicioSesion.getMapaUsuariosEmpleadoCliente().get(email).equals(clave)) {
                servicioSesion.setUsuarioEmpleadoCliente(usuarioEmpleadoClienteRepository.findByEmailAndClave(email, clave));
                return true;
            }
        }
        return false;
    }

    public Boolean usuarioRegistrado(String email) {
        servicioSesion.setListaUsuariosEmpleadoCliente(usuarioEmpleadoClienteRepository.findAll());
        List<String> emails = servicioSesion.getListaUsuariosEmpleadoCliente().stream().map(UsuarioEmpleadoCliente::getEmail).toList();
        return emails.contains(email);
    }

    public Boolean usuarioAdminRegistrado(String email, String clave) {
        servicioSesion.setListaUsuariosAdmin(administradorRepository.findAll());
        servicioSesion.crearMapaUsuariosAdmin();
        if (servicioSesion.getMapaUsuariosAdmin().containsKey(email)) {
            if (servicioSesion.getMapaUsuariosAdmin().get(email).equals(clave)) {
                servicioSesion.setAdministrador(administradorRepository.findByEmailAndClave(email, clave));
                return true;
            }
        }
        return false;
    }

}
