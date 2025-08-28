package com.example.fondos_btg.fondos_btg.exception;


/**
 * Excepción para errores de negocio (no técnicos)
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}