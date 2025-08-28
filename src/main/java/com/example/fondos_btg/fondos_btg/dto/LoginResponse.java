package com.example.fondos_btg.fondos_btg.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para la respuesta del login con JWT
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String usuario ;
}