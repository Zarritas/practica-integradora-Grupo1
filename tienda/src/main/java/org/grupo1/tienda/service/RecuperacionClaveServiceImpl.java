package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecuperacionClaveServiceImpl implements RecuperacionClaveService {

    @Autowired
    private RecuperacionClaveRepository recuperacionClaveRepository;

    public List<RecuperacionClave> devuelveRecuperacionesClave() {
        return recuperacionClaveRepository.findAll();
    }

    public RecuperacionClave devuelveRecuperacionClavePorId(Long id) throws NoEncontradoException {
        return recuperacionClaveRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Recuperación clave con id "
                                                                                + id + " no encontrada"));
    }

    public RecuperacionClave aniadeRecuperacionClave(RecuperacionClave recuperacionClave) {
        return recuperacionClaveRepository.save(recuperacionClave);
    }

    public RecuperacionClave actualizaRecuperacionClave(Long id,
                                                           RecuperacionClave recuperacionClave)
                                                           throws NoEncontradoException {
        if (!recuperacionClaveRepository.existsById(id)) {
            throw new NoEncontradoException("Recuperación clave con id " + id + " no encontrada");
        }
        recuperacionClave.setId(id);
        return recuperacionClaveRepository.save(recuperacionClave);
    }

    public void eliminaRecuperacionClave(Long id) throws NoEncontradoException {
        if (!recuperacionClaveRepository.existsById(id)) {
            throw new NoEncontradoException("Recuperación clave con id " + id + " no encontrada");
        }
        recuperacionClaveRepository.deleteById(id);
    }
}
