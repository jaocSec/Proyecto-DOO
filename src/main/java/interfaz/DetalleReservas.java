package interfaz;

import logica.Controlador;
import logica.ReservaFactory;
import logica.modelos.Estudiante;
import logica.modelos.Reserva;
import logica.modelos.Tutor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DetalleReservas extends JDialog {
    private Controlador controlador;
    private JComboBox<String> cbEstudiante;
    private JComboBox<String> cbMateria;
    private JComboBox<String> cbHorario;
    private JComboBox<String> cbTutor;
    private JComboBox<String> cbTipoReserva;
    private JTextField txtDetalleExtra;
    private JButton btnGuardar;

    public DetalleReservas(Window parent, Controlador controlador) {
        super(parent, "Crear Nueva Reserva", Dialog.ModalityType.APPLICATION_MODAL);
        this.controlador = controlador;

        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 15, 20));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Selección estudiante
        panelFormulario.add(new JLabel("Estudiante:"));
        cbEstudiante = new JComboBox<>();
        for (Estudiante e : controlador.getEstudiantes()) {
            cbEstudiante.addItem(e.getNombre());
        }
        panelFormulario.add(cbEstudiante);

        //Selección materia
        panelFormulario.add(new JLabel("Materia:"));
        cbMateria = new JComboBox<>(controlador.getCatalogo().getMaterias().toArray(new String[0]));
        panelFormulario.add(cbMateria);

        //selección horario
        panelFormulario.add(new JLabel("Horario:"));
        cbHorario = new JComboBox<>(controlador.getCatalogo().getHorarios().toArray(new String[0]));
        panelFormulario.add(cbHorario);

        //Selección tutor
        panelFormulario.add(new JLabel("Tutor Compatible:"));
        cbTutor = new JComboBox<>();
        panelFormulario.add(cbTutor);

        //Tipo de Reserva
        panelFormulario.add(new JLabel("Modalidad:"));
        cbTipoReserva = new JComboBox<>(new String[]{"VIRTUAL", "PRESENCIAL"});
        panelFormulario.add(cbTipoReserva);

        //Plataforma o sala
        JLabel lblDetalleExtra = new JLabel("Plataforma");
        txtDetalleExtra = new JTextField();
        panelFormulario.add(lblDetalleExtra);
        panelFormulario.add(txtDetalleExtra);

        //Listener para cambio dinámico de lista mostrada en dropdown
        cbTipoReserva.addActionListener(e -> {
            if (cbTipoReserva.getSelectedItem().equals("PRESENCIAL")) {
                lblDetalleExtra.setText("Sala");
            } else {
                lblDetalleExtra.setText("Plataforma");
            }
        });

        add(panelFormulario, BorderLayout.CENTER);

        //Panel Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Crear Reserva");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);



        //LISTENERS
        java.awt.event.ActionListener actualizadorTutores = e -> actualizarTutoresCompatibles();
        cbMateria.addActionListener(actualizadorTutores);
        cbHorario.addActionListener(actualizadorTutores);
        actualizarTutoresCompatibles();

        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> registrarReserva());
    }

    private void actualizarTutoresCompatibles() {
        cbTutor.removeAllItems();
        String materiaSel = (String) cbMateria.getSelectedItem();
        String horarioSel = (String) cbHorario.getSelectedItem();

        if (materiaSel != null && horarioSel != null) {
            List<Tutor> compatibles = controlador.buscarTutoresCompatibles(materiaSel, horarioSel);

            if (compatibles.isEmpty()) {
                cbTutor.addItem("Ningún tutor disponible");
                btnGuardar.setEnabled(false);

            } else {
                for (Tutor t : compatibles) {
                    cbTutor.addItem(t.getNombre());
                }
                btnGuardar.setEnabled(true);
            }
        }
    }

    private void registrarReserva() {
        String nombreEstudiante= (String) cbEstudiante.getSelectedItem();
        String nombreTutor= (String) cbTutor.getSelectedItem();
        String materia= (String) cbMateria.getSelectedItem();
        String horario= (String) cbHorario.getSelectedItem();
        String tipo= (String) cbTipoReserva.getSelectedItem();
        String detalleExtra= txtDetalleExtra.getText().trim();


        if (nombreEstudiante == null || nombreTutor == null || nombreTutor.equals("Ningún tutor disponible")) {
            JOptionPane.showMessageDialog(this, "Faltan datos o no hay tutor seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (detalleExtra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la sala o plataforma correspondiente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estudiante estudiante = controlador.buscarEstudiantePorNombre(nombreEstudiante);
        Tutor tutor = controlador.buscarTutorPorNombre(nombreTutor);

        Reserva nuevaReserva = ReservaFactory.crearReserva(tipo, estudiante, tutor, materia, "fechaStr", horario, detalleExtra);

        controlador.registrarReserva(nuevaReserva);
        JOptionPane.showMessageDialog(this, "Reserva creada exitosamente.");
        dispose();
    }
}