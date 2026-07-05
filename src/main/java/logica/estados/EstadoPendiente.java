package logica.estados;

import logica.modelos.Reserva;

/**
 * Representa el estado inicial de una reserva cuando ha sido solicitada
 * pero aún no ha sido respondida por el tutor.
 *
 * @author Antonia-FSR
 */
public class EstadoPendiente implements EstadoReserva {
    /**
     * Confirma la reserva y transita hacia el estado EstadoConfirmada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void confirmar(Reserva reserva) {
        System.out.println("La reserva ha sido confirmada por el tutor.");
        reserva.setEstadoActual(new EstadoConfirmada());    }
    /**
     * Cancela la reserva pendiente y transita hacia el estado EstadoCancelada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void cancelar(Reserva reserva) {
        System.out.println("La reserva pendiente ha sido cancelada.");
        reserva.setEstadoActual(new EstadoCancelada());
    }
    /**
     * Intenta finalizar la reserva. Produce un error porque una reserva
     * pendiente no puede finalizarse sin antes ser confirmada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void finalizar(Reserva reserva) {
        System.out.println("Error: No puedes finalizar una reserva que aún está pendiente.");
    }
}
