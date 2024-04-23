package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_direccion_tipo_via_id"))
    private TipoVia tipoVia;
    private Integer numero;
    private String portal;
    private String planta;
    private String puerta;
    private String localidad;
    private String comunidadAutonoma;
    private String codigoPostal;
}
