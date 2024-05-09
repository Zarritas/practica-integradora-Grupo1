package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import org.grupo1.tienda.component.ManejoCookieVisitas;
import org.grupo1.tienda.component.RegistroUsuario;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.MotivoBloqueoRepository;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import jakarta.servlet.http.Cookie;

@Controller
@RequestMapping("usuario")
public class ControllerUsuario {
    private final String PREFIJO1 = "singin/";
    private final String PREFIJO2 = "login/";
    private final String PREFIJO3 = "app/";

    // Se agregan los repositorios como campos del controlador.
    private final ServicioSesion servicioSesion;
    private final RegistroUsuario registroUsuario;
    private final PreguntaRecuperacionRepository preguntaRecuperacionRepository;
    private final UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private final RecuperacionClaveRepository recuperacionClaveRepository;
    private final MotivoBloqueoRepository motivoBloqueoRepository;

    // Los repositorios necesitan ser inicializados en el controlador.
    public ControllerUsuario(PreguntaRecuperacionRepository preguntaRecuperacionRepository,
                             ServicioSesion servicioSesion, RegistroUsuario registroUsuario,
                             UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository,
                             RecuperacionClaveRepository recuperacionClaveRepository, MotivoBloqueoRepository motivoBloqueoRepository) {
        this.preguntaRecuperacionRepository = preguntaRecuperacionRepository;
        this.servicioSesion = servicioSesion;
        this.registroUsuario = registroUsuario;
        this.usuarioEmpleadoClienteRepository = usuarioEmpleadoClienteRepository;
        this.recuperacionClaveRepository = recuperacionClaveRepository;
        this.motivoBloqueoRepository = motivoBloqueoRepository;
    }

    // Registro de un usuario cliente/empleado.
    @GetMapping("registro")
    public ModelAndView registroUsuarioGet(ModelAndView modelAndView,
                                           @ModelAttribute("usuario") Usuario usuario,
                                           @ModelAttribute("recuperacionClave") RecuperacionClave recuperacionClave) {
        // Si la lista de preguntas para la recuperación de contraseña no está en la sesión se añade.
        // Una vez recogida en la sesión ya no se volverá a hacer llamadas inecesarias a la base de datos.
        if (servicioSesion.getListaPreguntasRecuperacion() == null) {
            servicioSesion.setListaPreguntasRecuperacion(preguntaRecuperacionRepository.findAll());
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
            if (registroUsuario.usuarioRegistrado(usuario.getEmail())) {
                modelAndView.addObject("usuarioYaRegistrado",
                        "Ya existe una cuenta asociada a ese email");
            }
            modelAndView.setViewName(PREFIJO1 + "registro_usuario_empleado");
            return modelAndView;
        }
        // Se crea un usuario cleinte/empleado.
        UsuarioEmpleadoCliente uec = new UsuarioEmpleadoCliente(usuario.getEmail(), usuario.getClave(),
                usuario.getConfirmarClave(), recuperacionClave);
        // Se guarda la recuperación de clave y el usuario cliente/empleado en la base de datos.
        recuperacionClaveRepository.save(recuperacionClave);
        usuarioEmpleadoClienteRepository.save(uec);
        modelAndView.setViewName("redirect:authusuario");
        return modelAndView;
    }

