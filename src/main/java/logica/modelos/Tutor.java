package logica.modelos;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Representa a un tutor dentro del sistema de reservas.
 * Gestiona el perfil del profesional, incluyendo sus tarifas,
 * las materias que imparte con sus respectivos cupos máximos,
 * y sus bloques de disponibilidad horaria.
 *
 * @author Antonia-FSR
 */
public class Tutor implements java.io.Serializable {
    /** Identificador único del tutor (RUT). */
    private String rut;

    /** Nombre completo del tutor. */
    private String nombre;

    /** Correo electrónico de contacto profesional. */
    private String correo;

    /** tarifa que cobra el tutor por una hora de clase. */
    private double tarifaHora;

    /**
     * Diccionario que asocia el nombre de una materia (Clave)
     * con la cantidad máxima de estudiantes permitidos (Valor).
     */
    private Map<String, Integer> materias;

    /**
     * Colección de bloques horarios en los que el tutor está disponible.
     * Se usa un Set para evitar ingresar el mismo horario dos veces.
     * Ejemplo de formato: "LUN-10:00", "MAR-15:30".
     */
    private Set<String> horariosDisponibles;

    /**
     * Constructor principal de la clase Tutor.
     * Inicializa las colecciones vacías listas para ser pobladas.
     *
     * @param nombre     El nombre completo del profesional.
     * @param correo     El correo electrónico de contacto.
     * @param tarifaHora La tarifa base por hora de clase.
     */
    public Tutor(String rut, String nombre, String correo, double tarifaHora) {
        this.rut = rut;
        this.nombre = nombre;
        this.correo = correo;
        this.tarifaHora = tarifaHora;
        this.materias = new HashMap<>();
        this.horariosDisponibles = new HashSet<>();

    }

    /**
     * Registra una nueva materia que el tutor está capacitado para impartir,
     * estableciendo un límite de alumnos para dicha clase.
     *
     * @param nombreMateria  El nombre de la asignatura (ej. "Programación en Java").
     * @param maxEstudiantes El cupo máximo de alumnos permitidos.
     */
    public void agregarMateria(String nombreMateria, int maxEstudiantes) {
        this.materias.put(nombreMateria, maxEstudiantes);
    }

    /**
     * Añade un bloque de tiempo a la disponibilidad del tutor.
     *
     * @param bloqueHorario El bloque horario a liberar (ej. "LUN-10:00").
     */
    public void agregarDisponibilidad(String bloqueHorario) {
        this.horariosDisponibles.add(bloqueHorario);
    }

    /**
     * Elimina un bloque de tiempo de la disponibilidad del tutor
     * (útil cuando se concreta una reserva).
     *
     * @param bloqueHorario El bloque horario que se va a ocupar.
     */
    public void removerDisponibilidad(String bloqueHorario) {
        this.horariosDisponibles.remove(bloqueHorario);
    }

    /**
     * Verifica si el tutor tiene disponibilidad en el horario solicitado.
     *
     * @param bloqueHorario El horario que se desea consultar.
     * @return true si el tutor está disponible en ese bloque, false en caso contrario.
     */
    public boolean tieneDisponibilidad(String bloqueHorario) {
        return this.horariosDisponibles.contains(bloqueHorario);
    }

    /**
     * Verifica si el tutor imparte una materia específica y si el grupo
     * solicitado no excede el límite de estudiantes del tutor.
     *
     * @param nombreMateria      La materia a consultar.
     * @param cantidadEstudiantes La cantidad de alumnos que asistirán.
     * @return true si imparte la materia y hay cupo suficiente, false en caso contrario.
     */
    public boolean puedeImpartir(String nombreMateria, int cantidadEstudiantes) {
        if (!materias.containsKey(nombreMateria)) {
            return false;
        }
        int cupoMaximo = materias.get(nombreMateria);
        return cantidadEstudiantes <= cupoMaximo;
    }

    /**
     * Obtiene el nombre del tutor.
     *
     * @return Una cadena con el nombre completo.
     */
    public String getNombre() { return nombre; }

    /**
     * Modifica el nombre del tutor.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el correo del tutor.
     *
     * @return Una cadena con el correo.
     */
    public String getCorreo() { return correo; }

    /**
     * Modifica el correo electrónico del tutor.
     *
     * @param correo El nuevo correo a asignar.
     */
    public void setCorreo(String correo) { this.correo = correo; }

    /**
     * Obtiene la tarifa que cobra el tutor.
     *
     * @return El valor numérico de la tarifa.
     */
    public double getTarifaHora() { return tarifaHora; }

    /**
     * Modifica la tarifa por hora del tutor.
     *
     * @param tarifaHora La nueva tarifa a establecer.
     */
    public void setTarifaHora(double tarifaHora) { this.tarifaHora = tarifaHora; }

    /**
     * Obtiene el diccionario de materias impartidas por el tutor.
     *
     * @return Un mapa donde la clave es la materia y el valor es el cupo máximo.
     */
    public Map<String, Integer> getMaterias() { return materias; }

    /**
     * Obtiene los bloques horarios en los que el tutor está disponible.
     *
     * @return Un conjunto (Set) con los horarios.
     */
    public Set<String> getHorariosDisponibles() { return horariosDisponibles; }

    /**
     * Obtiene el RUT del tutor.
     *
     * @return Una cadena con el RUT.
     */
    public String getRUT() {
        return rut;
    }

    /**
     * Modifica el RUT del tutor.
     *
     * @param rut El nuevo identificador a asignar.
     */
    public void setRut(String rut) {this.rut = rut;}

    /**
     * Retorna una representación en texto del tutor.
     * @return Una cadena combinando el nombre y la tarifa del tutor.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Tarifa: $" + tarifaHora + "/hr";
    }
}
