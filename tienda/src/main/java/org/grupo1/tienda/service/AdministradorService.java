package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.entity.Administrador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AdministradorService {

    public List<Administrador> devuelveAdministradores();

    public Administrador devuelveAdministradorPorId(UUID id) throws NoEncontradoException;

    public Administrador devuelveAdministradorPorEmailYClave(String email, String clave) throws NoEncontradoException;

    public Administrador aniadeAdministrador(Administrador administrador);

    public Administrador actualizaAdministrador(UUID id, Administrador administrador)
            throws NoEncontradoException;

    public void eliminaAdministrador(UUID id) throws NoEncontradoException;

}
