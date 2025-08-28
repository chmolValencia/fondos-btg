package com.example.fondos_btg.fondos_btg.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.fondos_btg.fondos_btg.model.Fondo;
import java.util.Optional;

/**
 * Repositorio para operaciones CRUD sobre la colección 'fondos'.
 * Extiende MongoRepository para tener métodos como save(), findById(), findAll(), etc.
 */
@Repository
public interface FondoRepository extends MongoRepository<Fondo, String> {

    /**
     * Busca un fondo por su ID numérico (no por el ID de MongoDB).
     * @param fondoId ID del fondo
     * @return Optional con el fondo si existe
     */
    Optional<Fondo> findByFondoId(Integer fondoId);
}