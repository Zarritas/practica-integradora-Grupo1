package org.grupo1.tienda.repository;

import org.grupo1.tienda.model.entity.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {
}
