package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_empleado_cliente_usuario_id"))
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UQ_usuario_empleado_cliente_email_baja", columnNames = { "email", "baja" }),
        @UniqueConstraint(name = "UQ_usuario_empleado_cliente_recuperacion_clave", columnNames = { "recuperacion_clave_id" })
})
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UsuarioEmpleadoCliente extends Usuario {
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_usuario_cliente_recuperacion_clave_id"))
    private RecuperacionClave recuperacionClave;
    private LocalDate fechaUltimaConexion;
    private Integer numeroAccesos;
    private Boolean baja;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_usuario_cleinte_motivo_bloqueo_id"))
    private MotivoBloqueo motivoBloqueo;
    private LocalDateTime fechaDesbloqueo;
    private Integer intentosFallidosLogin;

    public UsuarioEmpleadoCliente(String email, String clave, String confirmarClave, RecuperacionClave recuperacionClave) {
        super(email, clave, confirmarClave);
        this.recuperacionClave = recuperacionClave;
        setBaja(false);
    }

}
