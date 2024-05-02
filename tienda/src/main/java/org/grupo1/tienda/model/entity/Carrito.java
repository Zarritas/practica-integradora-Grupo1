package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.auxiliary.LineaCarrito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_carrito_cliente", columnNames = { "cliente_id" }) })
@AllArgsConstructor @NoArgsConstructor @Data
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate fechaCreacion;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_carrito_linea_id"))
    private Set<LineaCarrito> lineasCarrito = new HashSet<>();
    @Transient
    private BigDecimal precio;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_carrito_cliente_id"))
    private Cliente cliente;

}
