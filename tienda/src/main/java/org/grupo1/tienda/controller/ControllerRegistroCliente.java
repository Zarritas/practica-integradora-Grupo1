package org.grupo1.tienda.controller;

import jakarta.servlet.http.HttpSession;
import org.grupo1.tienda.model.catalog.Pais;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControllerRegistroCliente {
    @Autowired
    PaisRepository paisRepository;
@GetMapping("/datos-personales")
    public ModelAndView datosPersonales(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente) {
        List<Pais> paisLista = paisRepository.findAll();
        Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        modelAndView.addObject("paises", paisLista);

        modelAndView.setViewName("registro-datos-personales");
        return modelAndView;
    }
    @PostMapping("/datos-personales")
    public ModelAndView datosPersonalesPost(ModelAndView modelAndView, HttpSession sesionRegistro){
        modelAndView.setViewName("redirect:/datos-contacto");
        return modelAndView;
    }
    @GetMapping("/datos-contacto")
    public ModelAndView datosContacto(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente) {
       // Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        modelAndView.setViewName("registro-datos-cliente");
        return modelAndView;
    }
    @GetMapping("/datos-cliente")
    public ModelAndView datosCliente(ModelAndView modelAndView, HttpSession sesionRegistro,
                                        @ModelAttribute("cliente") Cliente cliente) {
       // Cliente clienteRegistro = (Cliente) sesionRegistro.getAttribute("cliente");
        modelAndView.setViewName("registro-datos-resumen");
        return modelAndView;
    }
}
