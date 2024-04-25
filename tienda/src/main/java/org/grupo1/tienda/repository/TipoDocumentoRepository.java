package org.grupo1.tienda.repository;

import org.grupo1.tienda.model.catalog.TipoDocumentoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoCliente, Long> {
}
