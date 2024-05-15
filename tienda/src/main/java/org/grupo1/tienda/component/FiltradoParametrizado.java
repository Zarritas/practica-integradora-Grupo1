package org.grupo1.tienda.component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.ClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
@AllArgsConstructor @Data
public class FiltradoParametrizado {
    private final ClienteRepository clienteRepository;

    public List<Cliente> filtrarListaClientes(String boton, String finicio, String ffin, String tipoCliente,
                                              String dmin, String dmax, String apellido) {
        List<Cliente> listaClientes = null;
        if (boton.contains("fecha")) {
            if (finicio != null && ffin != null) {
                listaClientes = clienteRepository.findAll();
            }
        } else if (boton.contains("tipo")) {
            if (tipoCliente != null) {
                listaClientes = clienteRepository.findAll();
            }
        } else if (boton.contains("dinero")) {
            if (dmin != null && dmax != null) {
                listaClientes = clienteRepository.findAll();
            }
        } else {
            if (apellido != null) {
                listaClientes = clienteRepository.findAll();
            }
        }
        return listaClientes;
    }


}
