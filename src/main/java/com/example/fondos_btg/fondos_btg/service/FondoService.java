package com.example.fondos_btg.fondos_btg.service;


import com.example.fondos_btg.fondos_btg.model.Fondo;
import com.example.fondos_btg.fondos_btg.repository.FondoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar fondos.
 * Proporciona métodos para listar y buscar fondos.
 */
@Service
public class FondoService {

    @Autowired
    private FondoRepository fondoRepository;

    // Devuelve todos los fondos activos
    public List<Fondo> obtenerTodos() {
        List<Fondo> activos = fondoRepository.findAll().stream()
    .filter(Fondo::isActivo)
    .collect(java.util.stream.Collectors.toList());
        return activos;
    }

    /**
     * Busca un fondo por su ID numérico
     */
    public Optional<Fondo> findById(Integer id) {
        return fondoRepository.findByFondoId(id);
    }
}