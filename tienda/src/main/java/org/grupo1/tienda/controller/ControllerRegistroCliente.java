package org.grupo1.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerRegistroCliente {
@GetMapping("/datos-personales")
    public ModelAndView datosPersonales(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registro-datos-personales");
        return modelAndView;
    }
}
