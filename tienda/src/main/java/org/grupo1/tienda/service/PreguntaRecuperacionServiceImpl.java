package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.repository.PreguntaRecuperacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreguntaRecuperacionServiceImpl implements PreguntaRecuperacionService {

    @Autowired
    private PreguntaRecuperacionRepository preguntaRecuperacionRepository;

    public List<PreguntaRecuperacion> devuelvePreguntasRecuperacion() {
        return preguntaRecuperacionRepository.findAll();
    }

    public PreguntaRecuperacion devuelvePreguntaRecuperacionPorId(Long id) throws NoEncontradoException {
        return preguntaRecuperacionRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Pregunta con id " + id + " no encontrada"));
    }

    public PreguntaRecuperacion aniadePreguntaRecuperacion(PreguntaRecuperacion preguntaRecuperacion) {
        return preguntaRecuperacionRepository.save(preguntaRecuperacion);
    }

    public PreguntaRecuperacion actualizaPreguntaRecuperacion(Long id,
                                                              PreguntaRecuperacion preguntaRecuperacion)
                                                              throws NoEncontradoException {
        if (!preguntaRecuperacionRepository.existsById(id)) {
            throw new NoEncontradoException("Pregunta con id " + id + " no encontrada");
        }
        preguntaRecuperacion.setId(id);
        return preguntaRecuperacionRepository.save(preguntaRecuperacion);
    }

    public void eliminaPreguntaRecuperacion(Long id) throws NoEncontradoException {
        if (!preguntaRecuperacionRepository.existsById(id)) {
            throw new NoEncontradoException("Pregunta con id " + id + " no encontrada");
        }
        preguntaRecuperacionRepository.deleteById(id);
    }
}
