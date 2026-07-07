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
        Reserva nuevaReserva = ReservaFactory.crearReserva("VIRTUAL", null, null, "Química", "10-10-2026", "10:00", "Google Meet");
        gestor.registrarReserva(nuevaReserva);

        assertEquals(1, gestor.getReservas().size(), "Error: Debe haber exactamente 1 reserva en la lista");
        assertTrue(gestor.getReservas().get(0) instanceof ReservaVirtual, "Error: La reserva guardada debe ser Virtual");
    }

    @Test
    public void testNotificacionObservadores() {
        final boolean[] fueNotificado = {false};

        InterfazObserver observador = new InterfazObserver() {
            @Override
            public void actualizar() {
                fueNotificado[0] = true;
            }
        };

        gestor.agregarObservador(observador);

        Reserva nuevaReserva = ReservaFactory.crearReserva("PRESENCIAL", null, null, "Física", "10-10-2026", "12:00", "Sala 1");
        gestor.registrarReserva(nuevaReserva);

        assertTrue(fueNotificado[0], "Error: El observador debió ser notificado al registrar la reserva");
    }

    @Test
    public void testBuscarTutorYEstudianteNoExistenteRetornaNull() {
        assertNull(gestor.buscarTutorPorNombre("Tutor Fantasma"),
                "Error: Buscar un tutor inexistente debe retornar null");

        assertNull(gestor.buscarEstudiantePorNombre("Estudiante Fantasma"),
                "Error: Buscar un estudiante inexistente debe retornar null");
    }

    @Test
    public void testTieneCupoIgnoraReservasCanceladas() {
        Tutor tutor = new Tutor("111-1", "Profe Física", "profe@mail.com", 15000);
        tutor.agregarMateria("Física", 1);
        gestor.registrarTutor(tutor);

        Reserva r1 = ReservaFactory.crearReserva("VIRTUAL", null, tutor, "Física", "10-10-2026", "10:00", "Zoom");
        gestor.registrarReserva(r1);

        assertFalse(gestor.tieneCupo(tutor, "Física"),
                "Error: El tutor no debería tener cupo con una reserva activa");

        r1.cancelar();

        assertTrue(gestor.tieneCupo(tutor, "Física"),
                "Error: El tutor debería volver a tener cupo tras cancelar la reserva");
    }
}