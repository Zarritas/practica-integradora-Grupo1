package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MotivoBloqueoService {

    public List<MotivoBloqueo> devuelveMotivosBloqueo();

    public MotivoBloqueo devuelveMotivoBloqueoPorId(Long id) throws NoEncontradoException;

    public MotivoBloqueo aniadeMotivoBloqueo(MotivoBloqueo motivoBloqueo);

    public MotivoBloqueo actualizaMotivoBloqueo(Long id, MotivoBloqueo motivoBloqueo) throws NoEncontradoException;

    public void eliminaMotivoBloqueo(Long id) throws NoEncontradoException;

}
