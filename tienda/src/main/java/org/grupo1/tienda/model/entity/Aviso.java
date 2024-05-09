package org.grupo1.tienda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.UrgenciaAviso;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Aviso {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String descripcion;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_aviso_urgencia_id"))
    private UrgenciaAviso urgenciaAviso;
    private LocalDate fechaProcesado;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_aviso_administrador_id"))
    private Administrador usuarioAdministradorQueLoPeocesa;

}
