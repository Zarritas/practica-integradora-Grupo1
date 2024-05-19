package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.grupo1.tienda.component.GestionCookies;
import org.grupo1.tienda.component.RegistroUsuario;
import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.service.ClienteServiceImpl;
import org.grupo1.tienda.service.ServicioSesion;
import org.grupo1.tienda.service.UsuarioEmpleadoClienteServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@Controller
@RequestMapping("tienda")
public class ControllerTienda {
    private final String PREFIJO1 = "app/";

    private final ServicioSesion servicioSesion;
    private final RegistroUsuario registroUsuario;
    private final UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl;
    private final ClienteServiceImpl clienteServiceImpl;
    private final GestionCookies gestionCookies;

    public ControllerTienda(ServicioSesion servicioSesion, RegistroUsuario registroUsuario,
                            UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl,
                            ClienteServiceImpl clienteServiceImpl, GestionCookies gestionCookies) {
        this.servicioSesion = servicioSesion;
        this.registroUsuario = registroUsuario;
        this.usuarioEmpleadoClienteServiceImpl = usuarioEmpleadoClienteServiceImpl;
        this.clienteServiceImpl = clienteServiceImpl;
        this.gestionCookies = gestionCookies;
    }

    // Área personal de un usuario cliente/empleado
    @GetMapping("area-personal")
    public ModelAndView areaPersonalGet(ModelAndView modelAndView,
                                        HttpServletResponse respuestaHttp,
                                        @CookieValue(name = "paginas-visitadas", defaultValue = "0") String contenidoCookie) {
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
            modelAndView.addObject("usuarioLogged", servicioSesion.getUsuarioLoggeado().getEmail());
            // Lógica de Cookie que aumenta en 1 el número de páginas visitadas por el usuario
            gestionCookies.aumentoPaginasPorUsuario(respuestaHttp, contenidoCookie);
            // Aumento del número de páginas por las que pasa el usuario en la sesión
            servicioSesion.incrementaNumeroPaginasVisitadas();
            modelAndView.addObject("numPaginas", servicioSesion.getNumeroPaginasVisitadas());
            // Se muestra el cliente
            try {
                Cliente cliente = clienteServiceImpl.devuelveClientePorUsuario(servicioSesion.getUsuarioLoggeado());
                modelAndView.addObject("cliente", cliente);
                modelAndView.addObject("readonly", true);
                modelAndView.addObject("action", "detalle");
                // Para no mostrar el botón de volver
                modelAndView.addObject("personal", "botón invisible");
            } catch (NoEncontradoException e) {
                modelAndView.setViewName("redirect:/alta-cliente/datos-personales");
            }
        } else {
            // Si no tiene un cliente aterriza en el registro del mismo.
            modelAndView.setViewName("redirect:/alta-cliente/datos-personales");
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

    @GetMapping("detalle/{id}")
    public ModelAndView detallarClienteGet(ModelAndView modelAndView,
                                           @PathVariable UUID id) {
        // Muestra los datos del cliente
        try {
            Cliente cliente = clienteServiceImpl.devuelveClientePorId(id);
            modelAndView.addObject("cliente", cliente);
            // No se pueden modificar los datos
            modelAndView.addObject("readonly", true);
            modelAndView.addObject("action", "detalle");
            modelAndView.setViewName(PREFIJO1 + "detalle_cliente");
        } catch (NoEncontradoException e) {
            modelAndView.setViewName("redirect:/admin/listado-usuarios");
        }
        return modelAndView;
    }

    @GetMapping("desconexion")
    public ModelAndView desconectarGet(ModelAndView modelAndView) {
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
        try {
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(uec.getId(), uec);
            servicioSesion.setUsuarioLoggeado(null);
            redirectAttributes.addFlashAttribute("borradoFlash", "Se ha borrado la cuenta correctamente");
        } catch (NoEncontradoException e) {
            redirectAttributes.addFlashAttribute("errorFlash", e.getMessage());
        }
        return new RedirectView("/usuario/authusuario");
    }

    @GetMapping("productos")
    public ModelAndView dirigirAProductos(ModelAndView modelAndView, HttpSession httpSession) {
        httpSession.setAttribute("admin", false);
        modelAndView.setViewName("redirect:http://productos.poketienda.com?session="+httpSession);
        return modelAndView;
    }

}
