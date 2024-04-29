package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.view.RedirectView;

@Component
@RequestScope
@AllArgsConstructor
@Data
public class AutentificacionUsuario {
    private final ServicioSesion servicioSesion;
    private UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;

    public Boolean usuarioRegistrado(String email, String clave) {
        if (servicioSesion.getMapaUsuariosEmpleadoCliente() == null) {
            servicioSesion.setListaUsuarioEmpleadoCliente(usuarioEmpleadoClienteRepository.findAll());
            servicioSesion.crearMapaUsuarios();
        }
        if (servicioSesion.getMapaUsuariosEmpleadoCliente().containsKey(email)) {
            if (servicioSesion.getMapaUsuariosEmpleadoCliente().get(email).equals(clave)) {
                servicioSesion.setUsuarioEmpleadoCliente(usuarioEmpleadoClienteRepository.findByEmailAndClave(email, clave));
                return true;
            }
        }
        return false;
    }
}
