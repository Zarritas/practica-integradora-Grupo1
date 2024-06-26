package org.grupo1.tienda.component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.grupo1.tienda.model.entity.UsuarioEmpleadoCliente;
import org.grupo1.tienda.service.ServicioSesion;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@AllArgsConstructor
@Data
public class GestionCookies {
    private final ServicioSesion servicioSesion;

    public UsuarioEmpleadoCliente numeroAccesosPorUsuario(HttpServletResponse respuestaHttp, String contenidoCookie) {
        // Lógica de cookie que cuenta el número de accesos por usuario.
        UsuarioEmpleadoCliente uec = servicioSesion.getUsuarioLoggeado();
        ManejoCookieVisitas manejoCookieVisitas = new ManejoCookieVisitas(uec.getEmail(), contenidoCookie, null);
        String numeroVisitas = manejoCookieVisitas.devuelveNumeroVisitas(true, true);
        Cookie miCookie = new Cookie("autentificaciones", manejoCookieVisitas.getValorCookie());
        miCookie.setPath("/");
        respuestaHttp.addCookie(miCookie);
        // Se guarda el número de accesos en el usuario.
        uec.setNumeroAccesos(Integer.valueOf(numeroVisitas));
        // Se devuelve el usuario que hay que actualizar en la base de datos.
        return uec;
    }

    public void aumentoPaginasPorUsuario(HttpServletResponse respuestaHttp, String contenidoCookie) {
        // Lógica de cookie que cuenta el número de páginas por las que pasa el usuario.
        UsuarioEmpleadoCliente uec = servicioSesion.getUsuarioLoggeado();
        ManejoCookieVisitas manejoCookiePaginas = new ManejoCookieVisitas(uec.getEmail(), contenidoCookie, null);
        manejoCookiePaginas.devuelveNumeroVisitas(true, false);
        Cookie miCookie = new Cookie("paginas-visitadas", manejoCookiePaginas.getValorCookie());
        miCookie.setPath("/");
        respuestaHttp.addCookie(miCookie);
    }

    public void reseteoNumeroPaginas(HttpServletResponse respuestaHttp, String contenidoCookie) {
        // Lógica de cookie que pone a 0 el número de páginas por las que pasa el usuario.
        UsuarioEmpleadoCliente uec = servicioSesion.getUsuarioLoggeado();
        ManejoCookieVisitas manejoCookiePaginas = new ManejoCookieVisitas(uec.getEmail(), contenidoCookie, null);
        manejoCookiePaginas.devuelveNumeroVisitas(false, false);
        Cookie miCookie = new Cookie("paginas-visitadas", manejoCookiePaginas.getValorCookie());
        miCookie.setPath("/");
        respuestaHttp.addCookie(miCookie);
    }

}
