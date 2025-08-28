package com.example.fondos_btg.fondos_btg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.fondos_btg.fondos_btg.model.Transaccion;

import java.util.List;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
    List<Transaccion> findByClienteId(String clienteId);
}