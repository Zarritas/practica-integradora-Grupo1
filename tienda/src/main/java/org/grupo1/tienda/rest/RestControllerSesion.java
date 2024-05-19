package org.grupo1.tienda.rest;

import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sesion")
public class RestControllerSesion {

    @Autowired
    private ServicioSesion servicioSesion;

    @GetMapping("/usuarioActual")
    public UsuarioEmpleadoCliente getUsuarioActual() {
        return servicioSesion.getUsuarioLoggeado();
    }
}
