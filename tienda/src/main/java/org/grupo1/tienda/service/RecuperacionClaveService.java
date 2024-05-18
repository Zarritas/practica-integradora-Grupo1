package org.grupo1.tienda.service;

import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecuperacionClaveService {

    public List<RecuperacionClave> devuelveRecuperacionesClave();

    public RecuperacionClave devuelveRecuperacionClavePorId(Long id) throws NoEncontradoException;

    public RecuperacionClave aniadeRecuperacionClave(RecuperacionClave recuperacionClave);

    public RecuperacionClave actualizaRecuperacionClave(Long id, RecuperacionClave recuperacionClave)
            throws NoEncontradoException;

    public void eliminaRecuperacionClave(Long id) throws NoEncontradoException;

}
