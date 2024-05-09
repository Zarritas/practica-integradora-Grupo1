package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.*;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
    private final String PREFIJO1 = "singin/";
    private final String PREFIJO2 = "login/";
    @Autowired
    PreguntaRecuperacionRepository preguntaRecuperacionRepository;
    @Autowired
    UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    @Autowired
    RecuperacionClaveRepository recuperacionClaveRepository;

    @GetMapping("registro")
    public ModelAndView registroUsuarioGet(ModelAndView modelAndView,
                                           HttpSession sesion,
                                           @ModelAttribute("usuario") Usuario usuario,
                                           @ModelAttribute("usuarioEmpleadoCliente") UsuarioEmpleadoCliente usuarioEmpleadoCliente) {
        Map<Long, String> mapa_preguntas = new HashMap<>();
        List<PreguntaRecuperacion> preguntasRecuperacion = preguntaRecuperacionRepository.findAll();
        for (PreguntaRecuperacion p : preguntasRecuperacion) {
            mapa_preguntas.put(p.getId(), p.getPregunta());
        }
        sesion.setAttribute("mapa_preguntas", mapa_preguntas);
        modelAndView.addObject("mapa_preguntas", mapa_preguntas);
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
                usuario.getConfirmarClave());
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
            usuarioEmpleadoClienteRepository.save(uec);
            recuperacionClaveRepository.save(rc);
            uec.setRecuperacionClave(rc);
            usuarioEmpleadoClienteRepository.save(uec);
            modelAndView.setViewName("redirect:autusuario");
        }
        return modelAndView;
    }

    @GetMapping("autusuario")
    public ModelAndView autentificacionUsuarioGet(ModelAndView modelAndView,
                                                  HttpSession sesion) {
        if (sesion.getAttribute("mensajeError") != null) {
            modelAndView.addObject("mensajeError", sesion.getAttribute("mensajeError"));
            sesion.removeAttribute("mensajeError");
        }
        modelAndView.setViewName(PREFIJO2 + "autentificacion_usuario");
        return modelAndView;
    }

    @PostMapping("autusuario")
    public ModelAndView autentificacionUsuarioPost(ModelAndView modelAndView,
                                                   HttpSession sesion,
                                                   @RequestParam("email") String email) {
        sesion.setAttribute("email", email);
        modelAndView.setViewName("redirect:autclave");
        return modelAndView;
    }

    @GetMapping("autclave")
    public ModelAndView autentificacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "autentificacion_clave");
        return modelAndView;
    }

    @PostMapping("autclave")
    public ModelAndView autentificacionClavePost(ModelAndView modelAndView,
                                                 HttpSession sesion,
                                                 @RequestParam("clave") String clave) {
        if (sesion.getAttribute("email") == null) {
            modelAndView.setViewName("redirect:autusuario");
            return modelAndView;
        }
        Map<String, String> mapa_usuarios = new HashMap<>();
        List<UsuarioEmpleadoCliente> usuarioEmpleadoClientes = usuarioEmpleadoClienteRepository.findAll();
        for (UsuarioEmpleadoCliente p : usuarioEmpleadoClientes) {
            mapa_usuarios.put(p.getEmail(), p.getClave());
        }
        String email = sesion.getAttribute("email").toString();
        if (mapa_usuarios.containsKey(email)) {
            if (mapa_usuarios.get(email).equals(clave)) {
                modelAndView.setViewName("redirect:exito");
            } else {
                sesion.setAttribute("mensajeError", "El usuario y/o la contraseña son incorrectos");
                modelAndView.setViewName("redirect:autusuario");
            }
        } else {
            sesion.setAttribute("mensajeError", "El usuario y/o la contraseña son incorrectos");
            modelAndView.setViewName("redirect:autusuario");
        }
        return modelAndView;
    }

    @GetMapping("exito")
    public ModelAndView exitoGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "exito");
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
