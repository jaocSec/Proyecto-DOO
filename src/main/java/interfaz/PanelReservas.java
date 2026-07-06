package interfaz;

import logica.Controlador;
import logica.modelos.Reserva;
import logica.observer.InterfazObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelReservas extends JPanel implements InterfazObserver {
    private Controlador controlador;
    private JComboBox<String> comboEstudiantes;
    private JComboBox<String> comboTutores;
    private JButton btnNuevaReserva;
    private JButton btnLimpiarFiltros;
    private JButton btnCancelarClase;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;

    public PanelReservas(Controlador controlador) {

        this.controlador = controlador;
        controlador.agregarObservador(this);

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtrar Reservas"));

        // Filtro estudiante
        panelFiltros.add(new JLabel("Estudiante:"));
        comboEstudiantes = new JComboBox<>(new String[]{"Todos", "María Soto", "Pedro Aranda", "Valentina Reyes"}); //Ejemplos
        comboEstudiantes.setPreferredSize(new Dimension(180, 30));
        panelFiltros.add(comboEstudiantes);

        //Filtro tutor
        panelFiltros.add(new JLabel("  Tutor:"));
        comboTutores = new JComboBox<>(new String[]{"Todos", "Juan Pérez", "Ana Gómez", "Carlos Silva"}); //Ejemplos
        comboTutores.setPreferredSize(new Dimension(180, 30));
        panelFiltros.add(comboTutores);

        //Agrega Reserva
        btnNuevaReserva = new JButton("Nueva Reserva");
        panelFiltros.add(btnNuevaReserva);
        btnNuevaReserva.addActionListener(e -> abrirFormularioReserva());

        //Reset filtros
        btnLimpiarFiltros = new JButton("Limpiar Filtros");
        panelFiltros.add(btnLimpiarFiltros);
        add(panelFiltros, BorderLayout.NORTH);

        //Tabla
        String[] columnas = {"Fecha", "Horario", "Materia", "Estudiante", "Tutor", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaReservas.setRowHeight(25); // Filas más altas para mejor lectura
        tablaReservas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaReservas.getTableHeader().setReorderingAllowed(false);

        tablaReservas.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                if (e.getClickCount() == 2){
                    int filaSeleccionada= tablaReservas.getSelectedRow();

                    if (filaSeleccionada != -1){
                       int filaModelo= tablaReservas.convertRowIndexToModel(filaSeleccionada);

                       //EJEMPLOS!!
                        String tutor = (String) modeloTabla.getValueAt(filaModelo, 0);
                        String estudiante = (String) modeloTabla.getValueAt(filaModelo, 1);
                        String fecha = (String) modeloTabla.getValueAt(filaModelo, 2);
                        String estado = (String) modeloTabla.getValueAt(filaModelo, 3);

                        Window ventanaPadre= SwingUtilities.getWindowAncestor(tablaReservas);

                        Reserva reserva =
                                controlador.getReservas().get(filaModelo);

                        DetalleReservas ventanaDetalle =
                                new DetalleReservas(ventanaPadre, controlador);
                        ventanaDetalle.setVisible(true);

                    }
                }
            }
        });

        cargarReservas();

        JScrollPane scrollTabla = new JScrollPane(tablaReservas);

        JPanel panelTablaWrapper = new JPanel(new BorderLayout());
        panelTablaWrapper.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelTablaWrapper.add(scrollTabla, BorderLayout.CENTER);

        add(panelTablaWrapper, BorderLayout.CENTER);



        //Botón cancelar clase
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCancelarClase = new JButton("Cancelar Clase Seleccionada");
        btnCancelarClase.setForeground(Color.RED);
        btnCancelarClase.addActionListener(e -> cancelarReserva());

        panelAcciones.add(btnCancelarClase);

        add(panelAcciones, BorderLayout.SOUTH);
    }

    private void abrirFormularioReserva() {
        Window ventanaPadre =
                SwingUtilities.getWindowAncestor(this);

        DetalleReservas formulario =
                new DetalleReservas(
                        ventanaPadre,
                        controlador
                );

        formulario.setVisible(true);
    }

    private void cancelarReserva() {

        int fila = tablaReservas.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una reserva.");
            return;
        }

        Reserva reserva = controlador.getReservas().get(fila);

        reserva.cancelar();

        controlador.refrescarUI();

    }

    private void cargarReservas() {

        modeloTabla.setRowCount(0);

        for (Reserva reserva : controlador.getReservas()) {

            modeloTabla.addRow(new Object[]{

                    reserva.getFecha(),

                    reserva.getHorario(),

                    reserva.getMateria(),

                    reserva.getEstudiante() != null
                            ? reserva.getEstudiante().getNombre()
                            : "",

                    reserva.getTutor() != null
                            ? reserva.getTutor().getNombre()
                            : "",

                    reserva.getEstadoActual().getClass().getSimpleName()
                            .replace("Estado","")

            });

        }

    }
    @Override
    public void actualizar() {
        cargarReservas();
    }
}