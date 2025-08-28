package com.example.fondos_btg;

import com.example.fondos_btg.fondos_btg.exception.BusinessException;
import com.example.fondos_btg.fondos_btg.model.Cliente;
import com.example.fondos_btg.fondos_btg.model.Fondo;
import com.example.fondos_btg.fondos_btg.model.Suscripcion;
import com.example.fondos_btg.fondos_btg.repository.SuscripcionRepository;
import com.example.fondos_btg.fondos_btg.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SuscripcionServiceTest {

    @Mock
    private FondoService fondoService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private TransaccionService transaccionService;

    @Mock
    private NotificacionService notificacionService;

    @Mock
    private SuscripcionRepository suscripcionRepository;

    @InjectMocks
    private SuscripcionService suscripcionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Caso 1: Cliente no existe
    @Test
    void suscribir_DebeLanzarError_SiClienteNoExiste() {
        // Given
        String clienteId = "CUST999";
        Integer fondoId = 1;

        when(clienteService.findByClienteId(clienteId)).thenReturn(Optional.empty());

        // When & Then
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            suscripcionService.suscribir(clienteId, fondoId, 100000.0);
        });

        assertEquals("Cliente no encontrado: CUST999", thrown.getMessage());
    }

    // ✅ Caso 2: Fondo no existe
    @Test
    void suscribir_DebeLanzarError_SiFondoNoExiste() {
        // Given
        String clienteId = "CUST123";
        Integer fondoId = 999;

        when(clienteService.findByClienteId(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(fondoService.findById(fondoId)).thenReturn(Optional.empty());

        // When & Then
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            suscripcionService.suscribir(clienteId, fondoId, 100000.0);
        });

        assertEquals("Fondo no disponible: " + fondoId, thrown.getMessage());
    }

    // ✅ Caso 3: Fondo inactivo
    @Test
    void suscribir_DebeLanzarError_SiFondoInactivo() {
        // Given
        String clienteId = "CUST123";
        Integer fondoId = 1;

        when(clienteService.findByClienteId(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(fondoService.findById(fondoId)).thenReturn(Optional.of(new Fondo() {{
            setActivo(false);
            setMontoMinimo(50000.0);
        }}));

        // When & Then
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            suscripcionService.suscribir(clienteId, fondoId, 100000.0);
        });

        assertEquals("Fondo no disponible: " + fondoId, thrown.getMessage());
    }

    // ✅ Caso 4: Monto insuficiente
    @Test
    void suscribir_DebeLanzarError_SiMontoInsuficiente() {
        // Given
        String clienteId = "CUST123";
        Integer fondoId = 1;

        when(clienteService.findByClienteId(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(fondoService.findById(fondoId)).thenReturn(Optional.of(new Fondo() {{
            setMontoMinimo(250000.0);
            setNombre("FDO-ACCIONES");
        }}));

        // When & Then
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            suscripcionService.suscribir(clienteId, fondoId, 100000.0);
        });

        assertTrue(thrown.getMessage().contains("Monto insuficiente"));
    }

    // ✅ Caso 5: Ya está suscrito (duplicado)
    @Test
    void suscribir_DebeLanzarError_SiYaEstaSuscrito() {
        // Given
        String clienteId = "CUST123";
        Integer fondoId = 1;

        when(clienteService.findByClienteId(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(fondoService.findById(fondoId)).thenReturn(Optional.of(new Fondo() {{
            setMontoMinimo(50000.0);
            setNombre("DEUDAPRIVADA");
            setActivo(true);
        }}));
        when(suscripcionRepository.findByClienteIdAndFondoIdAndEstado(
                eq(clienteId), eq(fondoId), eq("ACTIVO")))
                .thenReturn(Optional.of(new Suscripcion()));

        // When & Then
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            suscripcionService.suscribir(clienteId, fondoId, 100000.0);
        });

        assertEquals("Ya está suscrito a este fondo.", thrown.getMessage());
    }

    // ✅ Caso 6: Todo correcto
    @Test
    void suscribir_DebeCrearSuscripcionYTransaccion() {
        // Given
        String clienteId = "CUST123";
        Integer fondoId = 1;

        when(clienteService.findByClienteId(clienteId)).thenReturn(Optional.of(new Cliente()));
        when(fondoService.findById(fondoId)).thenReturn(Optional.of(new Fondo() {{
            setMontoMinimo(50000.0);
            setNombre("DEUDAPRIVADA");
            setFondoId(fondoId);
        }}));
        when(suscripcionRepository.findByClienteIdAndFondoIdAndEstado(
                anyString(), anyInt(), anyString())).thenReturn(Optional.empty());

        // When
        String resultado = suscripcionService.suscribir(clienteId, fondoId, 100000.0);

        // Then
        assertEquals("Suscripción exitosa al fondo: DEUDAPRIVADA", resultado);
        verify(suscripcionRepository, times(1)).save(any(Suscripcion.class));
        verify(transaccionService, times(1)).guardar(any());
        verify(notificacionService, times(1)).enviarNotificacion(anyString(), any(), anyString());
    }
}