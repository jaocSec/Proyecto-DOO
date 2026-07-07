package logica;

import logica.modelos.Estudiante;
import logica.modelos.Tutor;
import logica.modelos.Reserva;
import logica.observer.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase controladora que centraliza la lógica y la persistencia del sistema.
 * Actúa como intermediario entre la interfaz y los datos.
 *
 * * @author jaocSec
 */
public class Controlador extends Observable{
    private List<Estudiante> estudiantes;
    private List<Tutor> tutores;
    private List<Reserva> reservas;

    private Catalogos catalogo;

    /**
     * Constructor del controlador. Inicializa las colecciones de datos y realiza la carga persistente de información previa si existe.
     */
    public Controlador(){
        super();

        this.catalogo= new Catalogos();
        this.estudiantes= new ArrayList<>();
        this.tutores= new ArrayList<>();
        this.reservas= new ArrayList<>();
        cargarDatos();
    }

    /**
     * Fuerza una actualización de la interfaz gráfica para reflejar cambios.
     */
    public void refrescarUI() {
        notificarObservadores();
    }

    /**
     * Registra un nuevo tutor en el sistema.
     * @param tutor Tutor a registrar.
     */
    public void registrarTutor(Tutor tutor) {
        if (tutor != null) {
            tutores.add(tutor);
            notificarObservadores();
        }
    }

    /**
     * Registra un nuevo estudiante en el sistema.
     * @param estudiante Estudiante a registrar.
     */
    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.add(estudiante);
            notificarObservadores();
        }
    }

    /**
     * Elimina un tutor del sistema.
     * @param tutor Tutor a eliminar.
     */
    public void eliminarTutor(Tutor tutor) {
        if (tutor != null) {
            tutores.remove(tutor);
            notificarObservadores();
        }
    }

    /**
     * Elimina un estudiante del sistema.
     * @param estudiante Estudiante a eliminar.
     */
    public void eliminarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            estudiantes.remove(estudiante);
            notificarObservadores();
        }
    }

    /**
     * Busca un tutor por su nombre.
     * @param nombre El nombre del tutor.
     * @return El Tutor encontrado o null si no existe.
     */
    public Tutor buscarTutorPorNombre(String nombre) {

        for (Tutor tutor : tutores) {
            if (tutor.getNombre().equals(nombre)) {
                return tutor;
            }
        }

        return null;
    }

    /**
     * Busca un estudiante por su nombre.
     * @param nombre El nombre del estudiante.
     * @return El Estudiante encontrado o null si no existe.
     */
    public Estudiante buscarEstudiantePorNombre(String nombre) {

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equals(nombre)) {
                return estudiante;
            }
        }

        return null;
    }

    /**
     * Registra una nueva reserva en el sistema.
     * @param nuevaReserva Objeto Reserva a añadir.
     */
    public void registrarReserva(Reserva nuevaReserva) {

        reservas.add(nuevaReserva);
        notificarObservadores();
    }

    /**
     * Verifica si un tutor tiene cupo disponible para aceptar una nueva reserva.
     * Cuenta las reservas activas (no canceladas ni finalizadas) asignadas al tutor y las compara con su límite máximo establecido.
     * @param tutor El tutor a evaluar.
     * @return true si tiene disponibilidad, false si alcanzó su cupo máximo.
     */
    public boolean tieneCupo(Tutor tutor, String materia) {
        if (!tutor.getMaterias().containsKey(materia)) return false;

        int limiteMateria = tutor.getMaterias().get(materia);
        int reservasActivasMateria = 0;

        for (Reserva r : reservas) {
            if (r.getTutor() != null && r.getTutor().getRUT().equals(tutor.getRUT())) {
                String estado = r.getEstadoActual().getClass().getSimpleName();

                if (!estado.equals("EstadoCancelada") && !estado.equals("EstadoFinalizada")) {
                    if (r.getMateria().equals(materia)) {
                        reservasActivasMateria++;
                    }
                }
            }
        }

        return reservasActivasMateria < limiteMateria;
    }

    /**
     * Filtra tutores según disponibilidad y materia.
     * @param materia Materia requerida.
     * @param horario Horario solicitado.
     * @return Lista de tutores compatibles.
     */
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

    /**
     * Evaluación para finalizar reservas automáticamente según la fecha y hora.
     */
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

    /**
     * Guarda el estado actual del sistema en un archivo binario (.dat).
     */
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

    /**
     * Carga el estado del sistema desde un archivo binario (.dat).
     */
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

            if (reserva.getEstadoActual()
                    .getClass()
                    .getSimpleName()
                    .equals("EstadoPendiente")) {

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
