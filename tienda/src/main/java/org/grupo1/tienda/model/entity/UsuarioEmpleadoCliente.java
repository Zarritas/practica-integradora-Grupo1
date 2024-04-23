package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_empleado_cliente_usuario_id"))
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_usuario_empleado_cliente_email", columnNames = { "email" }),
        @UniqueConstraint(name = "UQ_usuario_empleado_cliente_recuperacion_clave", columnNames = { "recuperacionClave" })
})
@AllArgsConstructor @NoArgsConstructor @Data
public class UsuarioEmpleadoCliente extends Usuario {
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_usuario_cliente_recuperacion_clave_id"))
    private RecuperacionClave recuperacionClave;
    private LocalDate fechaUltimaConexion;
    private Integer numeroAccesos;
    private LocalDate fechaBloqueo;
    private String motivoBloqueo;
}
