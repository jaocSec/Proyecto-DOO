package logica;

import logica.observer.*;
import logica.modelos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControladorTest{

    private Controlador gestor;

    @BeforeEach
    public void setUp() {
        gestor = new Controlador();
    }

    @Test
    public void testListasInicializanVacias() {

        assertTrue(gestor.getReservas().isEmpty(), "Error: La lista de reservas debe iniciar vacía");
        assertTrue(gestor.getEstudiantes().isEmpty(), "Error: La lista de estudiantes debe iniciar vacía");
        assertTrue(gestor.getTutores().isEmpty(), "Error: La lista de tutores debe iniciar vacía");
    }

    @Test
    public void testRegistrarReservaAlmacenaEnLista() {
        gestor.registrarReserva("VIRTUAL", "R001", null, null, "Química", "10:00", "Google Meet");

        assertEquals(1, gestor.getReservas().size(), "Error: Debe haber exactamente 1 reserva en la lista");
        assertTrue(gestor.getReservas().get(0) instanceof ReservaVirtual, "Error: La reserva guardada debe ser Virtual");
    }

    @Test
    public void testNotificacionObservadores() {
        final boolean[] fueNotificado = {false};

        InterfazObserver observador= new InterfazObserver() {
            @Override
            public void actualizar() {
                fueNotificado[0] = true;
            }
        };

        gestor.agregarObservador(observador);

        gestor.registrarReserva("PRESENCIAL", "R002", null, null, "Física", "12:00", "Sala 1");
        assertTrue(fueNotificado[0], "Error: El observador debió ser notificado al registrar la reserva");
    }
}