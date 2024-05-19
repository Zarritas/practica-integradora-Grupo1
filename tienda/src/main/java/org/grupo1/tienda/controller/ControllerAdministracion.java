package org.grupo1.tienda.controller;

import org.grupo1.tienda.component.FiltradoParametrizado;
import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("admin")
public class ControllerAdministracion {
    private final String PREFIJO1 = "app/";

    private final ServicioSesion servicioSesion;
    private final UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl;
    private final ClienteServiceImpl clienteServiceImpl;
    private final MotivoBloqueoServiceImpl motivoBloqueoServiceImpl;
    private final TipoClienteServiceImpl tipoClienteServiceImpl;
    private final FiltradoParametrizado filtradoParametrizado;

    public ControllerAdministracion(ServicioSesion servicioSesion,
                                    UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl,
                                    ClienteServiceImpl clienteServiceImpl,
                                    MotivoBloqueoServiceImpl motivoBloqueoServiceImpl,
                                    TipoClienteServiceImpl tipoClienteServiceImpl,
                                    FiltradoParametrizado filtradoParametrizado) {
        this.servicioSesion = servicioSesion;
        this.usuarioEmpleadoClienteServiceImpl = usuarioEmpleadoClienteServiceImpl;
        this.clienteServiceImpl = clienteServiceImpl;
        this.motivoBloqueoServiceImpl = motivoBloqueoServiceImpl;
        this.tipoClienteServiceImpl = tipoClienteServiceImpl;
        this.filtradoParametrizado = filtradoParametrizado;
    }

