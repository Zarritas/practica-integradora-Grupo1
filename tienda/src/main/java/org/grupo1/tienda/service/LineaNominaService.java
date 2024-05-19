package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.LineaNomina;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LineaNominaService {

    public List<LineaNomina> devuelveLineasNomina();

    public LineaNomina devuelveLineaNominaPorId(Long id) throws NoEncontradoException;

    public LineaNomina aniadeLineaNomina(LineaNomina lineaNomina);

    public LineaNomina actualizaLineaNomina(Long id, LineaNomina lineaNomina) throws NoEncontradoException;

    public void eliminaLineaNomina(Long id) throws NoEncontradoException;

}
