package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdministradorServiceImp implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> devuelveAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador devuelveAdministradorPorId(UUID id) throws NoEncontradoException {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Usuario con id " + id + " no encontrado"));
    }

    public Administrador devuelveAdministradorPorEmailYClave(String email, String clave)
            throws NoEncontradoException {
        if (!administradorRepository.existsByEmailAndClave(email, clave)) {
            throw new NoEncontradoException("Usuario con email " + email + " no encontrado");
        }
        return administradorRepository.findByEmailAndClave(email, clave);
    }

    public Administrador aniadeAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Administrador actualizaAdministrador(UUID id, Administrador administrador)
            throws NoEncontradoException {
        if (!administradorRepository.existsById(id)) {
            throw new NoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        administrador.setId(id);
        return administradorRepository.save(administrador);
    }

    public void eliminaAdministrador(UUID id) throws NoEncontradoException {
        if (!administradorRepository.existsById(id)) {
            throw new NoEncontradoException("Usuario con id " + id + " no encontrado");
        }
        administradorRepository.deleteById(id);
    }

}
