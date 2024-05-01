package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.TipoTarjetaCredito;

import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class TarjetaCredito {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_tarjeta_credito_tipo_id"))
    private TipoTarjetaCredito tipoTarjetaCredito;
    private Integer numero;
    private String cvc;
    private LocalDate fechaCaducidad;
    private Boolean predeterminada;
}
