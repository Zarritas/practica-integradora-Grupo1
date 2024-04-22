package org.grupo1.tienda.model.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.entity.Usuario;

import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaAltaEntidad;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_auditoria_usuario_alta_id"))
    private Usuario usuarioAdministradorQueRealizaAlta;
    private LocalDate fechaUltimaModificacionEntidad;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_auditoria_usuario_modificacion_id"))
    private Usuario usuarioAdmisnistradorQueRealizaUltimaModificacion;
    private LocalDate fechaBorradoEntidad;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_auditoria_usuario_borrado_id"))
    private Usuario usaurioAdministradorQueRealizaBorrado;
}
