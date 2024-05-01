package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.FormatoImagen;
import org.grupo1.tienda.model.entity.Administrador;

import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreArchivo;
    private String ubicacion;
    private String descripcion;
    private Integer tamanio;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_imagen_formato_id"))
    private FormatoImagen formatoImagen;

}