    // Área personal de un usuario administrador.
    @GetMapping("administracion")
    public ModelAndView administracionGet(ModelAndView modelAndView) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
        } else {
            modelAndView.setViewName(PREFIJO1 + "administracion");
        }
        return modelAndView;
    }

    @PostMapping("administracion")
    public ModelAndView administracionPost(ModelAndView modelAndView) {
        // Desconexión ordenada.
        servicioSesion.setAdministradorLoggeado(null);
        modelAndView.setViewName("redirect:/usuario/authadmin");
        return modelAndView;
    }

    @GetMapping("listado-usuarios")
    public ModelAndView listarUsuariosGet(@ModelAttribute("flashAttribute") Object flashAttribute,
                                               ModelAndView modelAndView) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
            return modelAndView;
        }
        servicioSesion.setListaUsuariosEmpleadoCliente(usuarioEmpleadoClienteServiceImpl.devuelveUsuariosEmpleadoCliente());
        // Se pasa el listado de usuarios a la vista
        modelAndView.addObject("lista_usuariosClienteEmpleado", servicioSesion.getListaUsuariosEmpleadoCliente());
        // Se evalúa si este método ha recibido un atributo flash
        if (!flashAttribute.getClass().getSimpleName().equals("String")) {
            flashAttribute = null;
        }
        // Se pasa el atributo flash a la vista
        modelAndView.addObject("exito", flashAttribute);
        modelAndView.setViewName(PREFIJO1 + "listado_usuarios");
        return modelAndView;
    }

    @GetMapping("listado-clientes")
    public ModelAndView listarClientesGet(@ModelAttribute("flashAttribute") Object flashAttribute,
                                          ModelAndView modelAndView) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
            return modelAndView;
        }
        // Si no existe una lista de los tipos de cliente de la base de datos, se crea una.
        if (servicioSesion.getListaTiposCliente() == null) {
            servicioSesion.setListaTiposCliente(tipoClienteServiceImpl.devuelveTiposCliente());
        }
        // Se añade los tipos de cliente a la vista para que el administrador pueda filtar por uno de ellos.
        modelAndView.addObject("lista_tipo_cliente", servicioSesion.getListaTiposCliente());
        // Se actualiza la lista de clientes.
        servicioSesion.setListaClientes(clienteServiceImpl.devuelveClientes());
        // Se pasa el listado de clientes a la vista.
        modelAndView.addObject("lista_clientes", servicioSesion.getListaClientes());
        // Se evalúa si este método ha recibido un atributo flash
        if (!flashAttribute.getClass().getSimpleName().equals("String")) {
            flashAttribute = null;
        }
        // Se pasa el atributo flash a la vista
        modelAndView.addObject("exito", flashAttribute);
        modelAndView.setViewName(PREFIJO1 + "listado_clientes");
        return modelAndView;
    }

    @PostMapping("listado-clientes")
    public ModelAndView listarClientesPost(ModelAndView modelAndView,
                                           @RequestParam(value = "finicio", required = false) String finicio,
                                           @RequestParam(value = "ffin", required = false) String ffin,
                                           @RequestParam(value = "tcliente", required = false) String tipoCliente,
                                           @RequestParam(value = "dmin", required = false) String dmin,
                                           @RequestParam(value = "dmax", required = false) String dmax,
                                           @RequestParam(value = "apellido", required = false) String apellido,
                                           @RequestParam(value = "boton", required = false) String boton) {
        // Se añade los tipos de cliente a la vista para que el administrador pueda filtar por uno de ellos.
        modelAndView.addObject("lista_tipo_cliente", servicioSesion.getListaTiposCliente());
        // Se actualiza la lista de clientes según el filtrado elegido
        servicioSesion.setListaClientes(filtradoParametrizado.filtrarListaClientes(boton, finicio, ffin, tipoCliente, dmin, dmax, apellido));
        // Se pasa el listado de clientes a la vista.
        modelAndView.addObject("lista_clientes", servicioSesion.getListaClientes());
        modelAndView.setViewName(PREFIJO1 + "listado_clientes");
        return modelAndView;
    }

    @GetMapping("detalle/{id}")
    public ModelAndView detallarClienteGet(ModelAndView modelAndView,
                                           @PathVariable UUID id) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
            return modelAndView;
        }
        try {
            Cliente cliente = clienteServiceImpl.devuelveClientePorId(id);
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("readonly", true);
            modelAndView.addObject("action", "detalle");
            modelAndView.setViewName(PREFIJO1 + "detalle_cliente");
        } catch (NoEncontradoException e) {
            modelAndView.setViewName("redirect:/admin/listado-usuarios");
        }
        return modelAndView;
    }

    @PostMapping("detalle/{id}")
    public ModelAndView detallarClientePost(ModelAndView modelAndView,
                                            @PathVariable UUID id) {
        modelAndView.setViewName("redirect:/admin/listado-usuarios");
        return modelAndView;
    }

    @GetMapping("modificacion/{id}")
    public ModelAndView modificarClienteGet(ModelAndView modelAndView,
                                            @PathVariable UUID id) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
            return modelAndView;
        }
        try {
            Cliente cliente = clienteServiceImpl.devuelveClientePorId(id);
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("readonly", false);
            modelAndView.addObject("action", "modificacion");
            modelAndView.setViewName(PREFIJO1 + "detalle_cliente");
        } catch (NoEncontradoException e) {
            modelAndView.setViewName("redirect:/admin/listado-clientes");
        }
        return modelAndView;
    }

    @PostMapping("modificacion/{id}")
    public RedirectView modificarClientePost(RedirectAttributes redirectAttributes,
                                             @PathVariable UUID id,
                                             @ModelAttribute("cliente") Cliente cliente) {
        try {
            Cliente cli = clienteServiceImpl.devuelveClientePorId(id);
            cli.setNombre(cliente.getNombre());
            cli.setApellidos(cliente.getApellidos());
            cli.setTelefonoMovil(cliente.getTelefonoMovil());
            cli.setFechaNacimiento(cliente.getFechaNacimiento());
            clienteServiceImpl.actualizaCliente(id, cli);
            redirectAttributes.addFlashAttribute("flashAttribute", "Modificación del cliente realizada");
        } catch (NoEncontradoException e) {
            redirectAttributes.addFlashAttribute("flashAttribute", "No se ha podido modificar el cliente");
            return new RedirectView("/admin/listado-clientes");
        }
        return new RedirectView("/admin/listado-clientes");
    }

    @GetMapping("bloqueo/{id}")
    public ModelAndView bloquearUsuarioGet(ModelAndView modelAndView,
                                           @PathVariable UUID id) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
            return modelAndView;
        }
        // Si la sesión no tiene un listado de los motivos de bloqueo, se crea uno y se incluye en la sesión
        if (servicioSesion.getListaMotivosBloqueo() == null) {
            servicioSesion.setListaMotivosBloqueo(motivoBloqueoServiceImpl.devuelveMotivosBloqueo());
        }

        try {
            UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorId(id);
            modelAndView.addObject("usuario", uec);
            modelAndView.addObject("lista_motivos", servicioSesion.getListaMotivosBloqueo());
            modelAndView.setViewName(PREFIJO1 + "modificacion_bloqueo");
        } catch (NoEncontradoException e) {
            modelAndView.setViewName("redirect:/admin/listado-usuarios");
        }
        return modelAndView;
    }

    @PostMapping("bloqueo/{id}")
    public RedirectView bloquearUsuarioPost(RedirectAttributes redirectAttributes,
                                            @PathVariable UUID id,
                                            @ModelAttribute("motiv") String motiv) {
        try {
            UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorId(id);
            MotivoBloqueo motivoBloqueo = motivoBloqueoServiceImpl.devuelveMotivoBloqueoPorId(Long.valueOf(motiv));
            uec.setMotivoBloqueo(motivoBloqueo);
            uec.setFechaDesbloqueo(LocalDateTime.now().plusMinutes(motivoBloqueo.getMinutosBloqueo()));
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(uec.getId(), uec);
        } catch (NoEncontradoException e) {
            // Se devuleve un mensaje flash a la vista del listado de usuarios indicando el error.
            redirectAttributes.addFlashAttribute("flashAttribute", "No se ha podido bloquear el usuario");
        }
        return new RedirectView("/admin/listado-usuarios");
    }

    @GetMapping("nomina/{id}")
    public ModelAndView detallarNominasGet(ModelAndView modelAndView,
                                           @PathVariable UUID id) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministradorLoggeado() == null) {
            modelAndView.setViewName("redirect:/usuario/authadmin");
            return modelAndView;
        }
        try {
            Cliente cliente = clienteServiceImpl.devuelveClientePorId(id);
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("readonly", true);
            modelAndView.addObject("action", "detalle");
            modelAndView.setViewName(PREFIJO1 + "detalle_cliente");
        } catch (NoEncontradoException e) {
            modelAndView.setViewName("redirect:/admin/listado-usuarios");
        }

        return modelAndView;
    }
}
