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
     * @param idReserva    El identificador único.
     * @param estudiante   El estudiante asociado.
     * @param tutor        El tutor asignado.
     * @param materia      La materia.
     * @param horario      La fecha y bloque horario.
     * @param detalleExtra 'plataforma' para virtuales o 'sala' para presenciales.
     * @return Una instancia de ReservaVirtual o ReservaPresencial.
     */
    public static Reserva crearReserva(String tipo, String idReserva, Estudiante estudiante, Tutor tutor, String materia, String horario, String detalleExtra) {

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de reserva no puede estar vacío.");
        }

        switch (tipo.toUpperCase()) {
            case "VIRTUAL":
                return new ReservaVirtual(idReserva, estudiante, tutor, materia, horario, detalleExtra);

            case "PRESENCIAL":
                return new ReservaPresencial(idReserva, estudiante, tutor, materia, horario, detalleExtra);

            default:
                throw new IllegalArgumentException("Tipo de reserva no válido en el sistema: " + tipo);
        }
    }
}