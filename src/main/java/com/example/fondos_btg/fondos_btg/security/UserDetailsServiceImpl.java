package com.example.fondos_btg.fondos_btg.security;

import com.example.fondos_btg.fondos_btg.model.Cliente;
import com.example.fondos_btg.fondos_btg.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteService clienteService;

    @Override
    public UserDetails loadUserByUsername(String clienteId) throws UsernameNotFoundException {
        Cliente cliente = clienteService.findByClienteId(clienteId)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado: " + clienteId));
        return org.springframework.security.core.userdetails.User
                .withUsername(cliente.getClienteId())
                .password("")
                .authorities("CLIENTE")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}