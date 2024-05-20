package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UsuarioEmpleadoClienteService {

    public List<UsuarioEmpleadoCliente> devuelveUsuariosEmpleadoCliente();

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoClientePorId(UUID id) throws NoEncontradoException;

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoClientePorEmail(String email) throws NoEncontradoException;

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoClientePorEmailYBajaTrue(String email) throws NoEncontradoException;

    public UsuarioEmpleadoCliente aniadeUsuarioEmpleadoCliente(UsuarioEmpleadoCliente usuarioEmpleadoCliente);

    public UsuarioEmpleadoCliente actualizaUsuarioEmpleadoCliente(UUID id, UsuarioEmpleadoCliente usuarioEmpleadoCliente)
            throws NoEncontradoException;

    public void eliminaUsuarioEmpleadoCliente(UUID id) throws NoEncontradoException;

}
