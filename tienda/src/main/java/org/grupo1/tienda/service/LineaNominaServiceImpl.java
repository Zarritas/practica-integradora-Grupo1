package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.LineaNomina;
import org.grupo1.tienda.repository.LineaNominaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineaNominaServiceImpl implements LineaNominaService {

    @Autowired
    private LineaNominaRepository lineaNominaRepository;

    public List<LineaNomina> devuelveLineasNomina() {
        return lineaNominaRepository.findAll();
    }

    public LineaNomina devuelveLineaNominaPorId(Long id) throws NoEncontradoException {
        return lineaNominaRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Línea de nómina con id " + id + " no encontrada"));
    }

    public LineaNomina aniadeLineaNomina(LineaNomina lineaNomina) {
        return lineaNominaRepository.save(lineaNomina);
    }

    public LineaNomina actualizaLineaNomina(Long id, LineaNomina lineaNomina) throws NoEncontradoException {
        if (!lineaNominaRepository.existsById(id)) {
            throw new NoEncontradoException("Línea de nómina con id " + id + " no encontrada");
        }
        lineaNomina.setId(id);
        return lineaNominaRepository.save(lineaNomina);
    }

    public void eliminaLineaNomina(Long id) throws NoEncontradoException {
        if (!lineaNominaRepository.existsById(id)) {
            throw new NoEncontradoException("Línea de nómina con id " + id + " no encontrada");
        }
        lineaNominaRepository.deleteById(id);
    }

}
