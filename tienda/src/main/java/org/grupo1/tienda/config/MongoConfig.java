package org.grupo1.tienda.config;

import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.context.annotation.*;
/*
@Configuration
public class MongoConfig {
    @Bean
    public static MongoCollection<Document> conectarMongo(){
        String uri = "mongodb://root:admin@172.19.0.5:27017/tienda?authSource=admin";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("tienda");
        return mongoDatabase.getCollection("producto");
    }
}*/