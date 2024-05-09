package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.auxiliary.LineaCatalogo;
import org.grupo1.tienda.model.embeddables.Periodo;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_catalogo_proveedor", columnNames = { "proveedor_id" }) })
@AllArgsConstructor @NoArgsConstructor @Data
public class CatalogoProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_catalogo_proveedor_id"))
    private Proveedor proveedor;
    @Embedded
    private Periodo periodo;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_catalogo_lineas_id"))
    private Set<LineaCatalogo> lineasCatalogo = new HashSet<>();

}
