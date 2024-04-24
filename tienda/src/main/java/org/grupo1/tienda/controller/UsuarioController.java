package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.entity.Usuario;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("registro")
    public ModelAndView registroUsuarioGet(ModelAndView modelAndView,
                                           HttpSession sesionRegistro,
                                           @ModelAttribute("usuario") Usuario usuario,
                                           @ModelAttribute("usuarioEmpleadoCliente") UsuarioEmpleadoCliente usuarioEmpleadoCliente) {
        Map<Long, String> mapa_preguntas = new HashMap<>();
        List<PreguntaRecuperacion> preguntasRecuperacion = preguntaRecuperacionRepository.findAll();
        for (PreguntaRecuperacion p : preguntasRecuperacion) {
            mapa_preguntas.put(p.getId(), p.getPregunta());
        }
        sesionRegistro.setAttribute("mapa_preguntas", mapa_preguntas);
        modelAndView.addObject("mapa_preguntas", mapa_preguntas);
        modelAndView.setViewName(PREFIJO1 + "registro_usuario");
        return modelAndView;
    }

    @PostMapping("registro")
    public ModelAndView registroUsuarioPost(ModelAndView modelAndView,
                                            HttpSession sesionRegistro,
                                            @ModelAttribute("usuario") Usuario usuario,
                                            @ModelAttribute("usuarioEmpleadoCliente") UsuarioEmpleadoCliente usuarioEmpleadoCliente) {
        System.out.println(usuarioEmpleadoCliente);
        System.out.println(usuario);
        modelAndView.setViewName("redirect:autentificacion");
        return modelAndView;
    }

    @GetMapping("autentificacion")
    public ModelAndView autentificacionUsuarioGet(ModelAndView modelAndView) {
        modelAndView.setViewName(PREFIJO2 + "autentificacion_usuario");

        return modelAndView;
    }
}
