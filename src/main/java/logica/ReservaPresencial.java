package logica;

/**
 * Representa una tutoría que se realizará físicamente en las instalaciones.
 */
public class ReservaPresencial extends Reserva {

    private String sala;

    public ReservaPresencial(String idReserva, Estudiante estudiante, Tutor tutor, String materia, String horario, String salaUbicacion) {

        super(idReserva, estudiante, tutor, materia, horario);
        this.sala= salaUbicacion;
    }

    @Override
    public String getTipo() {
        return "Presencial";
    }

    public String getSala() {
        return sala;
    }

    public void setSalaUbicacion(String salaUbicacion) {
        this.sala= salaUbicacion;
    }
}