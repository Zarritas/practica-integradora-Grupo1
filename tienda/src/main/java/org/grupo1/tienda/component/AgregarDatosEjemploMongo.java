package org.grupo1.tienda.component;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.grupo1.tienda.config.MongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class AgregarDatosEjemploMongo {
    @Autowired
    MongoCollection<Document> conexionMongo = MongoConfig.conectarMongo();

//    @Bean
//    private void agregarProductos(){
//        Document data = new Document().append("_id", 1)
//                .append("name", "Peluche Pikachu")
//                .append("descripcion", "Peluche de Pikachu estilo oriental")
//                .append("image", "http://172.19.0.3:8080/tienda/images/pikachu-peluche.jpg")
////                .append("image", "http://localhost:8080/images/pikachu-peluche.jpg")
//                .append("cantidad", 0);
//        conexionMongo.insertOne(data);
//
//        Document data2 = new Document().append("_id", 2)
//                .append("name", "Pijama Pikachu")
//                .append("descripcion", "Pijama de Pikachu estilo oriental")
//                .append("image", "http://172.19.0.3:8080/tienda/images/pikachu-pijama.jpg")
////                .append("image", "http://localhost:8080/images/pikachu-pijama.jpg")
//                .append("cantidad", 100);
//        conexionMongo.insertOne(data2);
//    }
}
