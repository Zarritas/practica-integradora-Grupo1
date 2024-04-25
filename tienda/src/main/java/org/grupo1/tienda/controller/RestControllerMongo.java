package org.grupo1.tienda.controller;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
public class RestControllerMongo {

    static MongoCollection<Document> collection;
    @Bean
    private static void conectarMongo(){
        String uri = "mongodb://172.19.0.5:27017/tienda";
        try (MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase mongoDatabase = mongoClient.getDatabase("tienda");
            collection = mongoDatabase.getCollection("producto");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/listado")
    public FindIterable<Document> obtenerProductos() {
        collection = collection;
        return collection.find();
    }

    @PostMapping("/crear/{id}/{name}/{descripcion}/{image}/{cantidad}")
    public void crearProducto(@PathVariable Long id,
                               @PathVariable String name,
                               @PathVariable String descripcion,
                               @PathVariable String image,
                               @PathVariable Long cantidad) {
        image = "http://localhost:8080/images/"+image;

        Document data = new Document().append("id", id)
                .append("name", name)
                .append("descripcion", descripcion)
                .append("image", image)
                .append("cantidad", cantidad);
        collection.insertOne(data);
    }
    @PostMapping("/actualizar/{id}/{descripcion}")
    public void actualizarProducto(@PathVariable String id, @PathVariable String descripcion) {
        Bson filter = Filters.eq("id", id);
        Bson nuevaDescripcion = Updates.set("descripcion", descripcion);
        collection.updateOne(filter, nuevaDescripcion);
    }

    @DeleteMapping("/borrar-productos")
    public void borrarProductos() {
        collection = collection;
        collection.deleteMany(Filters.exists("id"));
    }
}
