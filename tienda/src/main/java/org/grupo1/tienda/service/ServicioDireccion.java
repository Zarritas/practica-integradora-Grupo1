package org.grupo1.tienda.service;

import org.grupo1.tienda.model.auxiliary.Direccion;
import org.grupo1.tienda.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class ServicioDireccion {
    @Autowired
    private DireccionRepository direccionRepository;

    public Direccion guardarDireccion(Direccion direccion){
        return direccionRepository.save(direccion);
    }
    public Set<Direccion> guardarDirecciones(Set<Direccion> direcciones){
        return new HashSet<Direccion>(direccionRepository.saveAll(direcciones));
    }
}
