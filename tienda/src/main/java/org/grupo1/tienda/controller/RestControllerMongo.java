package org.grupo1.tienda.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.grupo1.tienda.model.entity.Producto;
import org.grupo1.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
public class RestControllerMongo {

    @Autowired
    private ProductoRepository repositorioProducto;

    @GetMapping("/listado")
    public List<Producto> obtenerProductos() {
        return repositorioProducto.findAll();
    }

    @PostMapping("/crear/{id}/{name}/{descripcion}/{image}/{cantidad}")
    public void crearProducto(@PathVariable Long id,
                               @PathVariable String name,
                               @PathVariable String descripcion,
                               @PathVariable String image,
                               @PathVariable Long cantidad) {
        image = "http://localhost:8080/images/"+image;
        Producto p = new Producto(id,name, descripcion, image,cantidad);
        repositorioProducto.save(p);
    }

    @DeleteMapping("/borrar-productos")
    public void borrarProductos() {
        repositorioProducto.deleteAll();
    }
}
