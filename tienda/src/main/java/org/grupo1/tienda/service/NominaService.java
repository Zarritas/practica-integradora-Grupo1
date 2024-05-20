package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.Nomina;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NominaService {

    public List<Nomina> devuelveNominas();

    public Nomina devuelveNominaPorId(Long id) throws NoEncontradoException;

    public Nomina aniadeNomina(Nomina nomina);

    public Nomina actualizaNomina(Long id, Nomina nomina) throws NoEncontradoException;

    public void eliminaNomina(Long id) throws NoEncontradoException;

}
