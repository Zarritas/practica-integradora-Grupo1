package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> devuelveClientes() {
        return clienteRepository.findAll();
    }

    public Cliente devuelveClientePorId(UUID id) throws NoEncontradoException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Cliente con id " + id + " no encontrado"));
    }

    public Cliente devuelveClientePorUsuario(UsuarioEmpleadoCliente usuarioEmpleadoCliente)
            throws NoEncontradoException {
        if (!clienteRepository.existsByUsuario(usuarioEmpleadoCliente)) {
            throw new NoEncontradoException("Cliente con usuario " + usuarioEmpleadoCliente.getEmail() + " no encontrado");
        }
        return clienteRepository.findByUsuario(usuarioEmpleadoCliente);
    }

    public Cliente aniadeCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizaCliente(UUID id, Cliente cliente) throws NoEncontradoException {
        if (!clienteRepository.existsById(id)) {
            throw new NoEncontradoException("Cliente con id " + id + " no encontrado");
        }
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public void eliminaCliente(UUID id) throws NoEncontradoException {
        if (!clienteRepository.existsById(id)) {
            throw new NoEncontradoException("Cliente con id " + id + " no encontrado");
        }
        clienteRepository.deleteById(id);
    }

}
