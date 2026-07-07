package logica;

import logica.modelos.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaFactoryTest {

    @Test
    public void testCrearReservaVirtual() {
        Reserva reserva = ReservaFactory.crearReserva(
                "VIRTUAL", null, null, "Programación", "10-10-2026", "10:00", "Zoom"
        );

        assertTrue(reserva instanceof ReservaVirtual, "Error: La fábrica no retornó una ReservaVirtual");
        assertEquals("Virtual", reserva.getTipo(), "Error: El tipo de detalle no coincide");

        ReservaVirtual rv = (ReservaVirtual) reserva;
        assertEquals("Zoom", rv.getPlataforma(), "Error: La plataforma no se asignó correctamente");
    }

    @Test
    public void testCrearReservaPresencial() {
        Reserva reserva = ReservaFactory.crearReserva(
                "PRESENCIAL", null, null, "Física", "10-10-2026", "11:30", "Sala 302"
        );

        assertTrue(reserva instanceof ReservaPresencial, "Error: La fábrica no retornó una ReservaPresencial");
        assertEquals("Presencial", reserva.getTipo(), "Error: El tipo de detalle no coincide");

        ReservaPresencial rp = (ReservaPresencial) reserva;
        assertEquals("Sala 302", rp.getSala(), "Error: La sala no se asignó correctamente");
    }

    @Test
    public void testCrearReservaConTipoInvalido() {
        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ReservaFactory.crearReserva(
                    "HIBRIDA", null, null, "Química", "10-10-2026", "14:00", "Laboratorio"
            );
        });

        // Este assert dependerá de cómo escribiste el mensaje de error en tu Factory
        assertTrue(excepcion.getMessage().contains("Tipo de reserva no válido") || excepcion.getMessage().toLowerCase().contains("tipo"),
                "Error: El mensaje de la excepción no es el esperado");
    }

    @Test
    public void testCrearReservaConTipoVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            ReservaFactory.crearReserva(
                    "", null, null, "Cálculo", "10-10-2026", "15:00", "Sala 1"
            );
        }, "Error: La fábrica debe rechazar un string vacío");

        assertThrows(IllegalArgumentException.class, () -> {
            ReservaFactory.crearReserva(
                    null, null, null, "Cálculo", "10-10-2026", "16:00", "Sala 2"
            );
        }, "Error: La fábrica debe rechazar un valor nulo");
    }
}