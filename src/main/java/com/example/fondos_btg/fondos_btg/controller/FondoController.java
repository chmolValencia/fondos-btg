// FondoController.java
package com.example.fondos_btg.fondos_btg.controller;

import com.example.fondos_btg.fondos_btg.dto.ClienteRequest;
import com.example.fondos_btg.fondos_btg.dto.SuscripcionRequest;
import com.example.fondos_btg.fondos_btg.dto.CancelacionRequest;
import com.example.fondos_btg.fondos_btg.model.Cliente;
import com.example.fondos_btg.fondos_btg.model.Fondo;
import com.example.fondos_btg.fondos_btg.model.Transaccion;
import com.example.fondos_btg.fondos_btg.service.ClienteService;
import com.example.fondos_btg.fondos_btg.service.FondoService;
import com.example.fondos_btg.fondos_btg.service.SuscripcionService;
import com.example.fondos_btg.fondos_btg.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/fondos")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class FondoController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FondoService fondoService;

    @Autowired
    private SuscripcionService suscripcionService;

    @Autowired
    private TransaccionService transaccionService;

    // ✅ Público: Cualquiera puede ver los fondos
    @GetMapping
    public ResponseEntity<List<Fondo>> listarFondos() {
        return ResponseEntity.ok(fondoService.obtenerTodos());
    }

    // ✅ Protegido: Solo usuarios autenticados
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/suscribir")
    public ResponseEntity<String> suscribir(@RequestBody SuscripcionRequest request) {
        // ✅ Validar que el cliente autenticado sea el mismo que se está suscribiendo
        String clienteAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!clienteAutenticado.equals(request.getClienteId())) {
            return ResponseEntity.status(403).body("No tienes permiso para suscribirte en nombre de otro cliente.");
        }

        String resultado = suscripcionService.suscribir(
                request.getClienteId(),
                request.getFondoId(),
                request.getMonto()
        );
        return ResponseEntity.ok(resultado);
    }

    // ✅ Protegido: Solo usuarios autenticados
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/cancelar")
    public ResponseEntity<String> cancelar(@RequestBody CancelacionRequest request) {
        String clienteAutenticado = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!clienteAutenticado.equals(request.getClienteId())) {
            return ResponseEntity.status(403).body("No tienes permiso para cancelar en nombre de otro cliente.");
        }

        String resultado = suscripcionService.cancelar(
                request.getClienteId(),
                request.getFondoId()
        );
        return ResponseEntity.ok(resultado);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/historial/{clienteId}")
    public List<Transaccion> getHistorial(@PathVariable String clienteId) {
        return transaccionService.obtenerHistorial(clienteId);
    }



    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/clientes")
    public ResponseEntity<String> registrarCliente(@RequestBody ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(request.getClienteId());
        cliente.setNombre(request.getNombre());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setPreferenciaNotificacion(request.getPreferenciaNotificacion());
        cliente.setFechaRegistro(LocalDateTime.now());
        clienteService.registrar(cliente);
        return ResponseEntity.ok("Cliente registrado exitosamente");
    }
}