package org.grupo1.tienda.component;

import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.grupo1.tienda.config.MongoConfig.*;

/*
@Component
public class DatosPruebaMongo {
    @Bean
    public void agregarDatosJson() {
        conectarProducto().drop();
        try (BufferedReader br = new BufferedReader(new FileReader("/usr/local/tomcat/webapps/ROOT/WEB-INF/classes/data.json"))) {
            String line;
            List<Document> documents = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                documents.add(Document.parse(line));
            }
            conectarProducto().insertMany(documents);
        } catch (IOException e) {
            System.err.println("Archivo vacío");
        }
    }
}*/ //