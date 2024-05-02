package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.ClaseProveedor;
import org.grupo1.tienda.model.auxiliary.Direccion;
import org.grupo1.tienda.model.catalog.TipoDocumentoProveedor;

import java.util.UUID;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_proveedor_direccion", columnNames = { "direccion_id" }) })
@AllArgsConstructor @NoArgsConstructor @Data
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_proveedor_tipo_documento_id"))
    private TipoDocumentoProveedor tipoDocumentoProveedor;
    private String documento;
    private String telefonoFijo;
    private String telefonoMovil;
    private String nombre;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_proveedor_direccion_id"))
    private Direccion direccion;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_proveedor_clase_proveedor_id"))
    private ClaseProveedor claseProveedor;
    private String comentarios;

}
