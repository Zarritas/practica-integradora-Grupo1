package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        modelAndView.setViewName(PREFIJO1 + "registro_usuario");
        return modelAndView;
    }

    @PostMapping("registro")
    public ModelAndView registroUsuarioPost(ModelAndView modelAndView,
                                            @Valid @ModelAttribute("usuario") Usuario usuario,
                                            @Valid @ModelAttribute("usuarioEmpleadoCliente")
                                                UsuarioEmpleadoCliente usuarioEmpleadoCliente) {
        RecuperacionClave rc = new RecuperacionClave(usuarioEmpleadoCliente.getRecuperacionClave().getPregunta(),
                usuarioEmpleadoCliente.getRecuperacionClave().getRespuesta());
        UsuarioEmpleadoCliente uec = new UsuarioEmpleadoCliente(usuario.getEmail(), usuario.getClave(),
                usuario.getConfirmarClave());
        usuarioEmpleadoClienteRepository.save(uec);
        recuperacionClaveRepository.save(rc);
        uec.setRecuperacionClave(rc);
        usuarioEmpleadoClienteRepository.save(uec);
        System.out.println(usuarioEmpleadoCliente);
        System.out.println(usuario);
        modelAndView.setViewName("redirect:autusuario");
        return modelAndView;
    }

    @GetMapping("autusuario")
    public ModelAndView autentificacionUsuarioGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "autentificacion_usuario");
        return modelAndView;
    }

    @PostMapping("autusuario")
    public ModelAndView autentificacionUsuarioPost(ModelAndView modelAndView,
                                                   @RequestParam("usuario") String usuario) {

        modelAndView.setViewName("redirect:autclave");
        return modelAndView;
    }

    @GetMapping("autclave")
    public ModelAndView autentificacionClaveGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "autentificacion_clave");
        return modelAndView;
    }

    @PostMapping("autclave")
    public ModelAndView autentificacionClavePost(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:exito");
        return modelAndView;
    }

    @GetMapping("exito")
    public ModelAndView exitoGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "exito");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
