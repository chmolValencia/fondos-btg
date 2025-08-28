package com.example.fondos_btg.fondos_btg.dto;

import lombok.Data;

@Data
public class ClienteRequest {
    private String clienteId;
    private String nombre;
    private String email;
    private String telefono;
    private String preferenciaNotificacion; // EMAIL, SMS, AMBOS
}