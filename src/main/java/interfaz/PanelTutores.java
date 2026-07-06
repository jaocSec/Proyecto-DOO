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

        // Nombre y foto
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));


        ImageIcon iconoOriginal = new ImageIcon("src/imagenes/perfil.png");
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon iconoFinal = new ImageIcon(imagenRedimensionada);

        lblAvatar = new JLabel(iconoFinal);
        lblAvatar.setFont(new Font("Segoe UI", Font.PLAIN, 60));

        lblNombre = new JLabel("Seleccione un tutor");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));

        panelHeader.add(lblAvatar);
        panelHeader.add(lblNombre);

        //Información panel derecho
        txtInformacion = new JTextArea("Seleccione un tutor de la lista para ver sus detalles.");
        txtInformacion.setEditable(false);
        txtInformacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInformacion.setOpaque(false);
        txtInformacion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelDerecho.add(panelHeader, BorderLayout.NORTH);
        panelDerecho.add(txtInformacion, BorderLayout.CENTER);


        //Listener click de la lista
        listaTutores.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String seleccionado = listaTutores.getSelectedValue();
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

        cargarTutores();
    }

    private void eliminarTutor() {

        // 1. Verificar selección
        String seleccionado = listaTutores.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor");
            return;
        }

        // 2. Buscar tutor
        Tutor tutor = controlador.buscarTutorPorNombre(seleccionado);
        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Tutor no encontrado");
            return;
        }

        // 3. Confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este tutor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) return;

        // 4. Eliminar
        controlador.eliminarTutor(tutor);

        // 5. Mensaje
        JOptionPane.showMessageDialog(this, "Tutor eliminado correctamente");
    }

    private void editarTutor() {

        // 1. Verificar selección
        String seleccionado = listaTutores.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor");
            return;
        }

        // 2. Buscar tutor en controlador
        Tutor tutor = controlador.buscarTutorPorNombre(seleccionado);
        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Tutor no encontrado");
            return;
        }

        // 3. Pedir nuevos datos (solo lo editable)
        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre del tutor:", tutor.getNombre());
        if (nuevoNombre == null || nuevoNombre.isBlank()) return;

        String nuevoCorreo = JOptionPane.showInputDialog(this, "Nuevo correo:", tutor.getCorreo());
        if (nuevoCorreo == null || nuevoCorreo.isBlank()) return;

        String nuevaTarifaStr = JOptionPane.showInputDialog(this, "Nueva tarifa por hora:", tutor.getTarifaHora());
        if (nuevaTarifaStr == null || nuevaTarifaStr.isBlank()) return;

        double nuevaTarifa;

        try {
            nuevaTarifa = Double.parseDouble(nuevaTarifaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tarifa inválida");
            return;
        }

        // 4. Aplicar cambios
        tutor.setNombre(nuevoNombre);
        tutor.setCorreo(nuevoCorreo);
        tutor.setTarifaHora(nuevaTarifa);

        // 5. Refrescar interfaz
        controlador.refrescarUI();

        // 6. Mensaje final
        JOptionPane.showMessageDialog(this, "Tutor actualizado correctamente");
    }

    private void agregarTutor() {

        String id = JOptionPane.showInputDialog(this, "ID del tutor:");
        if (id == null || id.isBlank()) return;

        String nombre = JOptionPane.showInputDialog(this, "Nombre del tutor:");
        if (nombre == null || nombre.isBlank()) return;

        String correo = JOptionPane.showInputDialog(this, "Correo:");
        if (correo == null || correo.isBlank()) return;

        String tarifaStr = JOptionPane.showInputDialog(this, "Tarifa por hora:");
        if (tarifaStr == null || tarifaStr.isBlank()) return;

        double tarifa;

        try {
            tarifa = Double.parseDouble(tarifaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tarifa inválida");
            return;
        }

        // 1. Crear tutor
        Tutor nuevo = new Tutor(id, nombre, correo, tarifa);

        // 2. Agregar datos básicos (opcional demo)
        nuevo.agregarMateria("General", 5);
        nuevo.agregarDisponibilidad("LUN 10:00");

        // 3. Registrar en controlador
        controlador.registrarTutor(nuevo);

        // 4. Mensaje
        JOptionPane.showMessageDialog(this, "Tutor agregado correctamente");
    }

    private void actualizarDetalles(String nombre) {

        Tutor tutor = controlador.buscarTutorPorNombre(nombre);

        if (tutor == null) return;

        lblNombre.setText(tutor.getNombre());

        StringBuilder info = new StringBuilder();

        info.append("Correo: ").append(tutor.getCorreo()).append("\n\n");
        info.append("Tarifa por hora: $").append(tutor.getTarifaHora()).append("\n\n");

        info.append("Materias:\n");
        for (String materia : tutor.getMaterias().keySet()) {
            info.append(" - ")
                    .append(materia)
                    .append(" (máx ")
                    .append(tutor.getMaterias().get(materia))
                    .append(")\n");
        }

        info.append("\nDisponibilidad:\n");
        for (String h : tutor.getHorariosDisponibles()) {
            info.append(" - ").append(h).append("\n");
        }

        txtInformacion.setText(info.toString());
    }
    @Override
    public void actualizar() {
        cargarTutores();
    }
}