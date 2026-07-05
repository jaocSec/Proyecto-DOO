package logica.estados;

import logica.modelos.Reserva;

/**
 * Interfaz para el Patrón State.
 * Define las acciones que puede recibir una reserva en cualquier estado.
 */
public interface EstadoReserva {
    void confirmar(Reserva reserva);
    void cancelar(Reserva reserva);
    void finalizar(Reserva reserva);
}
