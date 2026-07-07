package logica.modelos;



/**
 * Representa una reserva que se realizará virtualmente.
 *
 * Implementa {@link java.io.Serializable} para garantizar persistencia en el sistema de almacenamiento.
 */
public class ReservaVirtual extends Reserva implements java.io.Serializable{
    private String linkReunion;
    private String plataforma;

    /**
     * Construye e inicializa una nueva reserva presencial.
     * Delega la inicialización de los atributos base a la clase padre y asigna la ubicación específica para esta tutoría.
     *
     * @param estudiante    El objeto {@link Estudiante} que solicita la tutoría.
     * @param tutor         El objeto {@link Tutor} asignado a la tutoria.
     * @param materia       El nombre de la asignatura requerida.
     * @param fecha         La fecha de la tutoría en formato "dd-MM-yyyy".
     * @param horario       El bloque horario en el que se hará la tutoría.
     * @param plataforma    La plataforma donde se realizará la sesión.
     */
    public ReservaVirtual(Estudiante estudiante, Tutor tutor, String materia, String fecha, String horario, String plataforma){
        super(estudiante, tutor, materia, fecha, horario);

        this.plataforma= plataforma;
    }

    /**
     * Obtiene la modalidad de esta reserva.
     *
     * @return Una cadena de texto indicando el tipo "Virtual".
     */
    @Override
    public String getTipo(){
        return "Virtual";
    }

    /**
     * Obtiene el enlace asignado para la tutoría.
     *
     * @return Una cadena de texto con el link.
     */
    public String getLinkReunion() {
        return linkReunion;
    }

    /**
     * Obtiene la plataforma virtual mediante la cual se hará la tutoria.
     *
     * @return Una cadena de texto con el nombre de la plataforma.
     */
    public String getPlataforma() {
        return plataforma;
    }

    /**
     * Modifica o establece el enlace de la videollamada para la tutoría.
     *
     * @param link El nuevo link.
     */
    public void setLinkReunion(String link){
         this.linkReunion= link;
    }
}


