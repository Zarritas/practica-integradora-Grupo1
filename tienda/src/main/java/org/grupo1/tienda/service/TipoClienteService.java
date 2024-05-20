package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.TipoCliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoClienteService {

    public List<TipoCliente> devuelveTiposCliente();

    public TipoCliente devuelveTipoClientePorId(Long id) throws NoEncontradoException;

    public TipoCliente aniadeTipoCliente(TipoCliente tipoCliente);

    public TipoCliente actualizaTipoCliente(Long id, TipoCliente tipoCliente) throws NoEncontradoException;

    public void eliminaTipoCliente(Long id) throws NoEncontradoException;

}
