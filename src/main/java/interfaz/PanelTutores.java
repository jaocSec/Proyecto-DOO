package interfaz;

import logica.Controlador;
import logica.modelos.Tutor;
import logica.observer.InterfazObserver;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

            controlador.refrescarUI();
            JOptionPane.showMessageDialog(this, "Tutor actualizado correctamente");
        }
    }

    private void agregarTutor() {
        FormularioTutor form = new FormularioTutor(SwingUtilities.getWindowAncestor(this), this.controlador);
        form.setVisible(true);

        if (form.isGuardado()) {

            Tutor nuevoTutor = new Tutor(form.getRUT(), form.getNombre(), form.getCorreo(), form.getTarifa());

            nuevoTutor.agregarMateria(form.getMateria(), 5);
            nuevoTutor.agregarDisponibilidad(form.getHorario());

            controlador.registrarTutor(nuevoTutor);
            JOptionPane.showMessageDialog(this, "Tutor agregado correctamente");
        }
    }

    private void actualizarDetalles(String nombre) {

        Tutor tutor = controlador.buscarTutorPorNombre(nombre);

        if (tutor == null) return;

        lblNombre.setText(tutor.getNombre());
        String info= "RUT: " + tutor.getRUT() + "\n\n" +
                "Correo: " + tutor.getCorreo() + "\n\n" +
                "Tarifa por hora: $" + tutor.getTarifaHora() + "\n\n" +
                "Materias:\n";

        for (String materia : tutor.getMaterias().keySet()) {
            info += " - " + materia + " (máx " + tutor.getMaterias().get(materia) + ")\n";
        }

        info += "\nDisponibilidad:\n";
        for (String h : tutor.getHorariosDisponibles()) {
            info += " - " + h + "\n";
        }

        txtInformacion.setText(info.toString());
    }
    @Override
    public void actualizar() {
        cargarTutores();
    }
}