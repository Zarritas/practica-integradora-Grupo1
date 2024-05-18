package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.ClienteRepository;
import org.grupo1.tienda.service.AdministradorServiceImp;
import org.grupo1.tienda.service.ClienteServiceImpl;
import org.grupo1.tienda.service.ServicioSesion;
import org.grupo1.tienda.service.UsuarioEmpleadoClienteServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequestScope
@AllArgsConstructor @Data
public class RegistroUsuario {
    private final ServicioSesion servicioSesion;
    private final UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImp;
    private final AdministradorServiceImp administradorServiceImp;
    private final ClienteServiceImpl clienteServiceImpl;

    public Boolean usuarioRegistrado(String email, ModelAndView modelAndView, String PREFIJO1) {
        try {
            usuarioEmpleadoClienteServiceImp.devuelveUsuarioEmpleadoClientePorEmail(email);
            modelAndView.addObject("usuarioYaRegistrado",
                    "Ya existe una cuenta asociada a ese email");
            modelAndView.setViewName(PREFIJO1 + "registro_usuario_empleado");
        } catch (NoEncontradoException e) {
            return false;
        }
        return true;
    }

    public Boolean usuarioAdminRegistrado(String email, String clave) {
        try {
            servicioSesion.setAdministradorLoggeado(administradorServiceImp.devuelveAdministradorPorEmailYClave(email, clave));
        } catch (NoEncontradoException e) {
            return false;
        }
        return true;
    }

    public Boolean clienteRegistrado() {
        try {
            clienteServiceImpl.devuelveClientePorUsuario(servicioSesion.getUsuarioLoggeado());
        } catch (NoEncontradoException e) {
            return false;
        }
        return true;
    }


}
