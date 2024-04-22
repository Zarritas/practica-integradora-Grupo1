package org.grupo1.tienda.controller;

import org.grupo1.tienda.model.entity.Producto;
import org.grupo1.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class RestControllerMongo {

    @Autowired
    private ProductoRepository repositorioProducto;

    @GetMapping("/listado")
    public List<Producto> obtenerProductos() {
        return repositorioProducto.findAll();
    }

    @PostMapping("/crear/{id}/{nombre}/{descripcion}/{titulo}")
    private void crearProducto(@PathVariable String id,
                               @PathVariable String nombre,
                               @PathVariable String descripcion,
                               @PathVariable String titulo) {

        Producto p = new Producto(id, nombre, descripcion, titulo);
        repositorioProducto.save(p);
    }
}
