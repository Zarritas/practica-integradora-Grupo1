package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.Auditoria;

import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_administrador_usuario_id"))
@AllArgsConstructor @NoArgsConstructor @Data
public class Administrador extends Usuario {
    @OneToMany
    private Set<Auditoria> auditorias;
}
