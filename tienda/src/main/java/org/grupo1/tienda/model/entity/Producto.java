package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.Valoracion;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private

}