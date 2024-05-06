package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.AdministradorRepository;
import org.grupo1.tienda.repository.ClienteRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@AllArgsConstructor @Data
public class RegistroUsuario {
    private final ServicioSesion servicioSesion;
    private final UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private final AdministradorRepository administradorRepository;
    private final ClienteRepository clienteRepository;

    public Boolean usuarioRegistrado(String email, String clave) {
        UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteRepository.findByEmailAndClaveAndBajaIsFalse(email, clave);
        servicioSesion.setUsuarioEmpleadoCliente(uec);
        return uec != null;
    }

    public Boolean usuarioRegistrado(String email) {
        UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteRepository.findByEmailAndBajaIsFalse(email);
        return uec != null;
    }

    public Boolean usuarioAdminRegistrado(String email, String clave) {
        Administrador admin = administradorRepository.findByEmailAndClave(email, clave);
        servicioSesion.setAdministrador(admin);
        return admin != null;
    }

    public Boolean clienteRegistrado() {
        UsuarioEmpleadoCliente usuarioLoggeado = servicioSesion.getUsuarioEmpleadoCliente();
        Cliente cliente = clienteRepository.findByUsuario(usuarioLoggeado);
        return cliente != null;
    }


}
