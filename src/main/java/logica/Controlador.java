package logica;

import logica.modelos.Estudiante;
import logica.modelos.Tutor;
import logica.modelos.Reserva;
import logica.observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class Controlador extends Observable{
    private List<Estudiante> estudiantes;
    private List<Tutor> tutores;
    private List<Reserva> reservas;

    private Catalogos catalogo;

    public Controlador(){
        super();

        this.catalogo= new Catalogos();
        this.estudiantes= new ArrayList<>();
        this.tutores= new ArrayList<>();
        this.reservas= new ArrayList<>();
    }

    public void confirmarReserva(Reserva reserva){

        reserva.confirmar();

        notificarObservadores();
    }

    public void refrescarUI() {
        notificarObservadores();
    }

    public void registrarTutor(Tutor tutor) {
        if (tutor != null) {
            tutores.add(tutor);
            notificarObservadores();
        }
    }

    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.add(estudiante);
            notificarObservadores();
        }
    }

    public void eliminarTutor(Tutor tutor) {
        if (tutor != null) {
            tutores.remove(tutor);
            notificarObservadores();
        }
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.remove(estudiante);
            notificarObservadores();
        }
    }

    public Tutor buscarTutorPorNombre(String nombre) {

        for (Tutor tutor : tutores) {
            if (tutor.getNombre().equals(nombre)) {
                return tutor;
            }
        }

        return null;
    }

    public Estudiante buscarEstudiantePorNombre(String nombre) {

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equals(nombre)) {
                return estudiante;
            }
        }

        return null;
    }

    public void registrarReserva(Reserva nuevaReserva) {

        reservas.add(nuevaReserva);
        notificarObservadores();
    }

    public java.util.List<Tutor> buscarTutoresCompatibles(String materia, String horario) {
        java.util.List<Tutor> compatibles = new java.util.ArrayList<>();

        for (Tutor tutor : getTutores()) {
            boolean enseñaMateria = tutor.getMaterias().containsKey(materia);
            boolean horarioLibre = tutor.getHorariosDisponibles().contains(horario);

            if (enseñaMateria && horarioLibre) {
                compatibles.add(tutor);
            }
        }
        return compatibles;
    }

    //GETTERS
    public List<Reserva> getReservas() {
        return reservas;
    }

    public int getCantidadReservasPendientes() {

        int cantidad = 0;

        for (Reserva reserva : reservas) {

            if (!reserva.getEstadoActual()
                    .getClass()
                    .getSimpleName()
                    .equals("EstadoCancelada")) {

                cantidad++;
            }

        }

        return cantidad;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Tutor> getTutores() {
        return tutores;
    }

    public Catalogos getCatalogo() {return catalogo;}
}
