package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_empleado_cliente_usuario_id"))
@AllArgsConstructor @NoArgsConstructor @Data
public class EmpleadoCliente extends Usuario {
    @OneToOne
    private RecuperacionClave recuperacionClave;
    private LocalDate fechaUltimaConexion;
    private Integer numeroAccesos;
    private LocalDate fechaBloqueo;
}
