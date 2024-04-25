package org.grupo1.tienda.controller;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
public class RestControllerMongo {

    static MongoCollection<Document> COLECCION;
    @Bean
    private static void conectarMongo(){
        String uri = "mongodb://root:admin@172.19.0.5:27017/tienda?authSource=admin";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("tienda");
        COLECCION = mongoDatabase.getCollection("producto");
    }

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
        image = "http://172.19.0.3:8080/images/"+image;

        Document data = new Document().append("id", id)
                .append("name", name)
                .append("descripcion", descripcion)
                .append("image", image)
                .append("cantidad", cantidad);
        COLECCION.insertOne(data);
    }
    @PostMapping("/actualizar/{id}/{descripcion}")
    public void actualizarProducto(@PathVariable int id, @PathVariable String descripcion) {
        COLECCION.updateOne(Filters.eq("id", id), Updates.set("descripcion", descripcion));
    }

    @DeleteMapping("/borrar-productos")
    public void borrarProductos() {
        COLECCION.deleteMany(Filters.exists("id"));
    }
}
