package com.example.fondos_btg.fondos_btg.dto;

import lombok.Data;

/**
 * DTO para la solicitud de login
 */
@Data
public class LoginRequest {
    private String usuario;
    private String password;
}