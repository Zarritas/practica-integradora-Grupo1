package org.grupo1.tienda.model.embeddables;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@AllArgsConstructor @NoArgsConstructor @Data
public class Periodo {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

}
