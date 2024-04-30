package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transaction;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import org.grupo1.tienda.component.AutentificacionUsuario;
import org.grupo1.tienda.component.RegistroUsuario;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
    private final String PREFIJO1 = "singin/";
    private final String PREFIJO2 = "login/";

    private final PreguntaRecuperacionRepository preguntaRecuperacionRepository;
    private final ServicioSesion servicioSesion;
    private final AutentificacionUsuario autentificacionUsuario;
    private final UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private final RecuperacionClaveRepository recuperacionClaveRepository;
    private final RegistroUsuario registroUsuario;

    public UsuarioController(PreguntaRecuperacionRepository preguntaRecuperacionRepository,
                             ServicioSesion servicioSesion, AutentificacionUsuario autentificacionUsuario, UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository, RecuperacionClaveRepository recuperacionClaveRepository, RegistroUsuario registroUsuario) {
        this.preguntaRecuperacionRepository = preguntaRecuperacionRepository;
        this.servicioSesion = servicioSesion;
        this.autentificacionUsuario = autentificacionUsuario;
        this.usuarioEmpleadoClienteRepository = usuarioEmpleadoClienteRepository;
        this.recuperacionClaveRepository = recuperacionClaveRepository;
        this.registroUsuario = registroUsuario;
    }

    @GetMapping("registro")
    public ModelAndView registroUsuarioGet(ModelAndView modelAndView,
                                           HttpSession sesion,
                                           @ModelAttribute("usuario") Usuario usuario,
                                           @ModelAttribute("usuarioEmpleadoCliente") UsuarioEmpleadoCliente usuarioEmpleadoCliente) {
        if (servicioSesion.getListaPreguntasRecuperacion() == null) {
            servicioSesion.setListaPreguntasRecuperacion(preguntaRecuperacionRepository.findAll());
            servicioSesion.crearMapaPreguntas();
        }
        modelAndView.addObject("mapa_preguntas", servicioSesion.getMapaPreguntasRecuperacion());
        if (sesion.getAttribute("mensaje") != null) {
            modelAndView.addObject("mensaje", sesion.getAttribute("mensaje"));
            modelAndView.addObject("err", sesion.getAttribute("err"));
            sesion.removeAttribute("mensaje");
            sesion.removeAttribute("err");
        }
        modelAndView.setViewName(PREFIJO1 + "registro_usuario");
        return modelAndView;
    }

    @PostMapping("registro")
    public ModelAndView registroUsuarioPost(ModelAndView modelAndView,
                                            @ModelAttribute("usuario") Usuario usuario,
                                            @ModelAttribute("usuarioEmpleadoCliente")
                                                UsuarioEmpleadoCliente usuarioEmpleadoCliente,
                                            HttpSession sesion) {
        boolean correcto = true;
        RecuperacionClave rc = new RecuperacionClave(usuarioEmpleadoCliente.getRecuperacionClave().getPregunta(),
                usuarioEmpleadoCliente.getRecuperacionClave().getRespuesta());
        UsuarioEmpleadoCliente uec = new UsuarioEmpleadoCliente(usuario.getEmail(), usuario.getClave(),
                usuario.getConfirmarClave(), rc);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UsuarioEmpleadoCliente>> violations = validator.validate(uec);
            List<String> err = new ArrayList<>();
            for(ConstraintViolation<UsuarioEmpleadoCliente> violation : violations){
                err.add(violation.getMessage());
            }
            if (!err.isEmpty()) {
                sesion.setAttribute("mensaje", "Hay errores: ");
                sesion.setAttribute("err", err);
                correcto = false;
                modelAndView.setViewName("redirect:registro");
            }
        }
        if (correcto) {
            //registroUsuario.guardarUsuario(rc, uec);
            recuperacionClaveRepository.save(rc);
            usuarioEmpleadoClienteRepository.save(uec);
            modelAndView.setViewName("redirect:authusuario");
        }
        return modelAndView;
    }

    @GetMapping("authusuario")
    public ModelAndView autentificacionUsuarioGet(@ModelAttribute("flashAttribute") Object flashAttribute,
                                                  ModelAndView modelAndView) {
        // Se evalúa si este método ha recibido un atributo flash
        if (flashAttribute.getClass().getSimpleName().equals("String")) {
            // Se pasa el atributo flash a la vista
            modelAndView.addObject("mensajeError", flashAttribute);
        }
        modelAndView.setViewName(PREFIJO2 + "autentificacion_usuario");
        return modelAndView;
    }

    @PostMapping("authusuario")
    public ModelAndView autentificacionUsuarioPost(ModelAndView modelAndView,
                                                   HttpSession sesion,
                                                   @RequestParam("email") String email) {
        sesion.setAttribute("email", email);
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
                                                 HttpSession sesion,
                                                 @RequestParam("clave") String clave) {
        if (sesion.getAttribute("email") == null) {
            return new RedirectView("/usuario/authusuario");
        }
        String email = sesion.getAttribute("email").toString();
        if (autentificacionUsuario.usuarioRegistrado(email, clave)) {
            return new RedirectView("/usuario/exito");
        } else {
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("/usuario/authusuario");
        }
    }

    @GetMapping("exito")
    public ModelAndView exitoGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "exito");
        return modelAndView;
    }

    @PostMapping("exito")
    public ModelAndView exitoPost(ModelAndView modelAndView) {
        servicioSesion.setUsuarioEmpleadoCliente(null);
        modelAndView.setViewName("redirect:authusuario");
        return modelAndView;
    }

    @GetMapping("recuperar")
    public ModelAndView recuperacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "recuperacion_clave");
        return modelAndView;
    }

    @GetMapping("authadmin")
    public ModelAndView autentificacionAdminGet(ModelAndView modelAndView,
                                                HttpSession sesion) {
        if (sesion.getAttribute("mensajeError") != null) {
            modelAndView.addObject("mensajeError", sesion.getAttribute("mensajeError"));
            sesion.removeAttribute("mensajeError");
        }
        modelAndView.setViewName(PREFIJO2 + "autentificacion_admin");
        return modelAndView;
    }

    @PostMapping("authadmin")
    public ModelAndView autentificacionAdminPost(ModelAndView modelAndView,
                                                 HttpSession sesion,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("clave") String clave) {
        /*if (servicioSesion.getMapaUsuariosEmpleadoCliente().containsKey(email)) {
            if (servicioSesion.getMapaUsuariosEmpleadoCliente().get(email).equals(clave)) {
                modelAndView.setViewName("redirect:exito");
            } else {
                sesion.setAttribute("mensajeError", "El usuario y/o la contraseña son incorrectos");
                modelAndView.setViewName("redirect:autadmin");
            }
        } else {
            sesion.setAttribute("mensajeError", "El usuario y/o la contraseña son incorrectos");
            modelAndView.setViewName("redirect:autadmin");
        }*/
        return modelAndView;
    }

/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationExceptions(MethodArgumentNotValidException ex,
                                                   HttpSession sesion) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ModelAndView modelAndView = new ModelAndView();
        for (String e : errors.keySet()) {
            if (e.equals("clave") || e.equals("email")) {
                sesion.setAttribute("errors", errors);
                modelAndView.setViewName("redirect:registro");
                break;
            }
        }
        return modelAndView;
        //https://reflectoring.io/bean-validation-with-spring-boot/
    }
 */
}
