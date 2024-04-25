package org.grupo1.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerExample {

    @GetMapping("prueba")
    private ModelAndView prueba(ModelAndView mv) {
        mv.setViewName("Example");
        return mv;
    }
}
