// src/main/java/com/fondos_btg/dto/CancelacionRequest.java
package com.example.fondos_btg.fondos_btg.dto;

import lombok.Data;

/**
 * DTO para solicitar la cancelación de una suscripción a un fondo
 */
@Data
public class CancelacionRequest {
    private String clienteId;
    private Integer fondoId;
}