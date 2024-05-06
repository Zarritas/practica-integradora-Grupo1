package org.grupo1.tienda.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequestScope
@NoArgsConstructor @AllArgsConstructor @Data
public class ManejoCookieVisitas {
    private String nombreUsuario;
    private String valorCookie;
    private String contadorVisitas;

    public String devuelveNumeroVisitas() {
        if (valorCookie.equals("0")) {
            contadorVisitas = "1";
            valorCookie = nombreUsuario + "<1";
        } else {
            Map<String, String> mapaCookie = creaMapaCookie();
            aumentaVisitaUsuario(mapaCookie);
            if (contadorVisitas == null) {
                contadorVisitas = "1";
                valorCookie = valorCookie + "#" + nombreUsuario + "<1";
            }  else {
                construyeValorCookie(mapaCookie);
            }
        }
        return contadorVisitas;
    }

    private Map<String, String> creaMapaCookie() {
        Map<String, String> mapaCookie = new LinkedHashMap<>();
        if (valorCookie.contains("#")) {
            String[] spliteado = valorCookie.split("#");
            for (String tupla : spliteado) {
                String[] pares = tupla.split("<");
                mapaCookie.put(pares[0], pares[1]);
            }
        } else {
            String[] pares = valorCookie.split("<");
            mapaCookie.put(pares[0], pares[1]);
        }
        return mapaCookie;
    }

    private void aumentaVisitaUsuario(Map<String, String> mapaCookie) {
        for (String usuario : mapaCookie.keySet()) {
            if (usuario.equals(nombreUsuario)) {
                String valor = mapaCookie.get(usuario);
                int numVisitas = Integer.parseInt(valor);
                numVisitas++;
                contadorVisitas = ""+numVisitas;
                mapaCookie.put(usuario, ""+numVisitas);
            }
        }
    }

    private void construyeValorCookie(Map<String, String> mapaCookie) {
        int iteracion = 0;
        for (String clave : mapaCookie.keySet()) {
            String valor = mapaCookie.get(clave);
            if (iteracion == 0) {
                valorCookie = clave + "<" + valor;
            } else {
                valorCookie = valorCookie + "#" + clave + "<" + valor;
            }
            iteracion ++;
        }
    }

    public Set<String> devuelveConjuntoUsuariosAutentificados(String contenidoCookie) {
        Map<String, String> mapaCookie = new LinkedHashMap<>();
        if (contenidoCookie.contains("#")) {
            String[] spliteado = contenidoCookie.split("#");
            for (String tupla : spliteado) {
                String[] pares = tupla.split("<");
                mapaCookie.put(pares[0], pares[1]);
            }
        } else {
            String[] pares = contenidoCookie.split("<");
            mapaCookie.put(pares[0], pares[1]);
        }
        return mapaCookie.keySet();
    }
}
