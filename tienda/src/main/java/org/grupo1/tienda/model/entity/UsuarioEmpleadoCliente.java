package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.grupo1.tienda.model.auxiliary.Nomina;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_usuario_cliente_motivo_bloqueo_id"))
    private MotivoBloqueo motivoBloqueo;
    private LocalDateTime fechaDesbloqueo;
    private Integer intentosFallidosLogin;
    @OneToMany(mappedBy = "usuarioEmpleadoCliente")
    private Set<Nomina> nominas = new HashSet<>();

    public UsuarioEmpleadoCliente(String email, String clave, String confirmarClave, RecuperacionClave recuperacionClave) {
        super(email, clave, confirmarClave);
        this.recuperacionClave = recuperacionClave;
        setBaja(false);
        setIntentosFallidosLogin(0);
    }

}
