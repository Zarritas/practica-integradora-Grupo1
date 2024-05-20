package org.grupo1.tienda.service;

import org.grupo1.tienda.repository.TarjetaCreditoRepository;
import org.grupo1.tienda.model.auxiliary.TarjetaCredito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ServicioTarjetaCredito {
    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    public Set<TarjetaCredito> guardarTarjetas(Set<TarjetaCredito> tarjetas){
        return new HashSet<TarjetaCredito>(tarjetaCreditoRepository.saveAll(tarjetas));
    }
}
