package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.validation.PreguntaClave;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data

public class RecuperacionClave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_recuperacion_clave_pregunta_id"))
    @PreguntaClave
    @NotNull
    private PreguntaRecuperacion pregunta;
    @NotBlank
    private String respuesta;

    public RecuperacionClave(PreguntaRecuperacion pregunta, String respuesta) {
        setPregunta(pregunta);
        setRespuesta(respuesta);
    }
}
