package logica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * Pruebas unitarias para la clase Tutor.
 * Verifica la correcta lógica de asignación de materias, cupos y horarios.
 */
class TutorTest {
    @Test
    void testPuedeImpartirMateriaConCupoSuficiente() {
        //Preparación (Arrange)
        Tutor tutor = new Tutor("123", "Diego", "diego@mail.com", 15000);
        tutor.agregarMateria("Ingles", 5);

        //ejecución (Act)
        boolean resultado = tutor.puedeImpartir("Ingles", 3);

        //Verificación (Assert)
        assertTrue(resultado, "El tutor debería poder impartir la materia porque 3 es menor al cupo máximo de 5");
    }

    @Test
    void testNoPuedeImpartirMateriaSiExcedeCupo() {
        //Preparación
        Tutor tutor = new Tutor("123", "Antonia", "Antonia@mail.com", 15000);
        tutor.agregarMateria("Matematicas", 5);

        //Ejecución
        boolean resultado = tutor.puedeImpartir("Matematicas", 6);

        //Verificación
        assertFalse(resultado, "El tutor NO debería poder impartir la clase porque 6 excede el cupo de 5");
    }

    @Test
    void testNoPuedeImpartirMateriaNoRegistrada() {
        //Preparación
        Tutor tutor = new Tutor("123", "Emilia", "emilia@mail.com", 15000);
        tutor.agregarMateria("Lenguaje", 5);

        //Ejecución
        boolean resultado = tutor.puedeImpartir("Fisica", 2);

        //Verificación
        assertFalse(resultado, "El tutor NO debería poder impartir Fisica porque no la tiene registrada");
    }

    @Test
    void testGestionDeDisponibilidadHoraria() {
        //Preparación
        Tutor tutor = new Tutor("123", "Bastian", "bastian@mail.com", 15000);
        String horario = "LUN-10:00";

        //Ejecución y Verificación de agregar
        tutor.agregarDisponibilidad(horario);
        assertTrue(tutor.tieneDisponibilidad(horario), "El tutor debería estar disponible los Lunes a las 10:00");

        //Ejecución y Verificación de remover
        tutor.removerDisponibilidad(horario);
        assertFalse(tutor.tieneDisponibilidad(horario), "El tutor ya no debería estar disponible tras remover el horario");
    }

}