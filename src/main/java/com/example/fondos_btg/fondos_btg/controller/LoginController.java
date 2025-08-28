// src/main/java/com/example/fondos_btg/fondos_btg/controller/LoginController.java
package com.example.fondos_btg.fondos_btg.controller;

import com.example.fondos_btg.fondos_btg.dto.LoginRequest;
import com.example.fondos_btg.fondos_btg.dto.LoginResponse;
import com.example.fondos_btg.fondos_btg.model.Cliente;
import com.example.fondos_btg.fondos_btg.service.ClienteService;
import com.example.fondos_btg.fondos_btg.util.JwtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // Ajusta según tu frontend
public class LoginController {
    @JsonProperty("usuario")
    private String clienteId;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Validar que el clienteId y password no estén vacíos
        if (request.getUsuario() == null || request.getUsuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El clienteId es obligatorio");
        }

        Optional<Cliente> clienteOpt = clienteService.findByClienteId(request.getUsuario());

        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Cliente no encontrado");
        }

        // En un sistema real, validarías la contraseña aquí
        // Por ahora, asumimos que cualquier contraseña es válida

        String token = jwtUtil.generateToken(request.getUsuario());

        LoginResponse response = new LoginResponse(token, request.getUsuario());

        return ResponseEntity.ok(response);
    }
}