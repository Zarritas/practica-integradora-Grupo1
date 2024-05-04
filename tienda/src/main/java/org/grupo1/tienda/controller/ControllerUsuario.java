package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.*;
import org.grupo1.tienda.component.AutentificacionUsuario;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.ClienteRepository;
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

import java.util.*;

@Controller
@RequestMapping("usuario")
public class ControllerUsuario {
    private final String PREFIJO1 = "singin/";
    private final String PREFIJO2 = "login/";

    // Se agregan los repositorios como campos del controlador.
    private final PreguntaRecuperacionRepository preguntaRecuperacionRepository;
    private final ServicioSesion servicioSesion;
    private final AutentificacionUsuario autentificacionUsuario;
    private final UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private final RecuperacionClaveRepository recuperacionClaveRepository;
    private final ClienteRepository clienteRepository;

    // Los repositorios necesitan ser inicializados en el controlador.
    public ControllerUsuario(PreguntaRecuperacionRepository preguntaRecuperacionRepository,
                             ServicioSesion servicioSesion, AutentificacionUsuario autentificacionUsuario,
                             UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository,
                             RecuperacionClaveRepository recuperacionClaveRepository, ClienteRepository clienteRepository) {
        this.preguntaRecuperacionRepository = preguntaRecuperacionRepository;
        this.servicioSesion = servicioSesion;
        this.autentificacionUsuario = autentificacionUsuario;
        this.usuarioEmpleadoClienteRepository = usuarioEmpleadoClienteRepository;
        this.recuperacionClaveRepository = recuperacionClaveRepository;
        this.clienteRepository = clienteRepository;
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
            if (autentificacionUsuario.usuarioRegistrado(usuario.getEmail())) {
                modelAndView.addObject("usuarioYaRegistrado", "Ya existe una cuenta asociada a ese email");
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

/*
    @PostMapping("registro")
    public ModelAndView registroUsuarioPost(ModelAndView modelAndView,
                                            @ModelAttribute("usuario") Usuario usuario,
                                            @ModelAttribute("usuarioEmpleadoCliente")
                                                UsuarioEmpleadoCliente usuarioEmpleadoCliente,
                                            HttpSession sesion) {
        // Se crea un avariable booleana para medir si ha habido errores en la validación o no.
        boolean correcto = true;
        // Se crea un usuario cleinte/empleado.
        RecuperacionClave rc = new RecuperacionClave(usuarioEmpleadoCliente.getRecuperacionClave().getPregunta(),
                usuarioEmpleadoCliente.getRecuperacionClave().getRespuesta());
        UsuarioEmpleadoCliente uec = new UsuarioEmpleadoCliente(usuario.getEmail(), usuario.getClave(),
                usuario.getConfirmarClave(), rc);
        // Se recogen las violaciones de constraints para mostrarlas.
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
        // Si no hay errores en el registro se guarda el usuario empleado/cliente en la base de datos.
        if (correcto) {
            recuperacionClaveRepository.save(rc);
            usuarioEmpleadoClienteRepository.save(uec);
            modelAndView.setViewName("redirect:authusuario");
        }
        return modelAndView;
    }
*/
    // Autentificación de un usuario cliente/empleado.
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
        // Se guarda el email en la sesión sin hacer comprobaciones.
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
        // Si no hay guardado un usuario en la sesión se vuelve a la vista del login de usuario.
        if (sesion.getAttribute("email") == null) {
            return new RedirectView("/usuario/authusuario");
        }
        // Se regoge el email de la sesión y se comprueba que el usuario y la contraseña se coresponden.
        String email = sesion.getAttribute("email").toString();
        if (autentificacionUsuario.usuarioRegistrado(email, clave)) {
            return new RedirectView("/usuario/area-personal");
        } else {
            // Si no se corresponden se devuleve un mensaje flash a la vista del login de usuario indicando el error.
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("/usuario/authusuario");
        }
    }

    // Área personal de un usuario cliente/empleado
    @GetMapping("area-personal")
    public ModelAndView areaPersonalGet(ModelAndView modelAndView,
                                        HttpSession sesion) {
        // Si se accede al área personal sin iniciar sesión correctamente.
        if (servicioSesion.getUsuarioEmpleadoCliente() == null) {
            if (sesion.getAttribute("email") == null) {
                modelAndView.setViewName("redirect:authusuario");
            } else {
                // Si se ha llevado a cabo una desconexión ordenada.
                modelAndView.setViewName("redirect:authclave");
            }
            return modelAndView;
        }
        // Si el usuario no tiene registrado un cliente para realizar compras en la aplicación.
        if (clienteRepository.findByUsuario(servicioSesion.getUsuarioEmpleadoCliente()) == null) {
            modelAndView.setViewName("redirect:/datos-personales");
        } else {
            // Si ya tiene un cliente.
            modelAndView.setViewName(PREFIJO2 + "area_personal");
        }

        return modelAndView;
    }

    @PostMapping("area-personal")
    public ModelAndView areaPersonalPost(ModelAndView modelAndView) {
        // Desconexión ordenada.
        servicioSesion.setUsuarioEmpleadoCliente(null);
        modelAndView.setViewName("redirect:authclave");
        return modelAndView;
    }

    // Recuperación de la contraseña.
    @GetMapping("recuperar")
    public ModelAndView recuperacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "recuperacion_clave");
        return modelAndView;
    }

    @PostMapping("recuperar")
    public ModelAndView recuperacionClavePost(ModelAndView modelAndView) {

        return modelAndView;
    }

    // Autentificación de un usuario administrador
    @GetMapping("authadmin")
    public ModelAndView autentificacionAdminGet(@ModelAttribute("flashAttribute") Object flashAttribute,
                                                ModelAndView modelAndView,
                                                HttpSession sesion) {
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
                                                 HttpSession sesion,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("clave") String clave) {
        // Si no se ha introducido uno de los campos se vuelve a la vista de login con un mensaje flash indicándolo.
        if (email == null || clave == null) {
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("/usuario/authadmin");
        }
        // Se comprueba que el usuario y la contraseña son correctos.
        if (autentificacionUsuario.usuarioAdminRegistrado(email, clave)) {
            return new RedirectView("/usuario/administracion");
        } else {
            // Si el email no se corresponde con la contraseña se indica el error con un mensaje flash.
            redirectAttributes.addFlashAttribute("flashAttribute", "El usuario y/o la contraseña son incorrectos");
            return new RedirectView("/usuario/authadmin");
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
            modelAndView.setViewName(PREFIJO2 + "administracion");
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
