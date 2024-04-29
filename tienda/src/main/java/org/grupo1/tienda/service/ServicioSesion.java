package org.grupo1.tienda.service;

import lombok.*;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
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
    // Atributos de la clase
    private List<PreguntaRecuperacion> listaPreguntasRecuperacion;
    private Map<Long, String> mapaPreguntasRecuperacion;
    private List<UsuarioEmpleadoCliente> listaUsuarioEmpleadoCliente;
    private Map<String, String> mapaUsuariosEmpleadoCliente;
    private List<RecuperacionClave> listaRecuperacionClave;
    private UsuarioEmpleadoCliente usuarioEmpleadoCliente;
/*
    public Boolean guardarUsuarioEmpleadoCliente(UsuarioEmpleadoCliente uec) {
        boolean correcto = true;
        try {
            usuarioEmpleadoClienteRepository.save(uec);
        } catch (Exception e) {
            correcto = false;
        }
        return correcto;
    }

    public void guardarRecuperacionClave(RecuperacionClave rc) {
        recuperacionClaveRepository.save(rc);
    }
*/
    public void crearMapaPreguntas() {
        mapaPreguntasRecuperacion = new HashMap<>();
        for (PreguntaRecuperacion p : listaPreguntasRecuperacion) {
            mapaPreguntasRecuperacion.put(p.getId(), p.getPregunta());
        }
    }

    public void crearMapaUsuarios() {
        mapaUsuariosEmpleadoCliente = new HashMap<>();
        for (UsuarioEmpleadoCliente uec : listaUsuarioEmpleadoCliente) {
            mapaUsuariosEmpleadoCliente.put(uec.getEmail(), uec.getClave());
        }
    }

}
