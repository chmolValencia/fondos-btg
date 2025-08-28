package com.example.fondos_btg.fondos_btg.service;

import org.springframework.stereotype.Service;

import com.example.fondos_btg.fondos_btg.model.Fondo;

@Service
public class NotificacionService {
    public void enviarNotificacion(String clienteId, Fondo fondo, String accion) {
        System.out.println("[NOTIFICACIÓN] Cliente " + clienteId +" se ha " + accion + " al fondo " + fondo.getNombre());
    }
}