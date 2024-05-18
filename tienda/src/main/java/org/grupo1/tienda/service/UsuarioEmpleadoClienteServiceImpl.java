package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioEmpleadoClienteServiceImpl implements UsuarioEmpleadoClienteService {

    @Autowired
    private UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;

    public List<UsuarioEmpleadoCliente> devuelveUsuariosEmpleadoCliente() {
        return usuarioEmpleadoClienteRepository.findAll();
    }

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoClientePorId(UUID id) throws NoEncontradoException {
        return usuarioEmpleadoClienteRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Usuario con id " + id + " no encontrado"));
    }

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoClientePorEmail(String email) throws NoEncontradoException {
        if (!usuarioEmpleadoClienteRepository.existsByEmail(email)) {
            throw new NoEncontradoException("Usuario con email " + email + " no encontrado");
        }
        return usuarioEmpleadoClienteRepository.findByEmailAndBajaIsFalse(email);
    }

    public UsuarioEmpleadoCliente devuelveUsuarioEmpleadoClientePorEmailYBajaTrue(String email) throws NoEncontradoException {
        if (!usuarioEmpleadoClienteRepository.existsByEmailAndBajaIsTrue(email)) {
            throw new NoEncontradoException("Usuario con email " + email + " y dado de baja no encontrado");
        }
        return usuarioEmpleadoClienteRepository.findByEmailAndBajaIsTrue(email);
    }

    public UsuarioEmpleadoCliente aniadeUsuarioEmpleadoCliente(UsuarioEmpleadoCliente usuarioEmpleadoCliente) {
        return usuarioEmpleadoClienteRepository.save(usuarioEmpleadoCliente);
    }

    public UsuarioEmpleadoCliente actualizaUsuarioEmpleadoCliente(UUID id,
                                                                   UsuarioEmpleadoCliente usuarioEmpleadoCliente)
                                                                   throws NoEncontradoException {
        if (!usuarioEmpleadoClienteRepository.existsById(id)) {
            throw new NoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        usuarioEmpleadoCliente.setId(id);
        return usuarioEmpleadoClienteRepository.save(usuarioEmpleadoCliente);
    }

    public void eliminaUsuarioEmpleadoCliente(UUID id) throws NoEncontradoException {
        if (!usuarioEmpleadoClienteRepository.existsById(id)) {
            throw new NoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        usuarioEmpleadoClienteRepository.deleteById(id);
    }

}
