package com.example.fondos_btg.fondos_btg.service;

import com.example.fondos_btg.fondos_btg.exception.BusinessException;
import com.example.fondos_btg.fondos_btg.model.Cliente;
import com.example.fondos_btg.fondos_btg.model.Fondo;
import com.example.fondos_btg.fondos_btg.model.Suscripcion;
import com.example.fondos_btg.fondos_btg.model.Transaccion;
import com.example.fondos_btg.fondos_btg.repository.SuscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Servicio principal para gestionar suscripciones y cancelaciones.
 * Incluye lógica de negocio, validaciones y registro de transacciones.
 */
@Service
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final FondoService fondoService;
    private final ClienteService clienteService;
    private final TransaccionService transaccionService;
    private final NotificacionService notificacionService;

    // ✅ Inyección por constructor (mejor práctica)
    public SuscripcionService(
            SuscripcionRepository suscripcionRepository,
            FondoService fondoService,
            ClienteService clienteService,
            TransaccionService transaccionService,
            NotificacionService notificacionService) {
        this.suscripcionRepository = suscripcionRepository;
        this.fondoService = fondoService;
        this.clienteService = clienteService;
        this.transaccionService = transaccionService;
        this.notificacionService = notificacionService;
    }

    /**
     * Permite a un cliente suscribirse a un fondo.
     *
     * Validaciones:
     * - Cliente existe
     * - Fondo existe y está activo
     * - Monto >= monto mínimo
     * - No estar ya suscrito
     *
     * @param clienteId ID del cliente
     * @param fondoId ID del fondo
     * @param monto Monto inicial de inversión
     * @return Mensaje de éxito
     */
    @Transactional
    public String suscribir(String clienteId, Integer fondoId, Double monto) {
        // Validar cliente
        Optional<Cliente> clienteOpt = clienteService.findByClienteId(clienteId);
        if (clienteOpt.isEmpty()) {
            throw new BusinessException("Cliente no encontrado: " + clienteId);
        }

        // Validar fondo
        Optional<Fondo> fondoOpt = fondoService.findById(fondoId);
        if (fondoOpt.isEmpty() || !fondoOpt.get().isActivo()) {
            throw new BusinessException("Fondo no disponible: " + fondoId);
        }
        Fondo fondo = fondoOpt.get();

        // Validar monto
        if (monto == null || monto < fondo.getMontoMinimo()) {
            throw new BusinessException(
                    String.format("Monto insuficiente. Mínimo: $%,.0f COP", fondo.getMontoMinimo()));
        }

        // Evitar duplicados
        Optional<Suscripcion> existente = suscripcionRepository
                .findByClienteIdAndFondoIdAndEstado(clienteId, fondoId, "ACTIVO");
        if (existente.isPresent()) {
            throw new BusinessException("Ya está suscrito a este fondo.");
        }

        // Crear nueva suscripción
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setClienteId(clienteId);
        suscripcion.setFondoId(fondoId);
        suscripcion.setFechaSuscripcion(LocalDateTime.now());
        suscripcion.setEstado("ACTIVO");
        suscripcion.setMontoInicial(monto);
        suscripcionRepository.save(suscripcion);

        // Registrar transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setClienteId(clienteId);
        transaccion.setFondoId(fondoId);
        transaccion.setTipo("APERTURA");
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setMonto(monto);
        transaccion.setDetalle("Suscripción al fondo: " + fondo.getNombre());
        transaccionService.guardar(transaccion);

        // Enviar notificación
        notificacionService.enviarNotificacion(clienteId, fondo, "suscripción");

        return "Suscripción exitosa al fondo: " + fondo.getNombre();
    }

    /**
     * Cancela una suscripción activa.
     */
    @Transactional
    public String cancelar(String clienteId, Integer fondoId) {
        Optional<Suscripcion> suscripcionOpt = suscripcionRepository
                .findByClienteIdAndFondoIdAndEstado(clienteId, fondoId, "ACTIVO");

        if (suscripcionOpt.isEmpty()) {
            throw new BusinessException("No tiene una suscripción activa a este fondo.");
        }

        Suscripcion suscripcion = suscripcionOpt.get();
        suscripcion.setEstado("CANCELADO");
        suscripcionRepository.save(suscripcion);

        Optional<Fondo> fondoOpt = fondoService.findById(fondoId);
        Fondo fondo = fondoOpt.orElseGet(() -> {
            Fondo f = new Fondo();
            f.setNombre("Desconocido");
            return f;
        });

        // Registrar cancelación
        Transaccion transaccion = new Transaccion();
        transaccion.setClienteId(clienteId);
        transaccion.setFondoId(fondoId);
        transaccion.setTipo("CANCELACION");
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setMonto(suscripcion.getMontoInicial());
        transaccion.setDetalle("Cancelación del fondo: " + fondo.getNombre());
        transaccionService.guardar(transaccion);

        // Notificar
        if (fondoOpt.isPresent()) {
            notificacionService.enviarNotificacion(clienteId, fondo, "cancelación");
        }

        return "Cancelación exitosa.";
    }
}