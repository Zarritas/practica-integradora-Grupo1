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
        Map<String,String> camposConErrores = new HashMap<>();

        if (todosLosParametros.isEmpty()) {
            camposConErrores.put("general", "No se han proporcionado datos para guardar.");
            response.put("success", false);
            response.put("mensaje", "Error al crear el producto");
            response.put("camposConErrores", camposConErrores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List <Atributo> atributos = new ArrayList<>();
        Document producto = new Document();
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
                    try {
                        producto.append(atributo.getNombre(),LocalDate.parse(atributo.getValor()));
                    }catch (Exception e){
                        camposConErrores.put(atributo.getNombre(), "Formato de fecha no permitido");
                    }
                    break;
                case "Number":
                    try{

                        if(atributo.getNombre().equals("precio") && atributo.getValor().isEmpty())
                            camposConErrores.put(atributo.getNombre(), "El precio es obligatorio");
                        else
                            producto.append(atributo.getNombre(), Integer.parseInt(atributo.getValor()));

                    }catch (Exception e){
                        camposConErrores.put(atributo.getNombre(), "El campo tiene que ser un número");
                    }
                    break;
                case "Boolean":
                    try {
                        producto.append(atributo.getNombre(), Boolean.parseBoolean(atributo.getValor()));
                    }catch (Exception e){
                        camposConErrores.put(atributo.getNombre(), "Escribir true or false");
                    }
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
                    try {
                        if (atributo.getNombre().equals("imagen_perfil"))
                                producto.append(atributo.getNombre(), new Binary(BsonBinarySubType.BINARY, imagen_perfil.getBytes()));
                        else {
                            List<Binary> listaImagenes = new ArrayList<>();
                            for (MultipartFile imagen : imagenes)
                                listaImagenes.add(new Binary(BsonBinarySubType.BINARY, imagen.getBytes()));
                            producto.append(atributo.getNombre(), listaImagenes);
                        }
                    }catch (Exception e){
                        camposConErrores.put(atributo.getNombre(),"Error al subir las imagenes");
                    }
                    break;
                default:
                    switch (atributo.getNombre()) {
                        case "nombre":
                            if (atributo.getValor().isEmpty())
                                camposConErrores.put(atributo.getNombre(), "El nombre es obligatorio");
                            else
                                producto.append(atributo.getNombre(), atributo.getValor());
                            break;
                        case "descripcion":
                            if (atributo.getValor().isEmpty())
                                camposConErrores.put(atributo.getNombre(), "La descripción es obligatoria");
                            else
                                producto.append(atributo.getNombre(), atributo.getValor());
                            break;
                        case "categoria":
                            if (atributo.getValor().isEmpty())
                                camposConErrores.put(atributo.getNombre(), "La categoría es obligatoria");
                            else
                                producto.append(atributo.getNombre(), atributo.getValor());
                            break;
                        case "tipo":
                            if (atributo.getValor().isEmpty())
                                camposConErrores.put(atributo.getNombre(), "El tipo es obligatorio");
                            else
                                producto.append(atributo.getNombre(), atributo.getValor());
                            break;
                        default:
                            producto.append(atributo.getNombre(), atributo.getValor());
                            break;
                    }
                    break;
            }
        }
        if (todosLosParametros.isEmpty()) {
            camposConErrores.put("general", "No se han proporcionado datos para guardar.");
            response.put("success", false);
            response.put("mensaje", "Error al crear el producto");
            response.put("camposConErrores", camposConErrores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!camposConErrores.isEmpty()) {
            response.put("success", false);
            response.put("mensaje", "Error al crear el producto");
            response.put("camposConErrores", camposConErrores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        // Si no hay errores, insertar el producto en la base de datos
        conexionMongo.insertOne(producto);
        response.put("correcto", true);
        response.put("message", "Producto creado correctamente.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }


    @PostMapping("/actualizar/{id}")
    public void actualizarProducto(@PathVariable int id, @RequestParam Map<String,String> todosLosParametros) {
        List<Bson> actualizaciones = new ArrayList<>();
        for (Map.Entry<String,String> parametros : todosLosParametros.entrySet()) {
            switch (parametros.getKey()) {
                case "cantidad" -> actualizaciones.add(Updates.set(parametros.getKey(),Integer.parseInt(parametros.getValue())));
                case "image" -> actualizaciones.add(Updates.set(parametros.getKey(),"http://172.19.0.3:8080/tienda/images/"+parametros.getValue()));
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