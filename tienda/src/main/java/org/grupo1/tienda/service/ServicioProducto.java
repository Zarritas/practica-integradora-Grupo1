package org.grupo1.tienda.service;

import jakarta.validation.constraints.NotNull;
import org.bson.Document;
import org.grupo1.tienda.model.mongo.Atributo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

@Service
public class ServicioProducto {
    @NotNull
    public static void crearObjeto(Document auxDocumento, String[] pares) {
        for (String par : pares) {
            String[] partes = par.split(":");
            String clave = partes[0].trim();
            String valor = partes[1].trim();
            String tipo;
            try {
                tipo = partes[2].trim();
            }catch (Exception e) {
                tipo = "String";
            }
            switch (tipo) {
                case "int" -> auxDocumento.append(clave, Integer.parseInt(valor));
                case "boolean"-> auxDocumento.append(clave, Boolean.parseBoolean(valor));
                case "Date" -> auxDocumento.append(clave, LocalDate.parse(valor));
                default -> auxDocumento.append(clave, valor);
            }
        }
    }
    @NotNull
    public static void comprobarStringsObligatorios(Map<String, String> camposConErrores, Document producto, Atributo atributo) {
        switch (atributo.getNombre()) {
            case "nombre":
                if (atributo.getValor().isEmpty())
                    camposConErrores.put(atributo.getNombre(), "El nombre es obligatorio");
                else
                    producto.append(atributo.getNombre(), atributo.getValor());
                break;
            case "descripcion":
                if (atributo.getValor().isEmpty())
                    camposConErrores.put(atributo.getNombre(), "La descripción es obligatoria");
                else
                    producto.append(atributo.getNombre(), atributo.getValor());
                break;
            case "categoria":
                if (atributo.getValor().isEmpty())
                    camposConErrores.put(atributo.getNombre(), "La categoría es obligatoria");
                else
                    producto.append(atributo.getNombre(), atributo.getValor());
                break;
            case "tipo":
                if (atributo.getValor().isEmpty())
                    camposConErrores.put(atributo.getNombre(), "El tipo es obligatorio");
                else
                    producto.append(atributo.getNombre(), atributo.getValor());
                break;
            default:
                producto.append(atributo.getNombre(), atributo.getValor());
                break;
        }
    }
    public static void intentarAgregarArray(Map<String, String> camposConErrores, Document producto, Atributo atributo) {
        try {
            String[] valores = atributo.getValor().split(",");
            Document arrayDoc = new Document();
            arrayDoc.append("valores", Arrays.asList(valores));
            producto.append(atributo.getNombre(), arrayDoc);
        } catch (Exception e) {
            camposConErrores.put(atributo.getNombre(), "No se puede añadir el array");
        }
    }
    @NotNull
    public static String obtenerTipoDato(Object valor) {
        return valor == null ? "null" : valor.getClass().getSimpleName();
    }
}
