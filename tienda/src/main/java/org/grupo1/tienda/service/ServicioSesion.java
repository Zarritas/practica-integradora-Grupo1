package org.grupo1.tienda.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.grupo1.tienda.model.catalog.Pais;
import org.grupo1.tienda.model.catalog.PreguntaRecuperacion;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;

@Service
@SessionScope
@Data
public class ServicioSesion {
    // Repositorios
    @Autowired
    PreguntaRecuperacionRepository preguntaRecuperacionRepository;
    @Autowired
    UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    @Autowired
    RecuperacionClaveRepository recuperacionClaveRepository;
    // Atributos de la clase
    List<PreguntaRecuperacion> listaPreguntasRecuperacion;
    Map<Long, String> mapaPreguntasRecuperacion;
    List<UsuarioEmpleadoCliente> listaUsuarioEmpleadoCliente;
    Map<String, String> mapaUsuariosEmpleadoCliente;
    List<RecuperacionClave> listaRecuperacionClave;
    //Constructor
    public ServicioSesion() {
        listaPreguntasRecuperacion = preguntaRecuperacionRepository.findAll();
        crearMapaPreguntas();
        listaUsuarioEmpleadoCliente = usuarioEmpleadoClienteRepository.findAll();
        listaRecuperacionClave = recuperacionClaveRepository.findAll();
        crearMapaUsuarios();
    }

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

    private void crearMapaPreguntas() {
        for (PreguntaRecuperacion p : listaPreguntasRecuperacion) {
            mapaPreguntasRecuperacion.put(p.getId(), p.getPregunta());
        }
    }

    private void crearMapaUsuarios() {
        for (UsuarioEmpleadoCliente uec : listaUsuarioEmpleadoCliente) {
            mapaUsuariosEmpleadoCliente.put(uec.getEmail(), uec.getClave());
        }
    }

}
