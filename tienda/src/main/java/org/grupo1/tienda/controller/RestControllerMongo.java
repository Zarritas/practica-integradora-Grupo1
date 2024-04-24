package org.grupo1.tienda.controller;

import com.mongodb.ClientSessionOptions;
import com.mongodb.ServerAddress;
import com.mongodb.annotations.Beta;
import com.mongodb.client.*;
import com.mongodb.client.internal.MongoClientImpl;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.connection.ClusterDescription;
import jakarta.servlet.http.HttpSession;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.grupo1.tienda.model.entity.Producto;
import org.grupo1.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
public class RestControllerMongo {

    static MongoCollection<Document> collection;
    @Bean
    private static void mongo(){
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
        UpdateResult updateOne = collection.updateOne(filter, nuevaDescripcion);
    }

    @DeleteMapping("/borrar-productos")
    public void borrarProductos() {
        collection.deleteMany(Filters.);
    }
}
