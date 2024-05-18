package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.repository.MotivoBloqueoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivoBloqueoServiceImpl implements MotivoBloqueoService {

    @Autowired
    private MotivoBloqueoRepository motivoBloqueoRepository;

    public List<MotivoBloqueo> devuelveMotivosBloqueo() {
        return motivoBloqueoRepository.findAll();
    }

    public MotivoBloqueo devuelveMotivoBloqueoPorId(Long id) throws NoEncontradoException {
        return motivoBloqueoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Motivo con id " + id + " no encontrado"));
    }

    public MotivoBloqueo aniadeMotivoBloqueo(MotivoBloqueo motivoBloqueo) {
        return motivoBloqueoRepository.save(motivoBloqueo);
    }

    public MotivoBloqueo actualizaMotivoBloqueo(Long id, MotivoBloqueo motivoBloqueo) throws NoEncontradoException {
        if (!motivoBloqueoRepository.existsById(id)) {
            throw new NoEncontradoException("Motivo con id " + id + " no encontrado");
        }
        motivoBloqueo.setId(id);
        return motivoBloqueoRepository.save(motivoBloqueo);
    }

    public void eliminaMotivoBloqueo(Long id) throws NoEncontradoException {
        if (!motivoBloqueoRepository.existsById(id)) {
            throw new NoEncontradoException("Motivo con id " + id + " no encontrado");
        }
        motivoBloqueoRepository.deleteById(id);
    }

}
