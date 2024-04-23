package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import org.grupo1.tienda.model.validation.ClavesIguales;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
//@Table(uniqueConstraints = { @UniqueConstraint(name = "UQ_usuario_email", columnNames = { "email" }) })
@ClavesIguales
@AllArgsConstructor @NoArgsConstructor @Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    @NotBlank
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotBlank
    @Size(min=6, max=12)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&]).{4,}$")
    private String clave;
    @Transient
    private String confirmarClave;
}
