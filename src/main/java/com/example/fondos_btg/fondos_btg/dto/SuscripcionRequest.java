package com.example.fondos_btg.fondos_btg.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Objeto para recibir datos de suscripción desde el frontend
 */
public class SuscripcionRequest {
    @NotNull
    private String clienteId;
    @NotNull
    private Integer fondoId;
    @NotNull
    private Double monto;

    // Getters y Setters
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public Integer getFondoId() { return fondoId; }
    public void setFondoId(Integer fondoId) { this.fondoId = fondoId; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
}