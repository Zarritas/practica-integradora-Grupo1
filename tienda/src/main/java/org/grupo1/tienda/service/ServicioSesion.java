package org.grupo1.tienda.service;

import lombok.*;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.Administrador;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SessionScope
@Data
public class ServicioSesion {
    private List<PreguntaRecuperacion> listaPreguntasRecuperacion;
    private Map<Long, String> mapaPreguntasRecuperacion;
    private List<UsuarioEmpleadoCliente> listaUsuariosEmpleadoCliente;
    private Map<String, String> mapaUsuariosEmpleadoCliente;
    private List<Administrador> listaUsuariosAdmin;
    private Map<String, String> mapaUsuariosAdmin;
    private Administrador administrador;
    private UsuarioEmpleadoCliente usuarioEmpleadoCliente;

    public void crearMapaPreguntas() {
        mapaPreguntasRecuperacion = new HashMap<>();
        for (PreguntaRecuperacion p : listaPreguntasRecuperacion) {
            mapaPreguntasRecuperacion.put(p.getId(), p.getPregunta());
        }
    }

    public void crearMapaUsuarios() {
        mapaUsuariosEmpleadoCliente = new HashMap<>();
        for (UsuarioEmpleadoCliente uec : listaUsuariosEmpleadoCliente) {
            mapaUsuariosEmpleadoCliente.put(uec.getEmail(), uec.getClave());
        }
    }

    public void crearMapaUsuariosAdmin() {
        mapaUsuariosAdmin = new HashMap<>();
        for (Administrador admin : listaUsuariosAdmin) {
            mapaUsuariosAdmin.put(admin.getEmail(), admin.getClave());
        }
    }

}
