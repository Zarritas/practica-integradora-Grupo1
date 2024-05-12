package org.grupo1.tienda.service;

import org.springframework.stereotype.Service;

@Service
public class ServicioMongo {
    public static String obtenerTipoDato(Object valor) {
        return valor == null ? "null" : valor.getClass().getSimpleName();
    }
}
