package org.grupo1.tienda.controller;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.grupo1.tienda.config.MongoConfig;
import org.grupo1.tienda.model.mongo.Atributo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
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
    @GetMapping("/detalle/{_id}")
    public Map<String, Object> detalleProducto(@PathVariable String _id) {
        Map<String, Object> resultado = new HashMap<>();
        try {
            Document producto = conexionMongo.find(Filters.eq("_id",Integer.parseInt(_id))).first();
            if (producto != null) {
                resultado.put("documento", producto);

                // Determinar los tipos de datos de cada atributo
                Map<String, String> tiposDeDatos = new HashMap<>();
                for (String campo : producto.keySet()) {
                    Object valor = producto.get(campo);
                    String tipo = obtenerTipoDato(valor);
                    tiposDeDatos.put(campo, tipo);
                }
                resultado.put("tipos_de_datos", tiposDeDatos);
            } else {
                resultado.put("error", "Producto no encontrado");
            }
        } catch (Exception e) {
            resultado.put("error", "Error al obtener el producto");
            e.printStackTrace();
        }
        return resultado;
    }
    private String obtenerTipoDato(Object valor) {
        if (valor == null) {
            return "null";
        } else {
            return valor.getClass().getSimpleName();
        }
    }

    @PostMapping("/crear")
    public void crearProducto(@RequestParam Map<String,String> todosLosParametros, @RequestParam MultipartFile imagenes) {
        Document producto = new Document();
        Document ultimoId = conexionMongo.find()
                .projection(Projections.include("_id"))
                .sort(Sorts.descending("_id"))
                .limit(1)
                .first();
        List <Atributo> atributos = new ArrayList<>();
        for(String atributo: todosLosParametros.keySet()){
            if (atributo.startsWith("_")) {
                atributos.add(new Atributo(
                        todosLosParametros.get(atributo),
                        todosLosParametros.get(atributo.substring(1)),
                        todosLosParametros.get("tipo-"+atributo.substring(1))));
            }
        }
        for (Atributo atributo : atributos){
            try{
                switch (atributo.getTipo()){
                    case "Date":
                        producto.append(atributo.getNombre(),LocalDate.parse(atributo.getValor()));
                        break;
                    case "Number":
                        producto.append(atributo.getNombre(),Integer.parseInt(atributo.getValor()));
                        break;
                    case "Boolean":
                        producto.append(atributo.getNombre(),Boolean.parseBoolean(atributo.getValor()));
                        break;
                    case "Array":
                        producto.append(atributo.getNombre(),atributo.getValor().split(","));
                        break;
                    case "Object":
                        Document auxDocumento = new Document();
                        String[] pares = atributo.getValor().split(", ");

                        for (String par : pares) {
                            String[] partes = par.split("[:;]");
                            String clave = partes[0].trim();
                            String valor = partes[1].trim();
                            String tipo = partes[2].trim();

                            switch (tipo) {
                                case "int":
                                    auxDocumento.append(clave, Integer.parseInt(valor));
                                    break;
                                case "boolean":
                                    auxDocumento.append(clave, Boolean.parseBoolean(valor));
                                    break;
                                case "Date":
                                    producto.append(clave,LocalDate.parse(valor));
                                    break;
                                default:
                                    auxDocumento.append(clave, valor);
                                    break;
                            }
                        }
                        producto.append(atributo.getNombre(),auxDocumento);
                        break;
                    case "Binary":
                        producto.append(atributo.getNombre(),new Binary(BsonBinarySubType.BINARY, imagenes.getBytes()));
                        break;
                    default:
                        producto.append(atributo.getNombre(),atributo.getValor());
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (ultimoId != null){
            producto.append("_id",ultimoId.getInteger("_id")+1);
        }else{
            producto.append("_id",1);
        }
        conexionMongo.insertOne(producto);
    }

    @PostMapping("/actualizar/{id}")
    public void actualizarProducto(@PathVariable int id, @RequestParam Map<String,String> todosLosParametros) {
        List<Bson> actualizaciones = new ArrayList<>();
        for (Map.Entry<String,String> parametros : todosLosParametros.entrySet()) {
            switch (parametros.getKey()) {
                case "cantidad" -> actualizaciones.add(Updates.set(parametros.getKey(),Integer.parseInt(parametros.getValue())));
                case "image" -> actualizaciones.add(Updates.set(parametros.getKey(),"http://localhost:8080/images/"+parametros.getValue()));
                default -> actualizaciones.add(Updates.set(parametros.getKey(), parametros.getValue()));
            }
        }
        conexionMongo.updateOne(Filters.eq("_id",id),actualizaciones,new UpdateOptions().upsert(true));
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