package org.grupo1.tienda.service;

import org.grupo1.tienda.model.catalog.Pais;
import org.grupo1.tienda.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPais {
    @Autowired
    private PaisRepository paisRepository;
    public List<Pais> listaPaises(){
        return paisRepository.findAll();
    }

}
