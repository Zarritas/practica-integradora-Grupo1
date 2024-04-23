package org.grupo1.tienda.model.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class RecuperacionClave {
    @Id
    private Long id;
    @ManyToOne
    private PreguntaRecuperacion pregunta;
    private String respuesta;
}
