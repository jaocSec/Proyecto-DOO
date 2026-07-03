package interfaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCalendario extends JPanel {
    private JComboBox<String> comboEstudiantes;
    private JComboBox<String> comboTutores;
    private JButton btnLimpiarFiltros;
    private JButton btnCancelarClase;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;

    public PanelCalendario() {

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

        //EJEMPLO!!!!!
        llenarDatos();

        JScrollPane scrollTabla = new JScrollPane(tablaReservas);

        JPanel panelTablaWrapper = new JPanel(new BorderLayout());
        panelTablaWrapper.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelTablaWrapper.add(scrollTabla, BorderLayout.CENTER);

        add(panelTablaWrapper, BorderLayout.CENTER);



        //Botón cancelar clase
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCancelarClase = new JButton("Cancelar Clase Seleccionada");
        btnCancelarClase.setForeground(Color.RED);

        panelAcciones.add(btnCancelarClase);

        add(panelAcciones, BorderLayout.SOUTH);
    }

    //Datos ejemplo
    private void llenarDatos() {
        modeloTabla.addRow(new Object[]{"07-07-2026", "15:00 - 16:30", "Cálculo III", "María Soto", "Juan Pérez", "Confirmada"});
        modeloTabla.addRow(new Object[]{"08-07-2026", "10:00 - 11:30", "Física II", "Pedro Aranda", "Ana Gómez", "Confirmada"});
        modeloTabla.addRow(new Object[]{"09-07-2026", "17:00 - 18:00", "Programación", "Valentina Reyes", "Carlos Silva", "Pendiente"});
    }
}