package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.Concepto;
import org.grupo1.tienda.repository.ConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    @Autowired
    private ConceptoRepository conceptoRepository;

    public List<Concepto> devuelveConceptos() {
        return conceptoRepository.findAll();
    }

    public Concepto devuelveConceptoPorId(Long id) throws NoEncontradoException {
        return conceptoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Concepto de nómina con id " + id + " no encontrado"));
    }

    public Concepto devuelveConceptoPorDenominacion(String denominacion) throws NoEncontradoException {
        if (!conceptoRepository.existsByDenominacion(denominacion)) {
            throw new NoEncontradoException("Concepto de nómina con denominación " + denominacion + " no encontrado");
        }
        return conceptoRepository.findByDenominacion(denominacion);
    }

    public Concepto aniadeConcepto(Concepto concepto) {
        return conceptoRepository.save(concepto);
    }

    public Concepto actualizaConcepto(Long id, Concepto concepto) throws NoEncontradoException {
        if (!conceptoRepository.existsById(id)) {
            throw new NoEncontradoException("Concepto de nómina con id " + id + " no encontrado");
        }
        concepto.setId(id);
        return conceptoRepository.save(concepto);
    }

    public void eliminaConcepto(Long id) throws NoEncontradoException {
        if (!conceptoRepository.existsById(id)) {
            throw new NoEncontradoException("Concepto de nómina con id " + id + " no encontrado");
        }
        conceptoRepository.deleteById(id);
    }

}
