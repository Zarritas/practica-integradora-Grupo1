package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.TipoTarjetaCredito;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosCliente;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "numero", unique = true)
    @NotNull(groups = DatosCliente.class)
    private Integer numeroTarjeta ;
    @NotBlank(groups = DatosCliente.class)
    private String cvc;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(groups = DatosCliente.class)
    private LocalDate fechaCaducidad;
    private Boolean predeterminada = false;
}
