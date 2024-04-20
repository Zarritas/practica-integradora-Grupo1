package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class TarjetaCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_tarjeta_credito_tipo_id"))
    private TipoTarjetaCredito tipoTarjetaCredito;
    private Integer numero;
    private String cvc;
    private LocalDate fechaCaducidad;
    private Boolean predeterminada;
}
