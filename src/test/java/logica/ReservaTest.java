package logica;
import logica.estados.EstadoCancelada;
import logica.estados.EstadoConfirmada;
import logica.estados.EstadoFinalizada;
import logica.estados.EstadoPendiente;
import logica.modelos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {
    private Reserva reserva;

    @BeforeEach
    public void setUp() {
        reserva = new Reserva(null, null, "Matemáticas", "10-10-2026", "10:00 - 11:00"){
            @Override
            public String getTipo(){
                return "Prueba Unitaria";
            }
        };
    }

    @Test
    public void testEstadoInicialEsPendiente() {
        assertTrue(reserva.getEstadoActual() instanceof EstadoPendiente,
                "Error: El estado inicial debería ser EstadoPendiente");
    }

    @Test
    public void testConfirmarReserva() {
        reserva.confirmar();

        assertTrue(reserva.getEstadoActual() instanceof EstadoConfirmada,
                "Error: La reserva debería pasar a EstadoConfirmada");
    }

    @Test
    public void testCancelarReservaPendiente() {
        reserva.cancelar();

        assertTrue(reserva.getEstadoActual() instanceof EstadoCancelada,
                "Error: La reserva debería pasar a EstadoCancelada");
    }

    @Test
    public void testIntentoFinalizarReservaPendiente() {
        reserva.finalizar();

        assertTrue(reserva.getEstadoActual() instanceof EstadoPendiente,
                "Error: El estado no debe cambiar si se intenta finalizar una reserva pendiente");
    }

    @Test
    public void testFlujoCompletoExitoso() {
        reserva.confirmar();
        reserva.finalizar();

        assertTrue(reserva.getEstadoActual() instanceof EstadoFinalizada,
                "Error: Al terminar el flujo, el estado debe ser EstadoFinalizada");
    }

    @Test
    public void testTransicionInvalidaDesdeFinalizada() {
        reserva.confirmar();
        reserva.finalizar();

        reserva.cancelar();

        assertTrue(reserva.getEstadoActual() instanceof EstadoFinalizada,
                "Error: Una reserva finalizada no puede pasar a cancelada");
    }

    @Test
    public void testTransicionInvalidaDesdeCancelada() {
        reserva.cancelar();

        reserva.confirmar();

        assertTrue(reserva.getEstadoActual() instanceof EstadoCancelada,
                "Error: Una reserva cancelada no puede volver a confirmarse");
    }
}