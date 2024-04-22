package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.Auditoria;
import org.grupo1.tienda.model.catalog.TipoUsuario;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String clave;
    @Transient
    private String confirmarClave;
    private LocalDate fechaUltimaConexion;
    private Integer numeroAccesos;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_usuario_auditoria_id"))
    private Auditoria auditoria;
    private LocalDate fechaBloqueo;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_usuario_tipo_usuario_id"))
    private TipoUsuario tipo;
}
