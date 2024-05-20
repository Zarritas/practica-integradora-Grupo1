package org.grupo1.tienda.service;

import org.grupo1.tienda.model.catalog.TipoVia;
import org.grupo1.tienda.repository.TipoViaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioTipoVia {
    @Autowired
    private TipoViaRepository repositorioTipoVia;

    public List<TipoVia> listaTipoVia(){
        return repositorioTipoVia.findAll();
    }
}
