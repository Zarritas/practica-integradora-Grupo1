package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.Concepto;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class LineaNomina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_linea_nomina_concepto_id"))
    private Concepto tipo;
    private BigDecimal importe;

    public LineaNomina(Concepto tipo, BigDecimal importe) {
        setTipo(tipo);
        setImporte(importe);
    }
}
