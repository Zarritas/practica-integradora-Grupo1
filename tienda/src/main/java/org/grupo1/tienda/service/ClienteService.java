package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ClienteService {

    public List<Cliente> devuelveClientes();

    public Cliente devuelveClientePorId(UUID id) throws NoEncontradoException;

    public Cliente devuelveClientePorUsuario(UsuarioEmpleadoCliente usuarioEmpleadoCliente)
            throws NoEncontradoException;

    public Cliente aniadeCliente(Cliente cliente);

    public Cliente actualizaCliente(UUID id, Cliente cliente) throws NoEncontradoException;

    public void eliminaCliente(UUID id) throws NoEncontradoException;

}
