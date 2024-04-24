package org.grupo1.tienda.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "producto")
@AllArgsConstructor @NoArgsConstructor @Data
public class Producto {
    @Id
    private Long id;

    private String name;
    private String date;
    private String image;
    private Long cantidad;
}
