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
import org.springframework.web.servlet.ModelAndView;

@Component
@RequestScope
@AllArgsConstructor @Data
public class RegistroUsuario {
    private final ServicioSesion servicioSesion;
    private final UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private final AdministradorRepository administradorRepository;
    private final ClienteRepository clienteRepository;

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoCliente(String email) {
        return usuarioEmpleadoClienteRepository.findByEmailAndBajaIsFalse(email);
    }

    public Boolean usuarioRegistrado(String email, ModelAndView modelAndView, String PREFIJO1) {
        UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteRepository.findByEmailAndBajaIsFalse(email);
        if (uec != null) {
            modelAndView.addObject("usuarioYaRegistrado",
                    "Ya existe una cuenta asociada a ese email");
            modelAndView.setViewName(PREFIJO1 + "registro_usuario_empleado");
        }
        return uec != null;
    }

    public UsuarioEmpleadoCliente
    usuarioBorradoRegistrado(String email) {
        return usuarioEmpleadoClienteRepository.findByEmailAndBajaIsTrue(email);
    }

    public Boolean usuarioAdminRegistrado(String email, String clave) {
        Administrador admin = administradorRepository.findByEmailAndClave(email, clave);
        servicioSesion.setAdministradorLoggeado(admin);
        return admin != null;
    }

    public Boolean clienteRegistrado() {
        UsuarioEmpleadoCliente usuarioLoggeado = servicioSesion.getUsuarioLoggeado();
        Cliente cliente = clienteRepository.findByUsuario(usuarioLoggeado);
        return cliente != null;
    }


}
