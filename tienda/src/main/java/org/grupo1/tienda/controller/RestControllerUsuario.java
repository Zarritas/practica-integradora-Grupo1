package org.grupo1.tienda.controller;

import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("user")
public class RestControllerUsuario {
    @Autowired
    private UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;

    @GetMapping("recuperacion/{email}")
    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoCliente(@PathVariable String email) {
        System.out.println(usuarioEmpleadoClienteRepository.findByEmail(email));
        return usuarioEmpleadoClienteRepository.findByEmail(email);
    }

}
