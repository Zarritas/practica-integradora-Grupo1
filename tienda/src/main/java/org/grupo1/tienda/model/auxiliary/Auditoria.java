package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.entity.Administrador;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "UQ_auditoria_admin_alta", columnNames = { "usuario_administrador_que_realiza_alta_id" }),
    @UniqueConstraint(name = "UQ_auditoria_admin_ult_mod", columnNames = { "usuario_admisnistrador_que_realiza_ultima_modificacion_id" }),
    @UniqueConstraint(name = "UQ_auditoria_admin_borrado", columnNames = { "usaurio_administrador_que_realiza_borrado_id" }),
})
@NoArgsConstructor @AllArgsConstructor @Data
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaAltaEntidad;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_auditoria_usuario_alta_id"))
    private Administrador usuarioAdministradorQueRealizaAlta;
    private LocalDate fechaUltimaModificacionEntidad;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_auditoria_usuario_modificacion_id"))
    private Administrador usuarioAdmisnistradorQueRealizaUltimaModificacion;
    private LocalDate fechaBorradoEntidad;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_auditoria_usuario_borrado_id"))
    private Administrador usaurioAdministradorQueRealizaBorrado;
}
