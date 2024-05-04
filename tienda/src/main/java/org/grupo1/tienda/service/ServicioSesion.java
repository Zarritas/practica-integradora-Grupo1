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
    private List<UsuarioEmpleadoCliente> listaUsuariosEmpleadoCliente;
    private List<Administrador> listaUsuariosAdmin;
    private Administrador administrador;
    private UsuarioEmpleadoCliente usuarioEmpleadoCliente;

}
