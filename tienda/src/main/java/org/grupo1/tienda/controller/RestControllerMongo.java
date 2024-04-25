package org.grupo1.tienda.controller;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.grupo1.tienda.component.AgregarDatosMongo.COLECCION;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
public class RestControllerMongo {

    @GetMapping("/listado")
    public List<Document> obtenerProductos() {
        List<Document> productos = new ArrayList<>();
        try (MongoCursor<Document> cursor = COLECCION.find().iterator()) {
            while (cursor.hasNext()) {
                Document producto = cursor.next();
                productos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    @PostMapping("/crear/{id}/{name}/{descripcion}/{image}/{cantidad}")
    public void crearProducto(@PathVariable Long id,
                               @PathVariable String name,
                               @PathVariable String descripcion,
                               @PathVariable String image,
                               @PathVariable Long cantidad) {
        image = "http://172.19.0.3:8080/tienda/images/"+image;
//        image = "http://localhost:8080/images/"+image;

        Document data = new Document().append("_id", id)
                .append("name", name)
                .append("descripcion", descripcion)
                .append("image", image)
                .append("cantidad", cantidad);
        COLECCION.insertOne(data);
    }
    @PostMapping("/actualizar/{id}/{cantidad}")
    public void actualizarProducto(@PathVariable int id, @PathVariable String cantidad) {
        COLECCION.updateOne(Filters.eq("_id", id), Updates.set("cantidad", cantidad));
    }

    @DeleteMapping("/borrar-productos")
    public void borrarProductos() {
        COLECCION.deleteMany(Filters.exists("_id"));
    }
}
