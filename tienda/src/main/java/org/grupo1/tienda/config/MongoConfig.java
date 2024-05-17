package org.grupo1.tienda.config;

import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.context.annotation.*;

@Configuration
public class MongoConfig {

    public static MongoClient mongoClient = MongoClients.create("mongodb://root:admin@mongo.poketienda.com:27017/tienda?authSource=admin");

    @Bean
    public static MongoCollection<Document> conectarProducto() {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("tienda");
        return mongoDatabase.getCollection("producto");
    }
    public static MongoCollection<Document> conectarCarrito(){
        MongoDatabase mongoDatabase = mongoClient.getDatabase("tienda");
        return mongoDatabase.getCollection("carrito");
    }
}