package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.TipoCliente;
import org.grupo1.tienda.repository.TipoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoClienteServiceImpl implements TipoClienteService {

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    public List<TipoCliente> devuelveTiposCliente() {
        return tipoClienteRepository.findAll();
    }

    public TipoCliente devuelveTipoClientePorId(Long id) throws NoEncontradoException {
        return tipoClienteRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Tipo de cliente con id " + id + " no encontrado"));
    }

    public TipoCliente aniadeTipoCliente(TipoCliente tipoCliente) {
        return tipoClienteRepository.save(tipoCliente);
    }

    public TipoCliente actualizaTipoCliente(Long id, TipoCliente tipoCliente) throws NoEncontradoException {
        if (!tipoClienteRepository.existsById(id)) {
            throw new NoEncontradoException("Tipo de cliente con id " + id + " no encontrado");
        }
        tipoCliente.setId(id);
        return tipoClienteRepository.save(tipoCliente);
    }

    public void eliminaTipoCliente(Long id) throws NoEncontradoException {
        if (!tipoClienteRepository.existsById(id)) {
            throw new NoEncontradoException("Tipo de cliente con id " + id + " no encontrado");
        }
        tipoClienteRepository.deleteById(id);
    }

}
