package org.grupo1.tienda.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class Atributo {
    private String nombre;
    private String valor;
    private String tipo;
}
