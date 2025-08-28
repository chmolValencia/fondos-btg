// ✅ ClienteService.java - Correcto
package com.example.fondos_btg.fondos_btg.service;

import com.example.fondos_btg.fondos_btg.model.Cliente;
import com.example.fondos_btg.fondos_btg.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    public Optional<Cliente> findByClienteId(String usuario) {
        return clienteRepository.findByClienteId(usuario); // ✅ ¡Aquí va el return!
    }
    public Cliente registrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}