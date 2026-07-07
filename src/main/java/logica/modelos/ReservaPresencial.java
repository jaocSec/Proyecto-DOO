package logica.modelos;

/**
 * Representa una tutoría que se realizará físicamente en las instalaciones.
 */
public class ReservaPresencial extends Reserva  implements java.io.Serializable {

    private String sala;

    public ReservaPresencial(Estudiante estudiante, Tutor tutor, String materia, String fecha, String horario, String salaUbicacion) {

        super(estudiante, tutor, materia, fecha, horario);
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