package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;


@Entity
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_administrador_usuario_id"))
@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_administrador_email", columnNames = { "email" }) })
@NoArgsConstructor
public class Administrador extends Usuario {

    public Administrador(String email, String clave, String confirmarClave) {
        super(email, clave, confirmarClave);
    }
}
