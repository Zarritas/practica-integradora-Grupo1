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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                Map<String, String> tiposDeDatos = new HashMap<>();
                for (String campo : producto.keySet()) {
                    Object valor = producto.get(campo);
                    String tipo = obtenerTipoDato(valor);
                    tiposDeDatos.put(campo, tipo);
                }
                resultado.put("tipos_de_datos", tiposDeDatos);
            } else resultado.put("error", "Producto no encontrado");

        } catch (Exception e) {
            resultado.put("error", "Error al obtener el producto");
            e.printStackTrace();
        }
        return resultado;
    }
    private String obtenerTipoDato(Object valor) {
        return valor == null ? "null" : valor.getClass().getSimpleName();
    }

    @PostMapping("/crear")
    public ResponseEntity<Map<String,Object>> crearProducto(@RequestParam Map<String,String> todosLosParametros, @RequestParam MultipartFile imagen_perfil, @RequestParam List<MultipartFile> imagenes) {
        Map<String, Object> response = new HashMap<>();
        List <Atributo> atributos = new ArrayList<>();
        Document producto = new Document();
        try {
            Document ultimoId = conexionMongo.find()
                    .projection(Projections.include("_id"))
                    .sort(Sorts.descending("_id"))
                    .limit(1)
                    .first();

            producto.append("_id", ultimoId != null ? ultimoId.getInteger("_id") + 1 : 1);

            for(String atributo: todosLosParametros.keySet())
                if (atributo.startsWith("_"))
                    atributos.add(new Atributo(todosLosParametros.get(atributo),
                            todosLosParametros.get(atributo.substring(1)),
                            todosLosParametros.get("tipo-"+atributo.substring(1))));

            for (Atributo atributo : atributos){
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
                                case "int" -> auxDocumento.append(clave, Integer.parseInt(valor));
                                case "boolean"-> auxDocumento.append(clave, Boolean.parseBoolean(valor));
                                case "Date" -> producto.append(clave,LocalDate.parse(valor));
                                default -> auxDocumento.append(clave, valor);
                            }
                        }
                        producto.append(atributo.getNombre(),auxDocumento);
                        break;
                    case "Binary":
                        if (atributo.getNombre().equals("imagen_perfil"))
                            producto.append(atributo.getNombre(),new Binary(BsonBinarySubType.BINARY, imagen_perfil.getBytes()));
                        else {
                            List<Binary> listaImagenes = new ArrayList<>();
                            for (MultipartFile imagen : imagenes)
                                listaImagenes.add(new Binary(BsonBinarySubType.BINARY, imagen.getBytes()));
                            producto.append(atributo.getNombre(), listaImagenes);
                        }
                        break;
                    default:
                        producto.append(atributo.getNombre(),atributo.getValor());
                        break;
                }
            }

            conexionMongo.insertOne(producto);
            response.put("correcto",true);
            response.put("message", "Producto creado correctamente.");

        }catch (Exception e){
            e.printStackTrace();
            List<String> camposConErrores = new ArrayList<>();

            camposConErrores.add("nombre"); // Agrega aquí los nombres de los campos con errores
            response.put("success", false);
            response.put("message", "Error al crear el producto: " + e.getMessage());
            response.put("camposConErrores", camposConErrores);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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