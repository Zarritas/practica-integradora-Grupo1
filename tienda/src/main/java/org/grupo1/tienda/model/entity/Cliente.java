package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {
            @UniqueConstraint(name = "UQ_cliente_usuario", columnNames = { "usuario" }),
            @UniqueConstraint(name = "UQ_cliente_direccion", columnNames = { "direccion" }),
            @UniqueConstraint(name = "UQ_cliente_auditoria", columnNames = { "auditoria" })
       }, indexes = {
            @Index(name = "UQ_cliente_usuario", columnList = "usuario"),
            @Index(name = "UQ_cliente_direccion", columnList = "direccion"),
            @Index(name = "UQ_cliente_auditoria", columnList = "auditoria"),
       })
@AllArgsConstructor @NoArgsConstructor @Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_usuario_id"))
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_genero_id"))
    private Genero genero;
    private LocalDate fechaNacimiento;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_pais_id"))
    private Pais paisNacimiento;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tipo_documento_id"))
    private TipoDocumento tipoDocumento;
    private String documento;
    private String telefonoMovil;
    private String nombre;
    private String apellidos;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_direccion_id"))
    private Direccion direccion;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_direcciones_id"))
    private Set<Direccion> direccionesEntrega = new HashSet<>();
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tarjeta_credito_id"))
    private Set<TarjetaCredito> tarjetasCredito = new HashSet<>();
    private BigDecimal gastoAcumuladoCliente;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_tipo_cliente_id"))
    private TipoCliente tipoCliente;
    @ManyToMany
    private Set<Categoria> categoriasInteres = new HashSet<>();
    private String comentarios;
    private Boolean aceptacionLicencia;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_cliente_auditoria_id"))
    private Auditoria auditoria;
}
