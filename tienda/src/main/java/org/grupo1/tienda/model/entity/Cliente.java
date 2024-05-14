package org.grupo1.tienda.model.entity;

import jakarta.validation.Valid;
import lombok.*;
import jakarta.persistence.*;
import org.grupo1.tienda.model.auxiliary.Direccion;
import org.grupo1.tienda.model.auxiliary.Nomina;
import org.grupo1.tienda.model.auxiliary.TarjetaCredito;
import org.grupo1.tienda.model.catalog.TipoCliente;
import org.grupo1.tienda.model.catalog.*;
import org.grupo1.tienda.model.entity.grupovalidacion.*;

import jakarta.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

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
    @NotNull(groups = DatosPersonales.class)
    private Genero genero;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(groups = DatosPersonales.class)
    private LocalDate fechaNacimiento;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_pais_id"))
    @NotNull(groups = DatosPersonales.class)
    private Pais paisNacimiento;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tipo_documento_id"))
    @NotNull(groups = DatosPersonales.class)
    private TipoDocumentoCliente tipoDocumentoCliente;
    @NotBlank(groups = DatosPersonales.class)
    private String documento;
    @Pattern(regexp = "^$|[0-9]{9}", groups = DatosContacto.class)
    private String telefonoMovil;
    @NotBlank(groups = DatosPersonales.class)
    private String nombre;
    @NotBlank(groups = DatosPersonales.class)
    private String apellidos;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_direccion_id"))
    @Valid
    private Direccion direccion;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_direcciones_id"))
    @Valid
    private Set<Direccion> direccionesEntrega = new HashSet<>();
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tarjeta_credito_id"))
    @Valid
    private Set<TarjetaCredito> tarjetasCredito = new HashSet<>();
    private BigDecimal gastoAcumuladoCliente;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tipo_cliente_id"))
    private TipoCliente tipoCliente;
    @NotNull(groups = DatosCliente.class)
    private String comentarios;
    @AssertTrue(groups = DatosResumen.class)
    private Boolean aceptacionLicencia = false;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_nominas_id"))
    private Set<Nomina> nominas = new HashSet<>();
}