    // Autentificación de un usuario cliente/empleado.
    @GetMapping("authusuario")
    public ModelAndView autentificacionUsuarioGet(ModelAndView modelAndView,
                                                  @ModelAttribute("errorFlash") Object flashAttribute1,
                                                  @ModelAttribute("borradoFlash") Object flashAttribute2) {
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
        servicioSesion.setUsuarioParaLogin(registroUsuario.devuelveUsuarioEmpleadoCliente(email));
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
                                                 @RequestParam("clave") String clave) {
        // Si no existe el usuario o no se ha indicado uno
        if (servicioSesion.getUsuarioParaLogin() == null) {
            // Se incrementa el número de intentos de inicio de sesión no satisfactorios.
            servicioSesion.incrementaIntentos();
            // Si se ha llegado a 3 autentificaciones fallidas se bloquea la sesión.
            if (servicioSesion.getIntentosInicioSesion() == 3) {
                servicioSesion.setIntentosInicioSesion(0);
                servicioSesion.setFechaBloqueo(LocalDateTime.now());
                servicioSesion.setMotivoBloqueo("demasiados intentos de sesión");
                redirectAttributes.addFlashAttribute("errorFlash",
                        "Sesión bloqueada por " + servicioSesion.getMotivoBloqueo());
                return new RedirectView("authusuario");
            }
            // Se devuleve un mensaje flash a la vista del login de usuario indicando el error.
            redirectAttributes.addFlashAttribute("errorFlash",
                    "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authusuario");
        }

        // Si la fecha de bloqueo fue hace menos de 15 min se bloquea la sesión (Thread.sleep).
        // En una aplicación más grande habría que liberar el hilo (wait)
        if (servicioSesion.getFechaBloqueo() != null &&
                ChronoUnit.MILLIS.between(servicioSesion.getFechaBloqueo(), LocalDateTime.now()) < 900000) {
            try {
                Thread.sleep(900000 - ChronoUnit.MILLIS.between(servicioSesion.getFechaBloqueo(), LocalDateTime.now()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Una vez terminado el tiempo de bloqueo, se informa al usuario de que la sesión se ha desbloqueado.
            redirectAttributes.addFlashAttribute("borradoFlash", "Sesión desbloqueada");
            return new RedirectView("authusuario");
        }

        // Si el usuario está bloqueado se vuelve al login de usuario.
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
            servicioSesion.setUsuarioLoggeado(servicioSesion.getUsuarioParaLogin());
            // Lógica de cookie que cuenta el número de accesos por usuario.
            UsuarioEmpleadoCliente uec = servicioSesion.getUsuarioLoggeado();
            ManejoCookieVisitas manejoCookieVisitas = new ManejoCookieVisitas(uec.getEmail(), contenidoCookie, null);
            String numeroVisitas = manejoCookieVisitas.devuelveNumeroVisitas();
            Cookie miCookie = new Cookie("autentificaciones", manejoCookieVisitas.getValorCookie());
            miCookie.setPath("/");
            respuestaHttp.addCookie(miCookie);
            // Se guarda el número de accesos del usuario en la base de datos.
            uec.setNumeroAccesos(Integer.valueOf(numeroVisitas));
            usuarioEmpleadoClienteRepository.save(uec);
            return new RedirectView("area-personal");
        } else {
            // Se incrementa en uno el número de intentos de inicio de sesión no satisfactorios.
            servicioSesion.getUsuarioParaLogin().setIntentosFallidosLogin(servicioSesion.getUsuarioParaLogin().getIntentosFallidosLogin() + 1);
            usuarioEmpleadoClienteRepository.save(servicioSesion.getUsuarioParaLogin());
            // Si se ha llegado a 3 autentificaciones fallidas se bloquea el usuario.
            if (servicioSesion.getUsuarioParaLogin().getIntentosFallidosLogin() == 3) {
                servicioSesion.getUsuarioParaLogin().setIntentosFallidosLogin(0);
                if (servicioSesion.getListaMotivosBloqueo() == null) {
                    servicioSesion.setListaMotivosBloqueo(motivoBloqueoRepository.findAll());
                }
                // Se asigna un motivo de bloqueo al usuario que intenta hacer el login.
                for (MotivoBloqueo mb : servicioSesion.getListaMotivosBloqueo()) {
                    if (mb.getMinutosBloqueo() == 15) {
                        servicioSesion.getUsuarioParaLogin().setMotivoBloqueo(mb);
                    }
                }
                // Se añade una fecha de desbloqueo teniendo en cuenta el tiempo de bloqueo.
                servicioSesion.getUsuarioParaLogin().setFechaDesbloqueo(LocalDateTime.now().plusMinutes(
                        servicioSesion.getUsuarioParaLogin().getMotivoBloqueo().getMinutosBloqueo()));
                usuarioEmpleadoClienteRepository.save(servicioSesion.getUsuarioParaLogin());
                redirectAttributes.addFlashAttribute("errorFlash", "Usuario bloqueado por " +
                        servicioSesion.getUsuarioParaLogin().getMotivoBloqueo().getMotivo());
                return new RedirectView("authusuario");
            }
            // Si no se corresponde la contraseña con el usuario se devuleve un mensaje flash a la vista del login
            // de usuario indicando el error.
            redirectAttributes.addFlashAttribute("errorFlash",
                    "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authusuario");
        }
    }

    // Área personal de un usuario cliente/empleado
    @GetMapping("area-personal")
    public ModelAndView areaPersonalGet(ModelAndView modelAndView) {
        // Si se accede al área personal sin iniciar sesión correctamente.
        if (servicioSesion.getUsuarioLoggeado() == null) {
            // Si NO se ha llevado a cabo una desconexión ordenada.
            if (servicioSesion.getUsuarioParaLogin() == null) {
                modelAndView.setViewName("redirect:authusuario");
            } else {
                // Si se ha llevado a cabo una desconexión ordenada.
                modelAndView.setViewName("redirect:authclave");
            }
            return modelAndView;
        }
        // Si el usuario tiene registrado un cliente para realizar compras en la aplicación.
        if (registroUsuario.clienteRegistrado()) {
            modelAndView.setViewName(PREFIJO3 + "area_personal");
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
        modelAndView.setViewName("redirect:authclave");
        return modelAndView;
    }

    // Recuperación de la contraseña.
    @GetMapping("recuperar")
    public ModelAndView recuperacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO3 + "recuperacion_clave");
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
        return new RedirectView("authusuario");
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
        // Se comprueba que el usuario y la contraseña son correctos.
        if (registroUsuario.usuarioAdminRegistrado(email, clave)) {
            return new RedirectView("administracion");
        } else {
            // Si el email no se corresponde con la contraseña se indica el error con un mensaje flash.
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("authadmin");
        }
    }

    // Área personal de un usuario administrador.
    @GetMapping("administracion")
    public ModelAndView administracionGet(ModelAndView modelAndView) {
        // Si no se ha iniciado sesión correctamente y se intenta acceder directamente al área de administración
        // se redirige a la vista de login de los administradores de la aplicación.
        if (servicioSesion.getAdministrador() == null) {
            modelAndView.setViewName("redirect:authadmin");
        } else {
            modelAndView.setViewName(PREFIJO3 + "administracion");
        }
        return modelAndView;
    }

    @PostMapping("administracion")
    public ModelAndView administracionPost(ModelAndView modelAndView) {
        // Desconexión ordenada.
        servicioSesion.setAdministrador(null);
        modelAndView.setViewName("redirect:authadmin");
        return modelAndView;
    }

}
