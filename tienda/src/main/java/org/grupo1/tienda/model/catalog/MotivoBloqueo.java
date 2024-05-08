package org.grupo1.tienda.model.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MotivoBloqueo {
    @Id
    private Long id;
    private String motivo;
    private Integer minutosBloqueo;

    public MotivoBloqueo(String motivo, Integer minutosBloqueo) {
        setMotivo(motivo);
        setMinutosBloqueo(minutosBloqueo);
    }
}
