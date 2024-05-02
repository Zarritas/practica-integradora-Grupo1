package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.entity.Producto;

import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_linea_catalogo_producto", columnNames = { "producto_id" }) })
@NoArgsConstructor @AllArgsConstructor @Data
public class LineaCatalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_linea_catalogo_producto_id"))
    private Producto producto;
    private BigDecimal precioUnitario;
}
