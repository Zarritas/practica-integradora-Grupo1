package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.auxiliary.Direccion;
import org.grupo1.tienda.model.auxiliary.Nomina;
import org.grupo1.tienda.model.auxiliary.TarjetaCredito;
import org.grupo1.tienda.model.catalog.TipoCliente;
import org.grupo1.tienda.model.catalog.*;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosCliente;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosContacto;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosPersonales;
import org.grupo1.tienda.model.entity.grupovalidacion.DatosResumen;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {
            @UniqueConstraint(name = "UQ_cliente_usuario", columnNames = { "usuario_id" }),
            @UniqueConstraint(name = "UQ_cliente_direccion", columnNames = { "direccion_id" })
       })
@AllArgsConstructor @NoArgsConstructor @Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_usuario_id"))
    private UsuarioEmpleadoCliente usuario;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_genero_id"))
    @NotBlank(groups = DatosPersonales.class)
    private Genero genero;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotBlank(groups = DatosPersonales.class)
    @NotNull
    private LocalDate fechaNacimiento;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_pais_id"))
    @NotBlank(groups = DatosPersonales.class)
    private Pais paisNacimiento;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tipo_documento_id"))
    @NotBlank(groups = DatosPersonales.class)
    private TipoDocumentoCliente tipoDocumentoCliente;
    @NotBlank(groups = DatosPersonales.class)
    private String documento;
    @NotBlank(groups = DatosContacto.class)
    private String telefonoMovil;
    @NotBlank(groups = DatosPersonales.class)
    private String nombre;
    @NotBlank(groups = DatosPersonales.class)
    private String apellidos;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_direccion_id"))
    @NotBlank(groups = DatosContacto.class)
    private Direccion direccion;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_direcciones_id"))
    @NotBlank(groups = DatosCliente.class)
    private Set<Direccion> direccionesEntrega = new HashSet<>();
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tarjeta_credito_id"))
    @NotBlank(groups = DatosCliente.class)
    private Set<TarjetaCredito> tarjetasCredito = new HashSet<>();
    private BigDecimal gastoAcumuladoCliente;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tipo_cliente_id"))
    private TipoCliente tipoCliente;
    private String comentarios;
    @NotBlank(groups = DatosResumen.class)
    private Boolean aceptacionLicencia;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_nominas_id"))
    private Set<Nomina> nominas = new HashSet<>();
}
