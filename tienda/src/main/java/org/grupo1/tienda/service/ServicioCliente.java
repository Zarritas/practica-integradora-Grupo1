package org.grupo1.tienda.service;

import org.grupo1.tienda.repository.ClienteRepository;
import org.grupo1.tienda.model.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCliente {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }
}
