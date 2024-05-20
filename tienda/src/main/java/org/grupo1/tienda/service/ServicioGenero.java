package org.grupo1.tienda.service;

import org.grupo1.tienda.model.catalog.Genero;
import org.grupo1.tienda.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioGenero {
    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> listaGeneros(){
        return generoRepository.findAll();
    }
}
