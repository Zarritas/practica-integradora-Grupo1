package org.grupo1.tienda.controller;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.UsuarioEmpleadoClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("user")
public class RestControllerUsuario {
    @Autowired
    private UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl;

    @GetMapping("recuperacion/{email}")
    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoCliente(@PathVariable String email) {
        try {
            return usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorEmail(email);
        } catch (NoEncontradoException e) {
            return null;
        }
    }

}
