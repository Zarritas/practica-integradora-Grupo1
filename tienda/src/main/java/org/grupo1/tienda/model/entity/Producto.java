package org.grupo1.tienda.model.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "producto")
@AllArgsConstructor @NoArgsConstructor @Data
public class Producto {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private String titulo;
}
