package logica.modelos;

/**
 * Representa una reserva que se realizará físicamente.
 *
 * Implementa {@link java.io.Serializable} para garantizar su persistencia en el sistema de almacenamiento.
 */
public class ReservaPresencial extends Reserva  implements java.io.Serializable {

    private String sala;

    /**
     * Construye e inicializa una nueva reserva presencial.
     * Delega la inicialización de los atributos base a la clase padre y asigna la ubicación específica para esta tutoría.
     *
     * @param estudiante    El objeto {@link Estudiante} que solicita la tutoría.
     * @param tutor         El objeto {@link Tutor} asignado a la tutoria.
     * @param materia       El nombre de la asignatura requerida.
     * @param fecha         La fecha de la tutoría en formato "dd-MM-yyyy".
     * @param horario       El bloque horario en el que se hará la tutoría.
     * @param salaUbicacion El identificador o nombre de la sala física donde se realizará la clase.
     */
    public ReservaPresencial(Estudiante estudiante, Tutor tutor, String materia, String fecha, String horario, String salaUbicacion) {

        super(estudiante, tutor, materia, fecha, horario);
        this.sala= salaUbicacion;
    }

    /**
     * Obtiene la modalidad de esta reserva.
     *
     * @return Una cadena de texto indicando el tipo "Presencial".
     */
    @Override
    public String getTipo() {
        return "Presencial";
    }

    /**
     * Obtiene la sala asignada donde se hará la tutoría.
     *
     * @return Una cadena de texto con la identificación de la sala.
     */
    public String getSala() {
        return sala;
    }

    /**
     * Modifica o actualiza la sala asignada para la tutoría presencial.
     *
     * @param salaUbicacion La nueva identificación de la sala.
     */
    public void setSalaUbicacion(String salaUbicacion) {
        this.sala= salaUbicacion;
    }
}