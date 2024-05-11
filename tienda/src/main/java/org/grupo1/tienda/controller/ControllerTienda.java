package org.grupo1.tienda.controller;

import org.grupo1.tienda.component.RegistroUsuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("tienda")
public class ControllerTienda {
    private final String PREFIJO1 = "app/";

    private final ServicioSesion servicioSesion;
    private final RegistroUsuario registroUsuario;
    private final UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;

    public ControllerTienda(ServicioSesion servicioSesion, RegistroUsuario registroUsuario,
                            UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository) {
        this.servicioSesion = servicioSesion;
        this.registroUsuario = registroUsuario;
        this.usuarioEmpleadoClienteRepository = usuarioEmpleadoClienteRepository;
    }

    // Área personal de un usuario cliente/empleado
    @GetMapping("area-personal")
    public ModelAndView areaPersonalGet(ModelAndView modelAndView) {
        // Si se accede al área personal sin iniciar sesión correctamente.
        if (servicioSesion.getUsuarioLoggeado() == null) {
            // Si NO se ha llevado a cabo una desconexión ordenada.
            if (servicioSesion.getUsuarioParaLogin() == null) {
                modelAndView.setViewName("redirect:/usuario/authusuario");
            } else {
                // Si se ha llevado a cabo una desconexión ordenada.
                modelAndView.setViewName("redirect:/usuario/authclave");
            }
            return modelAndView;
        }
        // Si el usuario tiene registrado un cliente para realizar compras en la aplicación.
        if (registroUsuario.clienteRegistrado()) {
            modelAndView.setViewName(PREFIJO1 + "area_personal");
        } else {
            // Si no tiene un cliente aterriza en el registro del mismo.
            //modelAndView.setViewName("redirect:/alta-cliente/datos-personales");
            modelAndView.setViewName(PREFIJO1 + "area_personal");
        }
        return modelAndView;
    }

    @PostMapping("area-personal")
    public ModelAndView areaPersonalPost(ModelAndView modelAndView) {
        // Desconexión ordenada.
        servicioSesion.setUsuarioLoggeado(null);
        modelAndView.setViewName("redirect:/usuario/authclave");
        return modelAndView;
    }

    // Borrado de la cuenta de un cliente/empleado
    @PostMapping("borrar")
    public RedirectView borradoCuentaPost(RedirectAttributes redirectAttributes) {
        UsuarioEmpleadoCliente uec = servicioSesion.getUsuarioLoggeado();
        uec.setBaja(true);
        uec.setConfirmarClave(uec.getClave());
        usuarioEmpleadoClienteRepository.save(uec);
        servicioSesion.setUsuarioLoggeado(null);
        redirectAttributes.addFlashAttribute("borradoFlash", "Se ha borrado la cuenta correctamente");
        return new RedirectView("/usuario/authusuario");
    }

}
