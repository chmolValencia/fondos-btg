// src/main/java/com/fondos_btg/service/TransaccionService.java
package com.example.fondos_btg.fondos_btg.service;

import com.example.fondos_btg.fondos_btg.model.Transaccion;
import com.example.fondos_btg.fondos_btg.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    public List<Transaccion> obtenerHistorial(String clienteId) {
        return transaccionRepository.findByClienteId(clienteId);
    }

    public Transaccion registrarTransaccion(Transaccion transaccion) {
        transaccion.setFecha(java.time.LocalDateTime.now());
        return transaccionRepository.save(transaccion);
    }


}