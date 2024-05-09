package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosCliente;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosContacto;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosPersonales;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_direccion_tipo_via_id"))
    private TipoVia tipoVia;
    @NotBlank(groups = {DatosContacto.class, DatosCliente.class })
    private String via;
    @NotNull(groups = {DatosContacto.class, DatosCliente.class })
    private Integer numero;
    private String portal;
    private String planta;
    private String puerta;
    @NotBlank(groups = {DatosContacto.class, DatosCliente.class })
    private String localidad;
    @NotBlank(groups = {DatosContacto.class, DatosCliente.class })
    private String comunidadAutonoma;
    @NotBlank(groups = {DatosContacto.class, DatosCliente.class })
    private String codigoPostal;
}
