package interfaz;

import logica.Controlador;
import logica.modelos.Reserva;
import logica.observer.InterfazObserver;
import logica.modelos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;


public class PanelReservas extends JPanel implements InterfazObserver {
    private List<Reserva> reservasMostradas = new java.util.ArrayList<>();
    private Controlador controlador;
    private JComboBox<String> comboEstudiantes;
    private JComboBox<String> comboTutores;
    private JButton btnNuevaReserva;
    private JButton btnLimpiarFiltros;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;

    private JButton btnConfirmarReserva;
    private JButton btnCancelarReserva;

    public PanelReservas(Controlador controlador) {

        this.controlador= controlador;
        controlador.agregarObservador(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelFiltros= new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtrar Reservas"));

        // Filtro estudiante
        panelFiltros.add(new JLabel("Estudiante:"));
        comboEstudiantes= new JComboBox<>();
        comboEstudiantes.setPreferredSize(new Dimension(180, 30));
        panelFiltros.add(comboEstudiantes);

        //Filtro tutor
        panelFiltros.add(new JLabel("  Tutor:"));
        comboTutores= new JComboBox<>();
        comboTutores.setPreferredSize(new Dimension(180, 30));
        panelFiltros.add(comboTutores);

        //Agrega Reserva
        btnNuevaReserva= new JButton("Nueva Reserva");
        panelFiltros.add(btnNuevaReserva);

        //Reset filtros
        btnLimpiarFiltros= new JButton("Limpiar Filtros");
        panelFiltros.add(btnLimpiarFiltros);
        add(panelFiltros, BorderLayout.NORTH);

        //Tabla
        String[] columnas= {"Fecha", "Horario", "Materia", "Estudiante", "Tutor", "Estado"};
        modeloTabla= new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        tablaReservas= new JTable(modeloTabla);
        tablaReservas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaReservas.setRowHeight(25);
        tablaReservas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaReservas.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollTabla= new JScrollPane(tablaReservas);
        JPanel panelTablaWrapper= new JPanel(new BorderLayout());
        panelTablaWrapper.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelTablaWrapper.add(scrollTabla, BorderLayout.CENTER);

        add(panelTablaWrapper, BorderLayout.CENTER);


        JPanel panelAcciones= new JPanel(new FlowLayout(FlowLayout.RIGHT, 10 , 0));

        btnConfirmarReserva= new JButton("Confirmar Reserva");
        btnConfirmarReserva.setForeground(new Color(0, 128, 0));

        btnCancelarReserva= new JButton("Cancelar Reserva");
        btnCancelarReserva.setForeground(Color.RED);

        panelAcciones.add(btnConfirmarReserva);
        panelAcciones.add(btnCancelarReserva);
        add(panelAcciones, BorderLayout.SOUTH);

        btnNuevaReserva.addActionListener(e -> abrirFormularioReserva());

        //Listeners
        ActionListener filtroListener = e -> cargarReservas();
        comboEstudiantes.addActionListener(filtroListener);
        comboTutores.addActionListener(filtroListener);

        btnLimpiarFiltros.addActionListener(e -> {
            comboEstudiantes.setSelectedIndex(0);
            comboTutores.setSelectedIndex(0);
            cargarReservas();
        });

        //Cambiar Estado
        btnConfirmarReserva.addActionListener(e -> cambiarEstadoReserva("CONFIRMAR"));
        btnCancelarReserva.addActionListener(e -> cambiarEstadoReserva("CANCELAR"));

        actualizarFiltros();
        cargarReservas();
    }

    private void actualizarFiltros() {
        Object estSeleccionado = comboEstudiantes.getSelectedItem();
        Object tutSeleccionado = comboTutores.getSelectedItem();

        ActionListener[] estListeners = comboEstudiantes.getActionListeners();
        for (ActionListener l : estListeners) comboEstudiantes.removeActionListener(l);

        ActionListener[] tutListeners = comboTutores.getActionListeners();
        for (ActionListener l : tutListeners) comboTutores.removeActionListener(l);

        comboEstudiantes.removeAllItems();
        comboEstudiantes.addItem("Todos");
        for (Estudiante e : controlador.getEstudiantes()) {
            comboEstudiantes.addItem(e.getNombre());
        }

        comboTutores.removeAllItems();
        comboTutores.addItem("Todos");
        for (Tutor t : controlador.getTutores()) {
            comboTutores.addItem(t.getNombre());
        }

        if (estSeleccionado != null) comboEstudiantes.setSelectedItem(estSeleccionado);
        if (tutSeleccionado != null) comboTutores.setSelectedItem(tutSeleccionado);

        for (ActionListener l : estListeners) comboEstudiantes.addActionListener(l);
        for (ActionListener l : tutListeners) comboTutores.addActionListener(l);
    }

    private void cargarReservas() {
        modeloTabla.setRowCount(0);
        reservasMostradas.clear();

        String filtroEstudiante = (String) comboEstudiantes.getSelectedItem();
        String filtroTutor = (String) comboTutores.getSelectedItem();

        for (Reserva reserva : controlador.getReservas()) {
            String nombreEstudiante = reserva.getEstudiante() != null ? reserva.getEstudiante().getNombre() : "Sin asignar";
            String nombreTutor = reserva.getTutor() != null ? reserva.getTutor().getNombre() : "Sin asignar";

            boolean coincideEst = filtroEstudiante == null || filtroEstudiante.equals("Todos") || nombreEstudiante.equals(filtroEstudiante);
            boolean coincideTut = filtroTutor == null || filtroTutor.equals("Todos") || nombreTutor.equals(filtroTutor);

            if (coincideEst && coincideTut) {
                reservasMostradas.add(reserva);
                modeloTabla.addRow(new Object[]{
                        reserva.getFecha(),
                        reserva.getHorario(),
                        reserva.getMateria(),
                        nombreEstudiante,
                        nombreTutor,
                        reserva.getEstadoActual().getClass().getSimpleName().replace("Estado", "")
                });
            }
        }
    }

    private void cambiarEstadoReserva(String accion) {
        int filaVista = tablaReservas.getSelectedRow();
        if (filaVista == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva de la tabla primero.");
            return;
        }

        int filaModelo = tablaReservas.convertRowIndexToModel(filaVista);
        Reserva reserva = reservasMostradas.get(filaModelo);

        switch (accion) {
            case "CONFIRMAR":
                reserva.confirmar();
                break;
            case "CANCELAR":
                reserva.cancelar();
                break;
        }

        controlador.refrescarUI();
    }

    private void abrirFormularioReserva() {
        Window ventanaPadre = SwingUtilities.getWindowAncestor(this);
        DetalleReservas formulario = new DetalleReservas(ventanaPadre, controlador);
        formulario.setVisible(true);
    }

    @Override
    public void actualizar() {
        actualizarFiltros();
        cargarReservas();
    }
}