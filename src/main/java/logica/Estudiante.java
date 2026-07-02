package logica;
/**
 * Representa a un estudiante dentro del sistema de reservas de tutorías.
 * Esta clase se encarga de almacenar y gestionar la información personal
 * y de contacto de los alumnos que solicitan clases.
 *
 * @author Antonia-FSR
 */
public class Estudiante {
    /** Identificador unico del estudiante (por ejemplo, RUT o matrícula). */
    private String id;

    /** Nombre completo del estudiante. */
    private String nombre;

    /** Correo electrónico de contacto. */
    private String correo;

    /** Número de teléfono de contacto. */
    private String telefono;

    /**
     * Constructor principal de la clase Estudiante.
     * Inicializa una nueva instancia con todos los datos personales requeridos.
     *
     * @param id       El identificador único del estudiante.
     * @param nombre   El nombre completo del estudiante.
     * @param correo   El correo electrónico de contacto.
     * @param telefono El número de teléfono de contacto.
     */
    public Estudiante(String id, String nombre, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    /**
     * Obtiene el identificador único del estudiante.
     *
     * @return El ID actual del estudiante en formato String.
     */
    public String getId() {
        return id;
    }

    /**
     * Modifica el identificador único del estudiante.
     *
     * @param id El nuevo identificador a asignar.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del estudiante.
     *
     * @return El nombre del estudiante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre completo del estudiante.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico de contacto del estudiante.
     *
     * @return El correo electrónico actual.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Modifica el correo electrónico de contacto del estudiante.
     *
     * @param correo El nuevo correo electrónico a asignar.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono del estudiante.
     *
     * @return El número de teléfono actual.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Modifica el número de teléfono de contacto del estudiante.
     *
     * @param telefono El nuevo número de teléfono a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en formato de texto de los datos del estudiante.
     * Útil para propósitos de depuración y registro en consola.
     *
     * @return Una cadena de texto con el formato: Estudiante [id] - Nombre: nombre | Correo: correo
     */
    @Override
    public String toString() {
        return "Estudiante [" + id + "] - Nombre: " + nombre + " | Correo: " + correo;
    }
}
