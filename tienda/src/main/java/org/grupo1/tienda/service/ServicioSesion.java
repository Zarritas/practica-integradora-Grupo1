package org.grupo1.tienda.service;

import lombok.*;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.auxiliary.RecuperacionClave;
import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@SessionScope
@Data
public class ServicioSesion {
    // Utilizado durante el registro de un usuario empleado/cliente
    private List<PreguntaRecuperacion> listaPreguntasRecuperacion;
    private UsuarioEmpleadoCliente usuarioParaLogin;

    // Utilizado durante los intentos de autentificación
    private List<MotivoBloqueo> listaMotivosBloqueo;
    private LocalDateTime fechaBloqueo;
    private String motivoBloqueo;
    private Integer intentosInicioSesion;

    // Usuarios autentificados en la sesión
    private Administrador administradorLoggeado;
    private UsuarioEmpleadoCliente usuarioLoggeado;

    // Lista de usuarios
    private List<UsuarioEmpleadoCliente> listaUsuariosEmpleadoCliente;
    private List<Cliente> listaClientes;

    // Contador de páginas recorridas en la sesión
    private Set<String> conjuntoNombrePaginasVisitadas;


    public ServicioSesion() {
        intentosInicioSesion = 0;
    }

    public void incrementaIntentos() {
        intentosInicioSesion ++;
    }
}
