package org.grupo1.tienda.service;

import org.grupo1.tienda.model.catalog.TipoTarjetaCredito;
import org.grupo1.tienda.repository.TipoTarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioTipoTarjeta {
    @Autowired
    private TipoTarjetaRepository tipoTarjetaRepository;

    public List<TipoTarjetaCredito> listaTiposTarjetas() {
        return tipoTarjetaRepository.findAll();
    }
}
