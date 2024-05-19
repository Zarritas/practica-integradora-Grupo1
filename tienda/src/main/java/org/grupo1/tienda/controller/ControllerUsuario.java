package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import org.grupo1.tienda.component.AutentificacionUsuario;
import org.grupo1.tienda.component.GestionCookies;
import org.grupo1.tienda.component.RegistroUsuario;
import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("usuario")
public class ControllerUsuario {
    private final String PREFIJO1 = "signin/";
    private final String PREFIJO2 = "login/";
    private final String PREFIJO3 = "app/";

    // Se agregan los repositorios como campos del controlador.
    private final ServicioSesion servicioSesion;
    private final RegistroUsuario registroUsuario;
    private final AutentificacionUsuario autentificacionUsuario;
    private final PreguntaRecuperacionServiceImpl preguntaRecuperacionServiceImpl;
    private final UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl;
    private final RecuperacionClaveServiceImpl recuperacionClaveServiceImpl;
    private final GestionCookies gestionCookies;

    // Los repositorios necesitan ser inicializados en el controlador.
    public ControllerUsuario(PreguntaRecuperacionServiceImpl preguntaRecuperacionServiceImpl,
                             ServicioSesion servicioSesion, RegistroUsuario registroUsuario,
                             AutentificacionUsuario autentificacionUsuario,
                             UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl,
                             RecuperacionClaveServiceImpl recuperacionClaveServiceImpl, GestionCookies gestionCookies) {
        this.preguntaRecuperacionServiceImpl = preguntaRecuperacionServiceImpl;
        this.servicioSesion = servicioSesion;
        this.registroUsuario = registroUsuario;
        this.autentificacionUsuario = autentificacionUsuario;
        this.usuarioEmpleadoClienteServiceImpl = usuarioEmpleadoClienteServiceImpl;
        this.recuperacionClaveServiceImpl = recuperacionClaveServiceImpl;
        this.gestionCookies = gestionCookies;
    }

    // Registro de un usuario cliente/empleado.
    @GetMapping("registro")
    public ModelAndView registroUsuarioGet(ModelAndView modelAndView,
                                           @ModelAttribute("usuario") Usuario usuario,
                                           @ModelAttribute("recuperacionClave") RecuperacionClave recuperacionClave) {
        // Si la lista de preguntas para la recuperación de contraseña no está en la sesión se añade.
        // Una vez recogida en la sesión ya no se volverá a hacer llamadas inecesarias a la base de datos.
        if (servicioSesion.getListaPreguntasRecuperacion() == null) {
            servicioSesion.setListaPreguntasRecuperacion(preguntaRecuperacionServiceImpl.devuelvePreguntasRecuperacion());
        }
        // Se mete la lista de preguntas de recuperación de contraseña para que se muestre en la vista.
        modelAndView.addObject("lista_preguntas", servicioSesion.getListaPreguntasRecuperacion());
        modelAndView.setViewName(PREFIJO1 + "registro_usuario_empleado");
        return modelAndView;
    }

