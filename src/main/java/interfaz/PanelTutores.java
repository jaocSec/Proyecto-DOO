package interfaz;

import logica.Controlador;
import logica.modelos.Tutor;
import logica.observer.InterfazObserver;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panel de la interfaz dedicado a la gestión de los tutores.
 * Permite visualizar una lista actualizada de los tutores registrados, ver sus detalles y ejecutar acciones de creación, edición, eliminación y gestión específica de sus horarios y materias.
 *
 * Implementa el patrón Observer a través de {@link InterfazObserver} para reaccionar a los cambios en los datos gestionados por el {@link Controlador}.
 */
public class PanelTutores extends JPanel implements InterfazObserver {
    private Controlador controlador;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaTutores;
    private JButton btnAgregar, btnEditar, btnEliminar;

    //Componentes panel derecho
    private JLabel lblAvatar;
    private JLabel lblNombre;
    private JTextArea txtInformacion;

    private void cargarTutores() {
        modeloLista.clear();

        for (Tutor tutor : controlador.getTutores()) {
            modeloLista.addElement(tutor.getNombre());
        }
    }

    /**
     * Construye el panel de gestión de tutores.
     * Configura la vista dividida con una lista a la izquierda y un panel de detalles a la derecha, el cual incluye botones para la gestión de materias y horarios. Registra este panel como observador del controlador.
     * @param controlador El gestor principal de la lógica.
     */
    public PanelTutores(Controlador controlador) {

        this.controlador = controlador;
        controlador.agregarObservador(this);
        setLayout(new BorderLayout());

        //Panel Lista (izquierdo)
        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        //Panel BotonesLista
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 0));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnAgregar.addActionListener(e -> agregarTutor());
        btnEditar.addActionListener(e -> editarTutor());
        btnEliminar.addActionListener(e -> eliminarTutor());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        //Lista de tutores
        modeloLista = new DefaultListModel<>(); //Lista con nombres

        for (Tutor tutor : controlador.getTutores()) {
            modeloLista.addElement(tutor.getNombre());
        }

        listaTutores = new JList<>(modeloLista); //Lista gráfica
        listaTutores.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollLista = new JScrollPane(listaTutores);

        panelIzquierdo.add(panelBotones, BorderLayout.NORTH);
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER);


        //Panel detalles (derecho)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nombre
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        lblNombre = new JLabel("Seleccione un tutor");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));

        panelHeader.add(lblNombre);

        //Información panel derecho
        txtInformacion = new JTextArea("Seleccione un tutor de la lista para ver sus detalles.");
        txtInformacion.setEditable(false);
        txtInformacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInformacion.setOpaque(false);
        txtInformacion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelDerecho.add(panelHeader, BorderLayout.NORTH);
        panelDerecho.add(txtInformacion, BorderLayout.CENTER);


        JPanel panelAccionesTutor = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGestionarMaterias = new JButton("Gestionar Materias");
        JButton btnGestionarHorarios = new JButton("Gestionar Horarios");

        //deshabilitado si no se selecciona tutor
        btnGestionarMaterias.setEnabled(false);
        btnGestionarHorarios.setEnabled(false);

        panelAccionesTutor.add(btnGestionarMaterias);
        panelAccionesTutor.add(btnGestionarHorarios);

        btnGestionarMaterias.addActionListener(e -> abrirGestionMaterias());
        btnGestionarHorarios.addActionListener(e -> abrirGestionHorarios());

        panelDerecho.add(panelAccionesTutor, BorderLayout.SOUTH);



        //Listener click de la lista
        listaTutores.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String seleccionado = listaTutores.getSelectedValue();
                    if (seleccionado != null) {
                        actualizarDetalles(seleccionado);

                        btnGestionarMaterias.setEnabled(true);
                        btnGestionarHorarios.setEnabled(true);
                    }
                }
            }
        });



        //Divisor
        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        divisor.setDividerLocation(450);
        divisor.setDividerSize(5);
        divisor.setBorder(null);

        add(divisor, BorderLayout.CENTER);

        cargarTutores();
    }

    private void eliminarTutor() {
        String seleccionado = listaTutores.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor");
            return;
        }

        Tutor tutor = controlador.buscarTutorPorNombre(seleccionado);
        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Tutor no encontrado");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este tutor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) return;

        controlador.eliminarTutor(tutor);

        JOptionPane.showMessageDialog(this, "Tutor eliminado correctamente");
    }

    private void editarTutor() {

        String seleccionado = listaTutores.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor");
            return;
        }

        Tutor tutor = controlador.buscarTutorPorNombre(seleccionado);
        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Tutor no encontrado");
            return;
        }

        FormularioTutor form = new FormularioTutor(SwingUtilities.getWindowAncestor(this), this.controlador, tutor);
        form.setVisible(true);

        if (form.isGuardado()) {
            tutor.setNombre(form.getNombre());
            tutor.setCorreo(form.getCorreo());
            tutor.setTarifaHora(form.getTarifa());
            tutor.setRut(form.getRUT());

            controlador.refrescarUI();
            JOptionPane.showMessageDialog(this, "Tutor actualizado correctamente");
        }
    }

    private void agregarTutor() {
        FormularioTutor form = new FormularioTutor(SwingUtilities.getWindowAncestor(this), this.controlador);
        form.setVisible(true);

        if (form.isGuardado()) {

            Tutor nuevoTutor = new Tutor(form.getRUT(), form.getNombre(), form.getCorreo(), form.getTarifa());

            nuevoTutor.agregarMateria(form.getMateria(), form.getCupo());
            nuevoTutor.agregarDisponibilidad(form.getHorario());

            controlador.registrarTutor(nuevoTutor);
            JOptionPane.showMessageDialog(this, "Tutor agregado correctamente");
        }
    }

    private void actualizarDetalles(String nombre) {
        Tutor tutor = controlador.buscarTutorPorNombre(nombre);

        if (tutor == null) return;

        lblNombre.setText(tutor.getNombre());

        String info =
                "RUT: " + tutor.getRUT() + "\n\n" +
                "Correo: " + tutor.getCorreo() + "\n\n" +
                "Tarifa por hora: $" + tutor.getTarifaHora() + "\n\n" +
                "Materias y cupos:\n";

        for (java.util.Map.Entry<String, Integer> entrada : tutor.getMaterias().entrySet()) {
            info = info + " - " + entrada.getKey() + " (Cupo máx: " + entrada.getValue() + ")\n";
        }

        info = info + "\nDisponibilidad:\n";

        for (String h : tutor.getHorariosDisponibles()) {
            info = info + " - " + h + "\n";
        }

        txtInformacion.setText(info);
    }

    private void abrirGestionMaterias() {
        String seleccionado = listaTutores.getSelectedValue();
        if (seleccionado == null) return;

        Tutor tutor = controlador.buscarTutorPorNombre(seleccionado);
        if (tutor == null) return;

        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        JDialog dialogo = new JDialog(ventanaPadre, "Gestionar Materias - " + tutor.getNombre(), Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 450);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelAgregar = new JPanel(new GridLayout(3, 2, 5, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Añadir Nueva Materia"));

        panelAgregar.add(new JLabel("Materia:"));
        JComboBox<String> cbMaterias = new JComboBox<>(controlador.getCatalogo().getMaterias().toArray(new String[0]));
        panelAgregar.add(cbMaterias);

        panelAgregar.add(new JLabel("Cupo máximo:"));
        JTextField txtCupoMateria = new JTextField("5");
        panelAgregar.add(txtCupoMateria);

        JButton btnAgregar = new JButton("Añadir / Actualizar");
        panelAgregar.add(new JLabel("")); // Espaciador
        panelAgregar.add(btnAgregar);

        JPanel panelEliminar = new JPanel(new BorderLayout(5, 5));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Materias Asignadas (Seleccione para quitar)"));

        DefaultListModel<String> modeloMateriasActuales = new DefaultListModel<>();
        for (String m : tutor.getMaterias().keySet()) {
            modeloMateriasActuales.addElement(m);
        }

        JList<String> listaMateriasActuales = new JList<>(modeloMateriasActuales);
        listaMateriasActuales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelEliminar.add(new JScrollPane(listaMateriasActuales), BorderLayout.CENTER);

        JButton btnQuitar = new JButton("Quitar Materia Seleccionada");
        panelEliminar.add(btnQuitar, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {
            try {
                int cupo = Integer.parseInt(txtCupoMateria.getText().trim());
                String materia = (String) cbMaterias.getSelectedItem();

                tutor.agregarMateria(materia, cupo);
                controlador.guardarDatos();
                controlador.refrescarUI();
                actualizarDetalles(seleccionado);

                // Actualizar la lista inferior de la ventana
                if (!modeloMateriasActuales.contains(materia)) {
                    modeloMateriasActuales.addElement(materia);
                }
                JOptionPane.showMessageDialog(dialogo, "Materia añadida.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "El cupo debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnQuitar.addActionListener(e -> {
            String materiaSelec = listaMateriasActuales.getSelectedValue();
            if (materiaSelec != null) {
                tutor.getMaterias().remove(materiaSelec);
                controlador.guardarDatos();
                controlador.refrescarUI();
                actualizarDetalles(seleccionado);

                modeloMateriasActuales.removeElement(materiaSelec);
            }
        });

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());

        dialogo.add(panelAgregar, BorderLayout.NORTH);
        dialogo.add(panelEliminar, BorderLayout.CENTER);
        dialogo.add(btnCerrar, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void abrirGestionHorarios() {
        String seleccionado = listaTutores.getSelectedValue();
        if (seleccionado == null) return;

        Tutor tutor = controlador.buscarTutorPorNombre(seleccionado);
        if (tutor == null) return;

        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        JDialog dialogo = new JDialog(ventanaPadre, "Gestionar Horarios - " + tutor.getNombre(), Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setSize(400, 400);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelAgregar = new JPanel(new GridLayout(2, 2, 5, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Añadir Nuevo Horario"));

        panelAgregar.add(new JLabel("Bloque Horario:"));
        JComboBox<String> cbHorarios = new JComboBox<>(controlador.getCatalogo().getHorarios().toArray(new String[0]));
        panelAgregar.add(cbHorarios);

        JButton btnAgregar = new JButton("Añadir Horario");
        panelAgregar.add(new JLabel("")); // Espaciador
        panelAgregar.add(btnAgregar);

        JPanel panelEliminar = new JPanel(new BorderLayout(5, 5));
        panelEliminar.setBorder(BorderFactory.createTitledBorder("Horarios Asignados (Seleccione para quitar)"));

        DefaultListModel<String> modeloHorariosActuales = new DefaultListModel<>();
        for (String h : tutor.getHorariosDisponibles()) {
            modeloHorariosActuales.addElement(h);
        }

        JList<String> listaHorariosActuales = new JList<>(modeloHorariosActuales);
        listaHorariosActuales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelEliminar.add(new JScrollPane(listaHorariosActuales), BorderLayout.CENTER);

        JButton btnQuitar = new JButton("Quitar Horario Seleccionado");
        panelEliminar.add(btnQuitar, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {
            String horario = (String) cbHorarios.getSelectedItem();

            if (!tutor.getHorariosDisponibles().contains(horario)) {
                tutor.agregarDisponibilidad(horario);
                controlador.guardarDatos();
                controlador.refrescarUI();
                actualizarDetalles(seleccionado);

                modeloHorariosActuales.addElement(horario);
            }
        });

        btnQuitar.addActionListener(e -> {
            String horarioSelec = listaHorariosActuales.getSelectedValue();
            if (horarioSelec != null) {
                tutor.removerDisponibilidad(horarioSelec);
                controlador.guardarDatos();
                controlador.refrescarUI();
                actualizarDetalles(seleccionado);

                modeloHorariosActuales.removeElement(horarioSelec);
            }
        });

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());

        dialogo.add(panelAgregar, BorderLayout.NORTH);
        dialogo.add(panelEliminar, BorderLayout.CENTER);
        dialogo.add(btnCerrar, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    /**
     * Override del método en {@link InterfazObserver}.
     * Se invoca automáticamente cuando el {@link Controlador} avisa de un cambio en el estado del sistema, actualizando visualmente.
     */
    @Override
    public void actualizar() {
        cargarTutores();
    }
}