package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.model.catalog.RecuperacionClave;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.repository.RecuperacionClaveRepository;
import org.grupo1.tienda.repository.UsuarioEmpleadoClienteRepository;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@AllArgsConstructor @Data
public class RegistroUsuario {
    private final ServicioSesion servicioSesion;
    private UsuarioEmpleadoClienteRepository usuarioEmpleadoClienteRepository;
    private final RecuperacionClaveRepository recuperacionClaveRepository;

    public void guardarUsuario(RecuperacionClave rc, UsuarioEmpleadoCliente uec) {
        recuperacionClaveRepository.save(rc);
//        if (servicioSesion.getListaRecuperacionClave() == null) {
//            servicioSesion.setListaRecuperacionClave(recuperacionClaveRepository.findAll());
//        }
        //servicioSesion.getListaRecuperacionClave().add(rc);
        //System.out.println(servicioSesion.getListaRecuperacionClave());
        usuarioEmpleadoClienteRepository.save(uec);
//        if (servicioSesion.getListaUsuarioEmpleadoCliente() == null) {
//            servicioSesion.setListaUsuarioEmpleadoCliente(usuarioEmpleadoClienteRepository.findAll());
//            servicioSesion.crearMapaUsuarios();
//        }
        //servicioSesion.getListaUsuarioEmpleadoCliente().add(uec);
        //System.out.println(servicioSesion.getListaUsuarioEmpleadoCliente());
    }
}
