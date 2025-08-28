package com.example.fondos_btg.fondos_btg.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Entidad que representa la suscripción activa de un cliente a un fondo.
 * Un cliente puede tener múltiples suscripciones (una por fondo).
 */
@Data
@Document(collection = "suscripciones")
@Getter
@Setter
public class Suscripcion {

    @Id
    private String id;

    private String clienteId;       // Cliente que se suscribe
    private Integer fondoId;        // Fondo al que se suscribe
    private LocalDateTime fechaSuscripcion; // Fecha de inicio
    private String estado;          // ACTIVO o CANCELADO
    private Double montoInicial;    // Monto con el que se vinculó
}