package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.auxiliary.Auditoria;
import org.grupo1.tienda.model.auxiliary.Imagen;
import org.grupo1.tienda.model.catalog.Valoracion;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_producto_auditoria", columnNames = { "auditoria_id" }) })
@AllArgsConstructor @NoArgsConstructor @Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String codigo;
    private String descripcion;
    private Integer unidadesVendidas;
    private BigDecimal gastoAcumulado;
    private Integer cantidadAlmacen;
    private Integer umbralSolicitudProveedor;
    private Integer umbralOcultoEnTienda;
    private Boolean enOferta;
    private BigDecimal descuento;
    private Boolean esNovedad;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_producto_valoracion_id"))
    private Valoracion valoracion;
    private String marca;
    private String modelo;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_producto_imagen_id"))
    private Set<Imagen> imagenes = new HashSet<>();
    private String comentarios;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_producto_auditoria_id"))
    private Auditoria auditoria;
}