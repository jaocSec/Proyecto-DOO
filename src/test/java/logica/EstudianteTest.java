package logica;

import logica.modelos.Estudiante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstudianteTest {

    @Test
    public void testCreacionYGetters() {
        Estudiante estudiante = new Estudiante("12345678-9", "Juan Pérez", "juan@mail.com", "+56912345678");

        assertEquals("12345678-9", estudiante.getRUT(), "Error: El RUT no coincide");
        assertEquals("Juan Pérez", estudiante.getNombre(), "Error: El nombre no coincide");
        assertEquals("juan@mail.com", estudiante.getCorreo(), "Error: El correo no coincide");
        assertEquals("+56912345678", estudiante.getTelefono(), "Error: El teléfono no coincide");
    }

    @Test
    public void testSetters() {
        Estudiante estudiante = new Estudiante("0", "Test", "test@test.com", "000");

        estudiante.setNombre("María López");
        estudiante.setCorreo("maria@mail.com");
        estudiante.setTelefono("987654321");

        assertEquals("María López", estudiante.getNombre(), "Error: El setter de nombre falló");
        assertEquals("maria@mail.com", estudiante.getCorreo(), "Error: El setter de correo falló");
        assertEquals("987654321", estudiante.getTelefono(), "Error: El setter de teléfono falló");
    }
}