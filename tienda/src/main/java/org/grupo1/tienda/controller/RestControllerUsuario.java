package org.grupo1.tienda.controller;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.UsuarioEmpleadoClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("desbloqueo/{id}")
    public void desbloqueaUsuarioGet(@PathVariable UUID id) {
        try {
            UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorId(id);
            uec.setFechaDesbloqueo(null);
            uec.setMotivoBloqueo(null);
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(id, uec);
        } catch (NoEncontradoException e) {
            //
        }
    }

    @GetMapping("borrado/{id}")
    public void borraUsuarioGet(@PathVariable UUID id) {
        try {
            UsuarioEmpleadoCliente uec = usuarioEmpleadoClienteServiceImpl.devuelveUsuarioEmpleadoClientePorId(id);
            uec.setBaja(true);
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(id, uec);
        } catch (NoEncontradoException e) {
            //
        }
    }

}
