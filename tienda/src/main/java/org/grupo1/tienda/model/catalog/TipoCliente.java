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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_tipo_cliente_tipo_fidelizacion_id"))
    private TipoFidelizacion tipoFidelizacion;
    private BigDecimal gastoUmbral;
    private BigDecimal porcentajeDescuento;
}
