package org.grupo1.tienda.model.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class ClaseProveedor {
    @Id
    private Long id;
    private String denominacion;
}
