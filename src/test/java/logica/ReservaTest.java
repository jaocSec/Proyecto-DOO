package logica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ReservaTest {
    private Reserva reserva;

    /**
     * Este metodo se ejecuta automáticamente ANTES de cada @Test.
     * Nos sirve para tener una reserva "fresca" y nueva en cada prueba.
     */
    @BeforeEach
    public void setUp() {
        // Pasamos null a Estudiante y Tutor para simplificar,
        // ya que aquí solo nos interesa probar la lógica de los estados.
        reserva = new Reserva("R001", null, null, "Matemáticas", "10:00 - 11:00");
    }

    @Test
    public void testEstadoInicialEsPendiente() {
        // Verificamos que al crear la reserva, nazca en estado Pendiente
        assertTrue(reserva.getEstadoActual() instanceof EstadoPendiente,
                "Error: El estado inicial debería ser EstadoPendiente");
    }

    @Test
    public void testConfirmarReserva() {
        //Acción
        reserva.confirmar();

        //Verificación
        assertTrue(reserva.getEstadoActual() instanceof EstadoConfirmada,
                "Error: La reserva debería pasar a EstadoConfirmada");
    }

    @Test
    public void testCancelarReservaPendiente() {
        // Acción
        reserva.cancelar();

        // Verificación
        assertTrue(reserva.getEstadoActual() instanceof EstadoCancelada,
                "Error: La reserva debería pasar a EstadoCancelada");
    }

    @Test
    public void testIntentoFinalizarReservaPendiente() {
        //Acción: Intentamos saltarnos las reglas y finalizar antes de tiempo
        reserva.finalizar();

        //Verificación: Como el EstadoPendiente no permite esto, la reserva DEBE seguir pendiente
        assertTrue(reserva.getEstadoActual() instanceof EstadoPendiente,
                "Error: El estado no debe cambiar si se intenta finalizar una reserva pendiente");
    }

    @Test
    public void testFlujoCompletoExitoso() {
        //Acción: Simulamos el ciclo de vida completo
        reserva.confirmar(); //Primero pasa a Confirmada
        reserva.finalizar(); //Luego pasa a Finalizada

        // Verificación
        assertTrue(reserva.getEstadoActual() instanceof EstadoFinalizada,
                "Error: Al terminar el flujo, el estado debe ser EstadoFinalizada");
    }
}
