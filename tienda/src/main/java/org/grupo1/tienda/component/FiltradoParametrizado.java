package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.model.catalog.TipoCliente;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.repository.ClienteRepository;
import org.grupo1.tienda.repository.TipoClienteRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@RequestScope
@AllArgsConstructor @Data
public class FiltradoParametrizado {
    private final ClienteRepository clienteRepository;
    private final TipoClienteRepository tipoClienteRepository;

    public List<Cliente> filtrarListaClientes(String boton, String finicio, String ffin, String tipoCliente,
                                              String dmin, String dmax, String apellido) {
        List<Cliente> listaClientes = null;
        if (boton.contains("fecha")) {
            if (finicio != null && ffin != null) {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inicio = LocalDate.parse(finicio, formato);
                LocalDate fin = LocalDate.parse(ffin, formato);
                listaClientes = clienteRepository.findByFechaNacimientoIsBetween(inicio, fin);
            }
        } else if (boton.contains("tipo")) {
            if (tipoCliente != null) {
                Optional<TipoCliente> tipo = tipoClienteRepository.findById(Long.parseLong(tipoCliente));
                if (tipo.isPresent()) {
                    listaClientes = clienteRepository.findByTipoCliente((TipoCliente) tipo.get());
                }
            }
        } else if (boton.contains("dinero")) {
            if (dmin != null && dmax != null) {
                BigDecimal min = BigDecimal.valueOf(Double.parseDouble(dmin));
                BigDecimal max = BigDecimal.valueOf(Double.parseDouble(dmax));
                listaClientes = clienteRepository.findByGastoAcumuladoClienteIsBetween(min, max);
            }
        } else if (boton.contains("apellido")){
            if (apellido != null) {
                listaClientes = clienteRepository.findByApellidosContaining(apellido);
            }
        } else {
            listaClientes = clienteRepository.findAll();
        }
        return listaClientes;
    }


}
