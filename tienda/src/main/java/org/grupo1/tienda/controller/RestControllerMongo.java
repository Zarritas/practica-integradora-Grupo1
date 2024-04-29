package org.grupo1.tienda.controller;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.grupo1.tienda.config.MongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
public class RestControllerMongo {
    @Autowired
    MongoCollection<Document> conexionMongo = MongoConfig.conectarMongo();

    @GetMapping("/listado")
    public List<Document> obtenerProductos() {
        List<Document> productos = new ArrayList<>();
        try (MongoCursor<Document> cursor = conexionMongo.find().iterator()) {
            while (cursor.hasNext()) {
                Document producto = cursor.next();
                productos.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    @PostMapping("/crear")
    public void crearProducto(@RequestParam Map<String,String> todosLosParametros) {
        Document data = new Document();
        for (Map.Entry<String,String> parametros : todosLosParametros.entrySet()) {
            switch (parametros.getKey()) {
                case "image":
                    String imagen = "http://localhost:8080/images/"+parametros.getValue();
//                    String imagen = "http://172.19.0.3:8080/images/"+parametros.getValue();
                    data.append(parametros.getKey(), imagen);
                    break;
                default:
                    data.append(parametros.getKey(), parametros.getValue());
                    break;
            }
        }
        conexionMongo.insertOne(data);
    }
    @PostMapping("/actualizar/{id}")
    public void actualizarProducto(@PathVariable int id, @RequestParam Map<String,String> todosLosParametros) {
        for (Map.Entry<String,String> parametros : todosLosParametros.entrySet()) {
            conexionMongo.updateOne(Filters.eq("_id",id), Updates.set(parametros.getKey(), parametros.getValue()));
        }
    }

    @DeleteMapping("/borrar-por-id/{id}")
    public void borrarProducto(@PathVariable Long id) {
        conexionMongo.deleteOne(Filters.eq("_id",id));
    }

    @DeleteMapping("/borrar-todo")
    public void borrarProductos() {
        conexionMongo.deleteMany(Filters.exists("_id"));
    }
}