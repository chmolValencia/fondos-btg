package com.example.fondos_btg.fondos_btg.service;

import com.example.fondos_btg.fondos_btg.model.Transaccion;
import com.example.fondos_btg.fondos_btg.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public List<Transaccion> obtenerHistorial(String clienteId) {
        return transaccionRepository.findByClienteId(clienteId);
    }
}
