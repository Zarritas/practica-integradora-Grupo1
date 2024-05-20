package org.grupo1.tienda.service;

import lombok.*;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.catalog.TipoCliente;
import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.model.entity.Cliente;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.*;

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

    // Utilizado por el administrador
    private List<TipoCliente> listaTiposCliente;

    // Contador de páginas recorridas en la sesión
    private Integer numeroPaginasVisitadas;


    public ServicioSesion() {
        intentosInicioSesion = 0;
        numeroPaginasVisitadas = 0;
    }

    public void incrementaIntentos() {
        intentosInicioSesion ++;
    }

    public void incrementaNumeroPaginasVisitadas() {
        numeroPaginasVisitadas ++;
    }

    public String cogerDatosAdmin(){
        StringBuilder datos = new StringBuilder();
        if (administradorLoggeado != null) {
            datos.append("administradorLoggeado=").append(administradorLoggeado.getId()).append(",").append(administradorLoggeado.getEmail()).append("&");
        }
        if (!datos.isEmpty() && datos.charAt(datos.length() - 1) == '&') {
            datos.setLength(datos.length() - 1);
        }
        return datos.toString();
    }

    public String cogerDatosUsuario() {
        StringBuilder datos = new StringBuilder();

        if (usuarioLoggeado != null) {
            datos.append("usuarioLoggeado=").append(usuarioLoggeado.getId()).append(",").append(usuarioLoggeado.getEmail()).append("&");
        }
        if (numeroPaginasVisitadas != null) {
            datos.append("numeroPaginasVisitadas=").append(numeroPaginasVisitadas).append("&");
        }

        // Remove the trailing "&" if it exists
        if (!datos.isEmpty() && datos.charAt(datos.length() - 1) == '&') {
            datos.setLength(datos.length() - 1);
        }

        return datos.toString();
    }

}
