package logica.estados;

import logica.modelos.Reserva;

/**
 * Representa el estado de una reserva que ha sido aceptada por el tutor.
 * Desde aquí, la tutoría puede ser cancelada o finalizada con éxito.
 *
 * @author Antonia-FSR
 */
public class EstadoConfirmada implements EstadoReserva {
    /**
     * Intenta confirmar una reserva que ya está confirmada.
     * Notifica que la acción no es válida en este estado.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void confirmar(Reserva reserva) {
        System.out.println("La reserva ya se encuentra confirmada.");
    }
    /**
     * Cancela la reserva previamente confirmada y transita a EstadoCancelada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void cancelar(Reserva reserva) {
        System.out.println("El estudiante o tutor ha cancelado la reserva confirmada.");
        reserva.setEstadoActual(new EstadoCancelada());
    }
    /**
     * Finaliza la reserva de forma exitosa tras impartir la tutoría
     * y transita hacia el estado EstadoFinalizada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void finalizar(Reserva reserva) {
        System.out.println("La tutoría se ha realizado con éxito. Finalizando reserva...");
        reserva.setEstadoActual(new EstadoFinalizada());
    }
}
