package com.example.fondos_btg.fondos_btg.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Entidad que registra cada operación (apertura o cancelación) del cliente.
 * Sirve como historial de transacciones.
 */
@Data
@Document(collection = "transacciones")
@Getter
@Setter
public class Transaccion {

    @Id
    private String id;

    private String clienteId;
    private Integer fondoId;
    private String tipo;            // APERTURA o CANCELACION
    private LocalDateTime fecha;
    private Double monto;           // Monto involucrado
    private String detalle;         // Descripción de la operación
}