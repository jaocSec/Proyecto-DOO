package interfaz;

import logica.Controlador;
import logica.modelos.Estudiante;
import logica.observer.InterfazObserver;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelEstudiantes extends JPanel implements InterfazObserver {
    private Controlador controlador;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaEstudiantes;
    private JButton btnAgregar, btnEditar, btnEliminar;

    //Componentes panel derecho
    private JLabel lblNombre;
    private JTextArea txtInformacion;

    private void cargarEstudiantes() {
        modeloLista.clear();

        for (Estudiante e : controlador.getEstudiantes()) {
            modeloLista.addElement(e.getNombre());
        }
    }

    public PanelEstudiantes(Controlador controlador) {
        modeloLista = new DefaultListModel<>();
        listaEstudiantes = new JList<>(modeloLista);

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
        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnEditar.addActionListener(e -> editarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        //Lista de tutores
        modeloLista = new DefaultListModel<>(); //Lista con nombres
        for (Estudiante estudiante : controlador.getEstudiantes()) {
            modeloLista.addElement(estudiante.getNombre());
        }

        listaEstudiantes = new JList<>(modeloLista); //Lista gráfica
        listaEstudiantes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollLista = new JScrollPane(listaEstudiantes);

        panelIzquierdo.add(panelBotones, BorderLayout.NORTH);
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER);


        //Panel detalles (derecho)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nombre
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        lblNombre = new JLabel("Seleccione un estudiante");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));

        panelHeader.add(lblNombre);

        //Información panel derecho
        txtInformacion = new JTextArea("Seleccione estudiante de la lista para ver sus detalles.");
        txtInformacion.setEditable(false);
        txtInformacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInformacion.setOpaque(false);
        txtInformacion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelDerecho.add(panelHeader, BorderLayout.NORTH);
        panelDerecho.add(txtInformacion, BorderLayout.CENTER);


        //Listener click de la lista
        listaEstudiantes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String seleccionado = listaEstudiantes.getSelectedValue();
                    if (seleccionado != null) {
                        actualizarDetalles(seleccionado);
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

        cargarEstudiantes();
    }

    private void eliminarEstudiante() {

        String seleccionado = listaEstudiantes.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante");
            return;
        }

        Estudiante est = controlador.buscarEstudiantePorNombre(seleccionado);
        if (est == null) {
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este estudiante?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) return;

        controlador.eliminarEstudiante(est);
        JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente");
    }

    private void editarEstudiante() {
        String seleccionado = listaEstudiantes.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante");
            return;
        }

        Estudiante est = controlador.buscarEstudiantePorNombre(seleccionado);
        if (est == null) {
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
            return;
        }

        FormularioEstudiante form = new FormularioEstudiante(SwingUtilities.getWindowAncestor(this), est);
        form.setVisible(true);

        if (form.isGuardado()) {
            est.setNombre(form.getNombre());
            est.setCorreo(form.getCorreo());
            est.setTelefono(form.getTelefono());

            controlador.refrescarUI();
            JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente");
        }
    }

    private void agregarEstudiante() {
        FormularioEstudiante form = new FormularioEstudiante(SwingUtilities.getWindowAncestor(this));
        form.setVisible(true);

        if (form.isGuardado()) {
            Estudiante nuevo = new Estudiante(form.getRUT(), form.getNombre(), form.getCorreo(), form.getTelefono());
            controlador.registrarEstudiante(nuevo);
            JOptionPane.showMessageDialog(this, "Estudiante agregado correctamente");
        }
    }

    private void actualizarDetalles(String nombre) {

        Estudiante estudiante = controlador.buscarEstudiantePorNombre(nombre);

        if (estudiante == null) return;

        lblNombre.setText(estudiante.getNombre());

        txtInformacion.setText(
                        "RUT: " + estudiante.getRUT() + "\n\n" +
                        "Correo: " + estudiante.getCorreo() + "\n\n" +
                        "Teléfono: " + estudiante.getTelefono()
        );
    }
    @Override
    public void actualizar() {
        cargarEstudiantes();
    }
}