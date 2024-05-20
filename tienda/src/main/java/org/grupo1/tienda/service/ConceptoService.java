package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.Concepto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConceptoService {

    public List<Concepto> devuelveConceptos();

    public Concepto devuelveConceptoPorId(Long id) throws NoEncontradoException;

    public Concepto devuelveConceptoPorDenominacion(String denominacion) throws NoEncontradoException;

    public Concepto aniadeConcepto(Concepto concepto);

    public Concepto actualizaConcepto(Long id, Concepto concepto) throws NoEncontradoException;

    public void eliminaConcepto(Long id) throws NoEncontradoException;

}
