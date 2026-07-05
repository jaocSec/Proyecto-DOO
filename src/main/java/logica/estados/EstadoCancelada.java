package logica.estados;

import logica.modelos.Reserva;

/**
 * Representa un estado terminal de la reserva, indicando que fue anulada.
 * Este estado no permite transiciones adicionales.
 *
 * @author Antonia-FSR
 */
public class EstadoCancelada implements EstadoReserva {
    /**
     * Intenta confirmar la reserva. Produce un error porque una reserva
     * cancelada no puede ser reactivada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void confirmar(Reserva reserva) {
        System.out.println("Error: No se puede confirmar una reserva que ya fue cancelada.");
    }
    /**
     * Intenta cancelar una reserva que ya se encuentra en estado cancelado.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void cancelar(Reserva reserva) {
        System.out.println("La reserva ya se encuentra cancelada.");
    }
    /**
     * Intenta finalizar la reserva. Produce un error ya que la tutoría
     * nunca se llevó a cabo.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void finalizar(Reserva reserva) {
        System.out.println("Error: No se puede finalizar una tutoría de una reserva cancelada.");
    }
}
