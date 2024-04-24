package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class RecuperacionClave {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_recuperacion_clave_pregunta_id"))
    private PreguntaRecuperacion pregunta;
    private String respuesta;
}