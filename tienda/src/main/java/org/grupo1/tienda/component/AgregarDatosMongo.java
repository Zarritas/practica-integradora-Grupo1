package org.grupo1.tienda.component;

import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AgregarDatosMongo {

    public static MongoCollection<Document> COLECCION;
    @Bean
    private static void conectarMongo(){
        String uri = "mongodb://root:admin@172.19.0.5:27017/tienda?authSource=admin";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("tienda");
        COLECCION = mongoDatabase.getCollection("producto");
    }

    @Bean
    private void agregarProductos(){
        Document data = new Document().append("_id", 1)
                .append("name", "Peluche Pikachu")
                .append("descripcion", "Peluche de Pikachu estilo oriental")
                .append("image", "http://172.19.0.3:8080/tienda/images/pikachu-peluche.jpg")
//                .append("image", "http://localhost:8080/images/pikachu-peluche.jpg")
                .append("cantidad", 0);
        COLECCION.insertOne(data);

        Document data2 = new Document().append("_id", 2)
                .append("name", "Pijama Pikachu")
                .append("descripcion", "Pijama de Pikachu estilo oriental")
                .append("image", "http://172.19.0.3:8080/tienda/images/pikachu-pijama.jpg")
//                .append("image", "http://localhost:8080/images/pikachu-pijama.jpg")
                .append("cantidad", 100);
        COLECCION.insertOne(data2);
    }
}
