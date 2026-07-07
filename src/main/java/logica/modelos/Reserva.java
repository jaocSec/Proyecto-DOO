package logica.modelos;

import logica.estados.EstadoPendiente;
import logica.estados.EstadoReserva;

/**
 * Representa una reserva de tutoría entre un estudiante y un tutor.
 * Esta clase gestiona la información de la cita y maneja su estado
 * interno utilizando el Patrón de Diseño State.
 * @author Antonia-FSR
 */
public abstract class Reserva implements java.io.Serializable {
    protected String idReserva;
    protected Estudiante estudiante;
    protected Tutor tutor;
    protected String materia;
    protected String fecha;
    protected String horario;
    protected EstadoReserva estadoActual;
    /**
     * Constructor para inicializar una nueva Reserva.
     * Al crearse, la reserva asume por defecto el estado inicial "Pendiente".
     *
     * @param estudiante El estudiante que solicita la tutoría.
     * @param tutor      El tutor asignado para impartir la clase.
     * @param materia    El nombre de la materia a estudiar.
     * @param horario    La fecha y hora acordada para la sesión.
     */
    public Reserva(Estudiante estudiante, Tutor tutor, String materia, String fecha, String horario) {
        this.estudiante = estudiante;
        this.tutor = tutor;
        this.materia = materia;
        this.fecha = fecha;
        this.horario = horario;
        this.estadoActual = new EstadoPendiente();
    }

    /**
     * Método para definir que tipo de clase es (virtual o presencial)
     * @return un string indicando que tipo de reserva es (virtual, presencial)
     */
    public abstract String getTipo();


    /**
     * Solicita confirmar la reserva.
     * El comportamiento exacto de esta acción será delegado al estado actual.
     */
    public void confirmar() {
        if (estadoActual != null) {
            estadoActual.confirmar(this);
        }
    }
    /**
     * Solicita cancelar la reserva.
     * El comportamiento exacto de esta acción será delegado al estado actual.
     */
    public void cancelar() {
        if (estadoActual != null) {
            estadoActual.cancelar(this);
        }
    }
    /**
     * Solicita marcar la reserva como finalizada.
     * El comportamiento exacto de esta acción será delegado al estado actual.
     */
    public void finalizar() {
        if (estadoActual != null) {
            estadoActual.finalizar(this);
        }
    }

    /**
     * Actualiza el estado interno de la reserva (Transición de estado).
     *
     * @param nuevoEstado El nuevo estado que adoptará la reserva.
     */
    public void setEstadoActual(EstadoReserva nuevoEstado) {

        this.estadoActual = nuevoEstado;
    }
    /**
     * Obtiene el estado en el que se encuentra la reserva actualmente.
     *
     * @return El objeto EstadoReserva actual.
     */
    public EstadoReserva getEstadoActual() {
        return estadoActual;
    }


    //GETTERS

    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * Obtiene el estudiante asociado a la tutoría.
     *
     * @return El objeto {@link Estudiante} que solicitó la clase.
     */
    public Tutor getTutor() {
        return tutor;
    }

    /**
     * Obtiene el tutor asignado para impartir la tutoría.
     *
     * @return El objeto {@link Tutor} que dictará la clase.
     */
    public String getMateria() {
        return materia;
    }

    /**
     * Obtiene la fecha en la que está programada la tutoría.
     *
     * @return Una cadena de texto con la fecha ("dd-MM-yyyy").
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Obtiene el bloque horario en el que está programada la tutoría.
     *
     * @return Una cadena de texto con el horario.
     */
    public String getHorario() {
        return horario;
    }

    /**
     * Obtiene una representación amigable del estado actual de la reserva formateando el nombre de la clase de estado.
     * @return Una cadena de texto con el estado.
     */
    public String getEstado() {
        return estadoActual
                .getClass()
                .getSimpleName()
                .replace("Estado", "");
    }
}
