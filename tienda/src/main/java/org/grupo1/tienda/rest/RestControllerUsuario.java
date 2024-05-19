package org.grupo1.tienda.rest;

import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("user")
public class RestControllerUsuario {
    @Autowired
    private UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;

    @GetMapping("recuperacion/{email}")
    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoCliente(@PathVariable String email) {
        return usuarioEmpleadoClienteRepository.findByEmailAndBajaIsFalse(email);
    }

}
