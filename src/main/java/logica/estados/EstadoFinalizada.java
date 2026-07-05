package logica.estados;

import logica.modelos.Reserva;

/**
 * Representa el estado terminal de éxito de una reserva, indicando
 * que la tutoría se realizó por completo. No permite transiciones.
 *
 * @author Antonia-FSR
 */
public class EstadoFinalizada implements EstadoReserva {
    /**
     * Intenta confirmar la reserva. Produce un error porque la tutoría
     * ya concluyó en el pasado.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void confirmar(Reserva reserva) {
        System.out.println("Error: La tutoría ya finalizó, no se puede volver a confirmar.");
    }
    /**
     * Intenta cancelar la reserva. Produce un error porque una tutoría
     * impartida no se puede deshacer.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void cancelar(Reserva reserva) {
        System.out.println("Error: No se puede cancelar una tutoría que ya se impartió y finalizó.");
    }
    /**
     * Intenta finalizar una reserva que ya está registrada como finalizada.
     *
     * @param reserva La reserva sobre la cual se aplica la acción.
     */
    @Override
    public void finalizar(Reserva reserva) {
        System.out.println("La reserva ya se encuentra registrada como finalizada.");
    }
}
