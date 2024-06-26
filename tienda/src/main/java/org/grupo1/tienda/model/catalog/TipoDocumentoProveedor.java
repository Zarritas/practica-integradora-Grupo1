package org.grupo1.tienda.model.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class TipoDocumentoProveedor {
    @Id
    private Long id;
    private String denominacion;
}
