package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PreguntaRecuperacionService {

    public List<PreguntaRecuperacion> devuelvePreguntasRecuperacion();

    public PreguntaRecuperacion devuelvePreguntaRecuperacionPorId(Long id) throws NoEncontradoException;

    public PreguntaRecuperacion aniadePreguntaRecuperacion(PreguntaRecuperacion preguntaRecuperacion);

    public PreguntaRecuperacion actualizaPreguntaRecuperacion(Long id, PreguntaRecuperacion preguntaRecuperacion)
            throws NoEncontradoException;

    public void eliminaPreguntaRecuperacion(Long id) throws NoEncontradoException;

}
