package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class TipoCliente {
    @Id
    private Long id;
    private String tipoFidelizacion;
    private BigDecimal gastoUmbral;
    private BigDecimal porcentajeDescuento;
}
