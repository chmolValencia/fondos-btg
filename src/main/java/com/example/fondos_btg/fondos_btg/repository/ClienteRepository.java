package com.example.fondos_btg.fondos_btg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.fondos_btg.fondos_btg.model.Cliente;

import java.util.Optional;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    /**
     * Busca un cliente por su ID personalizado (ej: CUST123)
     */
    Optional<Cliente> findByClienteId(String usuario);
}