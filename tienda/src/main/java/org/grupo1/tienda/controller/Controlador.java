package org.grupo1.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class Controlador {
    @GetMapping()
    public RedirectView principal(){
        return new RedirectView("usuario/authusuario");
    }
}