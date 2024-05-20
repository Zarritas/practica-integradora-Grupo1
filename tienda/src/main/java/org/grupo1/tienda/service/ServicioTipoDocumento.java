package org.grupo1.tienda.service;

import org.grupo1.tienda.model.catalog.TipoDocumentoCliente;
import org.grupo1.tienda.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioTipoDocumento {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public List<TipoDocumentoCliente> listaTiposDocumentos(){
        return tipoDocumentoRepository.findAll();
    }
}
