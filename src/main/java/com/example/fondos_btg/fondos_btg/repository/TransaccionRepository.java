package com.example.fondos_btg.fondos_btg.repository;

import com.example.fondos_btg.fondos_btg.model.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransaccionRepository extends MongoRepository<Transaccion, String> {

    // Devuelve todas las transacciones de un cliente específico
    List<Transaccion> findByClienteId(String clienteId);
}
