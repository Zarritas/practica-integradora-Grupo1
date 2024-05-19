package org.grupo1.tienda.model.auxiliary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class Nomina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mes;
    private Integer annio;
    private BigDecimal ingresoLiquido;
    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_nomina_linea_id"))
    private Set<LineaNomina> lineaNominas = new HashSet<>();
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_nomina_cliente_id"))
    private UsuarioEmpleadoCliente usuarioEmpleadoCliente;

    public Nomina(Integer mes, Integer annio){
        setMes(mes);
        setAnnio(annio);
    }
}
