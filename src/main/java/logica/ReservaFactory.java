package logica;

import logica.modelos.*;

/**
 * Fábrica encargada de instanciar el tipo correcto de Reserva aplicando el patrón Factory Method.
 */
public class ReservaFactory {

    /**
     * Crea y retorna una instancia específica de Reserva (Virtual o Presencial).
     *
     * @param tipo         El tipo de reserva ("VIRTUAL" o "PRESENCIAL").
     * @param estudiante   El estudiante asociado.
     * @param tutor        El tutor asignado.
     * @param materia      La materia.
     * @param horario      La fecha y bloque horario.
     * @return Una instancia de ReservaVirtual o ReservaPresencial.
     */
    public static Reserva crearReserva(String tipo, Estudiante estudiante, Tutor tutor, String materia, String fecha, String horario, String detalleExtra) {

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de reserva no puede estar vacío.");
        }

        switch (tipo.toUpperCase()) {
            case "VIRTUAL":
                return new ReservaVirtual(estudiante, tutor, materia, fecha, horario, detalleExtra);

            case "PRESENCIAL":
                return new ReservaPresencial(estudiante, tutor, materia, fecha, horario, detalleExtra);

            default:
                throw new IllegalArgumentException("Tipo de reserva no válido en el sistema: " + tipo);
        }
    }
}