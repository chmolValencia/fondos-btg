package com.example.fondos_btg.fondos_btg.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Entidad que representa un cliente de BTG.
 * Almacena información de contacto y preferencias de notificación.
 */
@Data
@Document(collection = "clientes")
@Getter
@Setter
public class Cliente {

    @Id
    private String id;

    private String clienteId; // Identificador único del cliente (ej: CUST123)
    private String nombre;
    private String email;
    private String telefono;
    private String preferenciaNotificacion; // Valores: EMAIL, SMS, AMBOS
    private LocalDateTime fechaRegistro;   // Fecha de alta en el sistema
}