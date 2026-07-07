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
        cargarDatos();
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

    private void EstadoAutomatico(){
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (Reserva reserva : reservas) {
            if (reserva.getEstadoActual().getClass().getSimpleName().equals("EstadoConfirmada")) {
                try {
                    String horaExtraida = reserva.getHorario().contains("-")
                            ? reserva.getHorario().split("-")[1]
                            : reserva.getHorario();

                    String fechaHoraStr = reserva.getFecha() + " " + horaExtraida;
                    java.time.LocalDateTime fechaHoraReserva = java.time.LocalDateTime.parse(fechaHoraStr, formatter);

                    if (fechaHoraReserva.isBefore(ahora)) {
                        reserva.finalizar();
                    }
                } catch (Exception e) {
                    //nada
                }
            }
        }

    }

    public void guardarDatos() {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream("datos_sistema.dat"))) {
            oos.writeObject(this.estudiantes);
            oos.writeObject(this.tutores);
            oos.writeObject(this.reservas);
            oos.writeObject(this.catalogo);
            System.out.println("Datos guardados exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarDatos() {
        try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream("datos_sistema.dat"))) {
            this.estudiantes = (java.util.List<Estudiante>) ois.readObject();
            this.tutores = (java.util.List<Tutor>) ois.readObject();
            this.reservas = (java.util.List<Reserva>) ois.readObject();
            this.catalogo = (Catalogos) ois.readObject();
            System.out.println("Datos cargados exitosamente");
        } catch (Exception e) {
            System.out.println("Archivo de datos no existente");
        }
    }

    //GETTERS
    public java.util.List<Reserva> getReservas() {
        EstadoAutomatico();
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
