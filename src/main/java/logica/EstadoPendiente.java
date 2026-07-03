package logica;

public class EstadoPendiente implements EstadoReserva {
    @Override
    public void confirmar(Reserva reserva) {
        System.out.println("La reserva ha sido confirmada por el tutor.");
        // Más adelante conectaremos esto con EstadoConfirmada
    }

    @Override
    public void cancelar(Reserva reserva) {
        System.out.println("La reserva pendiente ha sido cancelada.");
        // Más adelante conectaremos esto con EstadoCancelada
    }

    @Override
    public void finalizar(Reserva reserva) {
        System.out.println("Error: No puedes finalizar una reserva que aún está pendiente.");
    }
}
