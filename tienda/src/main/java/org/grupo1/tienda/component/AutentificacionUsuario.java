package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.exception.NoEncontradoException;
import org.grupo1.tienda.model.catalog.MotivoBloqueo;
import org.grupo1.tienda.service.MotivoBloqueoServiceImpl;
import org.grupo1.tienda.service.ServicioSesion;
import org.grupo1.tienda.service.UsuarioEmpleadoClienteServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequestScope
@AllArgsConstructor
@Data
public class AutentificacionUsuario {
    private final ServicioSesion servicioSesion;
    private final UsuarioEmpleadoClienteServiceImpl usuarioEmpleadoClienteServiceImpl;
    private final MotivoBloqueoServiceImpl motivoBloqueoServiceImpl;

    public RedirectView bloqueoSesion(RedirectAttributes redirectAttributes) {
        try {
            Thread.sleep(900000 - ChronoUnit.MILLIS.between(servicioSesion.getFechaBloqueo(), LocalDateTime.now()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Una vez terminado el tiempo de bloqueo, se informa al usuario de que la sesión se ha desbloqueado.
        redirectAttributes.addFlashAttribute("borradoFlash", "Sesión desbloqueada");
        return new RedirectView("authusuario");
    }

    public RedirectView informacionBloqueoSesion(RedirectAttributes redirectAttributes) {
        servicioSesion.setIntentosInicioSesion(0);
        servicioSesion.setFechaBloqueo(LocalDateTime.now());
        servicioSesion.setMotivoBloqueo("demasiados intentos de sesión");
        redirectAttributes.addFlashAttribute("errorFlash",
                "Sesión bloqueada por " + servicioSesion.getMotivoBloqueo());
        return new RedirectView("authusuario");
    }

    public RedirectView bloqueoUsuario(RedirectAttributes redirectAttributes) {
        servicioSesion.getUsuarioParaLogin().setIntentosFallidosLogin(0);
        if (servicioSesion.getListaMotivosBloqueo() == null) {
            servicioSesion.setListaMotivosBloqueo(motivoBloqueoServiceImpl.devuelveMotivosBloqueo());
        }
        // Se asigna un motivo de bloqueo al usuario que intenta hacer el login.
        for (MotivoBloqueo mb : servicioSesion.getListaMotivosBloqueo()) {
            if (mb.getMinutosBloqueo() == 15) {
                servicioSesion.getUsuarioParaLogin().setMotivoBloqueo(mb);
            }
        }
        // Se añade una fecha de desbloqueo teniendo en cuenta el tiempo de bloqueo.
        servicioSesion.getUsuarioParaLogin().setFechaDesbloqueo(LocalDateTime.now().plusMinutes(
                servicioSesion.getUsuarioParaLogin().getMotivoBloqueo().getMinutosBloqueo()));
        try {
            usuarioEmpleadoClienteServiceImpl.actualizaUsuarioEmpleadoCliente(servicioSesion.getUsuarioParaLogin().getId(),
                    servicioSesion.getUsuarioParaLogin());
            redirectAttributes.addFlashAttribute("errorFlash", "Usuario bloqueado por " +
                    servicioSesion.getUsuarioParaLogin().getMotivoBloqueo().getMotivo());
        } catch (NoEncontradoException e) {
            redirectAttributes.addFlashAttribute("errorFlash", e.getMessage());
        }
        return new RedirectView("authusuario");
    }

}
