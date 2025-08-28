package com.example.fondos_btg.fondos_btg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.fondos_btg.fondos_btg.model.Suscripcion;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuscripcionRepository extends MongoRepository<Suscripcion, String> {

    /**
     * Obtiene todas las suscripciones de un cliente
     */
    List<Suscripcion> findByClienteId(String clienteId);

    /**
     * Busca una suscripción activa específica
     */
    Optional<Suscripcion> findByClienteIdAndFondoIdAndEstado(
        String clienteId, Integer fondoId, String estado);
}