    @PostMapping("registro")
    public ModelAndView registroUsuarioPost(ModelAndView modelAndView,
                                           @Valid @ModelAttribute("usuario") Usuario usuario,
                                           BindingResult bindingResult1,
                                           @Valid @ModelAttribute("recuperacionClave") RecuperacionClave recuperacionClave,
                                           BindingResult bindingResult2) {
        // Se añade la lista de preguntas de recuperación de contraseña para que las pinte la vista.
        modelAndView.addObject("lista_preguntas", servicioSesion.getListaPreguntasRecuperacion());
        // Si hay errores, se vuleve a mostrar el formulario con los errores.
        if (bindingResult1.hasErrors() || bindingResult2.hasErrors()) {
            modelAndView.addObject("mensaje", "Errores en el registro");
            // Si el email ya está registrado en la base de datos se muestra el error.
            registroUsuario.usuarioRegistrado(usuario.getEmail(), modelAndView, PREFIJO1);
            modelAndView.setViewName(PREFIJO1 + "registro_usuario_empleado");
            return modelAndView;
        }
        // Si el email ya está registrado en la base de datos se muestra el error.
        if (registroUsuario.usuarioRegistrado(usuario.getEmail(), modelAndView, PREFIJO1)) {
            return modelAndView;
        }
        // Se crea un usuario cliente/empleado.
        UsuarioEmpleadoCliente uec;
        // Si el usuario se había dado de baja se actualiza con los nuevos valores.
        try {
            uec = usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorEmailYBajaTrue(usuario.getEmail());
            uec.setClave(usuario.getClave());
            uec.setConfirmarClave(usuario.getConfirmarClave());
            uec.setRecuperacionClave(recuperacionClave);
            uec.setBaja(false);
        } catch (NoEncontradoException e) {
            // Si no se había dado de baja se crea un usuario cliente/empleado.
            uec = new UsuarioEmpleadoCliente(usuario.getEmail(), usuario.getClave(),
                    usuario.getConfirmarClave(), recuperacionClave);
        }
        // Se guarda la recuperación de clave y el usuario cliente/empleado en la base de datos.
        recuperacionClaveServiceImpl.aniadeRecuperacionClave(recuperacionClave);
        usuarioEmpleadoClienteServiceImpl.aniadeUsuarioEmpleadoCliente(uec);
        modelAndView.setViewName("redirect:authusuario");
        return modelAndView;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////

    // Autentificación de un usuario cliente/empleado.
    @GetMapping("authusuario")
    public ModelAndView autentificacionUsuarioGet(ModelAndView modelAndView,
                                                  @ModelAttribute("errorFlash") Object flashAttribute1,
                                                  @ModelAttribute("borradoFlash") Object flashAttribute2/*,
                                                  @RequestHeader(value = "referer", required = false) final String referer*/) {
        /*// Si el usuario acaba de registrarse se muestra un mensaje de éxito en el registro
        if (referer != null && referer.contains("registro")) {
            modelAndView.addObject("borradoCuenta", "Usuario registrado con éxito");
        }*/
        // Se evalúa si este método ha recibido un atributo flash
        if (flashAttribute1.getClass().getSimpleName().equals("String")) {
            // Se pasa el atributo flash a la vista
            modelAndView.addObject("mensajeError", flashAttribute1);
        }
        // Se evalúa si este método ha recibido un atributo flash
        if (flashAttribute2.getClass().getSimpleName().equals("String")) {
            // Se pasa el atributo flash a la vista
            modelAndView.addObject("borradoCuenta", flashAttribute2);
        }
        modelAndView.setViewName(PREFIJO2 + "autentificacion_usuario");
        return modelAndView;
    }

    @PostMapping("authusuario")
    public ModelAndView autentificacionUsuarioPost(ModelAndView modelAndView,
                                                   @RequestParam("email") String email) {
        // Se guarda el usuario en la sesión si está en la base de datos y no se ha dado de baja.
        try {
            servicioSesion.setUsuarioParaLogin(usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorEmail(email));
        } catch (NoEncontradoException e) {
            servicioSesion.setUsuarioParaLogin(null);
        }
        modelAndView.setViewName("redirect:authclave");
        return modelAndView;
    }

    @GetMapping("authclave")
    public ModelAndView autentificacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "autentificacion_clave");
        return modelAndView;
    }

    @PostMapping("authclave")
    public RedirectView autentificacionClavePost(RedirectAttributes redirectAttributes,
                                                 HttpServletResponse respuestaHttp,
                                                 @CookieValue(name = "autentificaciones", defaultValue = "0") String contenidoCookie,
                                                 @CookieValue(name = "paginas-visitadas", defaultValue = "0") String contenidoCookiePaginas,
                                                 @RequestParam("clave") String clave) throws NoEncontradoException {
        // Si la fecha de bloqueo fue hace menos de 15 min se bloquea la sesión (Thread.sleep).
        // En una aplicación más grande habría que liberar el hilo (wait)
        if (servicioSesion.getFechaBloqueo() != null &&
                ChronoUnit.MILLIS.between(servicioSesion.getFechaBloqueo(), LocalDateTime.now()) < 900000) {
            return autentificacionUsuario.bloqueoSesion(redirectAttributes);
        }

        // Si no existe el usuario o no se ha indicado uno
        if (servicioSesion.getUsuarioParaLogin() == null) {
            // Se incrementa el número de intentos de inicio de sesión no satisfactorios.
            servicioSesion.incrementaIntentos();
            // Si se ha llegado a 3 autentificaciones fallidas se informa que se bloquea la sesión.
            if (servicioSesion.getIntentosInicioSesion() == 3) {
                return autentificacionUsuario.informacionBloqueoSesion(redirectAttributes);
            }
            // Se devuleve un mensaje flash a la vista del login de usuario indicando el error.
            redirectAttributes.addFlashAttribute("errorFlash",
                    "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authusuario");
        }

        // Si el usuario está bloqueado y vuelve al login de usuario.
        if (servicioSesion.getUsuarioParaLogin().getFechaDesbloqueo() != null &&
                ChronoUnit.MILLIS.between(LocalDateTime.now(), servicioSesion.getUsuarioParaLogin().getFechaDesbloqueo()) > 0) {
            // Se mostrará que el usuario está bloqueado y cuantos minutos quedan para el desbloqueo del mismo.
            redirectAttributes.addFlashAttribute("errorFlash", "El usuario se desbloqueará en " +
                    ChronoUnit.MINUTES.between(LocalDateTime.now(), servicioSesion.getUsuarioParaLogin().getFechaDesbloqueo()) +
                    " minutos");
            return new RedirectView("authusuario");
        }

        // Se comprueba que el usuario y la contraseña se coresponden.
        if (servicioSesion.getUsuarioParaLogin().getClave().equals(clave)) {
            // Se guarda el usuario que se ha autentificado para poder recogerlo de la sesión.
            servicioSesion.setUsuarioLoggeado(servicioSesion.getUsuarioParaLogin());
            // Lógica de cookie que cuenta el número de accesos por usuario.
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(servicioSesion.getUsuarioLoggeado().getId(),
                    gestionCookies.numeroAccesosPorUsuario(respuestaHttp, contenidoCookie));
            //Lógica de cookie que pone a 0 el número de páginas visitadas por el usuario.
            gestionCookies.reseteoNumeroPaginas(respuestaHttp, contenidoCookiePaginas);
            // También se pone a 0 el número de páginas visitadas por el usuario en la sesión.
            servicioSesion.setNumeroPaginasVisitadas(0);
            return new RedirectView("/tienda/area-personal");
        } else {
            // Se incrementa en uno el número de intentos de inicio de sesión no satisfactorios.
            servicioSesion.getUsuarioParaLogin().setIntentosFallidosLogin(servicioSesion.getUsuarioParaLogin().getIntentosFallidosLogin() + 1);
            // Si se ha llegado a 3 autentificaciones fallidas se bloquea el usuario.
            if (servicioSesion.getUsuarioParaLogin().getIntentosFallidosLogin() == 3) {
                return autentificacionUsuario.bloqueoUsuario(redirectAttributes);
            }
            // Se guarda en la base de datos el intento fallido de inicio de sesión por el usuario.
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(servicioSesion.getUsuarioParaLogin().getId(),
                    servicioSesion.getUsuarioParaLogin());
            // Si no se corresponde la contraseña con el usuario se devuleve un mensaje flash a la vista del login
            // de usuario indicando el error.
            redirectAttributes.addFlashAttribute("errorFlash",
                    "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authusuario");
        }
    }

    // Recuperación de la contraseña.
    @GetMapping("recuperar")
    public ModelAndView recuperacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO3 + "recuperacion_clave");
        return modelAndView;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////

    // Autentificación de un usuario administrador
    @GetMapping("authadmin")
    public ModelAndView autentificacionAdminGet(@ModelAttribute("flashAttribute") Object flashAttribute,
                                                ModelAndView modelAndView) {
        // Se evalúa si este método ha recibido un atributo flash
        if (flashAttribute.getClass().getSimpleName().equals("String")) {
            // Se pasa el atributo flash a la vista
            modelAndView.addObject("mensajeError", flashAttribute);
        }
        modelAndView.setViewName(PREFIJO2 + "autentificacion_admin");
        return modelAndView;
    }

    @PostMapping("authadmin")
    public RedirectView autentificacionAdminPost(RedirectAttributes redirectAttributes,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("clave") String clave) {
        // Si no se ha introducido uno de los campos se vuelve a la vista de login con un mensaje flash indicándolo.
        if (email == null || clave == null) {
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authadmin");
        }
        // Se comprueba que el usuario y la contraseña son correctos y se guarda el usuario autentificado en la sesión.
        if (registroUsuario.usuarioAdminRegistrado(email, clave)) {
            return new RedirectView("/admin/administracion");
        } else {
            // Si el email no se corresponde con la contraseña se indica el error con un mensaje flash.
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authadmin");
        }
    }


}
