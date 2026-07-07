package interfaz;

import logica.Controlador;
import logica.observer.InterfazObserver;

import javax.swing.*;
import java.awt.*;

public class PanelConfiguracion extends JPanel implements InterfazObserver {
    private Controlador controlador;
    private DefaultListModel<String> modeloMaterias;
    private DefaultListModel<String> modeloHorarios;
    private JList<String> listaMaterias;
    private JList<String> listaHorarios;

    public PanelConfiguracion(Controlador controlador) {
        this.controlador = controlador;
        this.controlador.agregarObservador(this);

        setLayout(new GridLayout(1, 2, 20, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(crearPanelGestion("Materias", true));

        add(crearPanelGestion("Horarios", false));

        cargarDatos();
    }

    private JPanel crearPanelGestion(String titulo, boolean esMateria) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Gestión de " + titulo));

        //lista
        DefaultListModel<String> modelo = new DefaultListModel<>();
        JList<String> lista = new JList<>(modelo);
        lista.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        if (esMateria) {
            modeloMaterias = modelo;
            listaMaterias = lista;
        } else {
            modeloHorarios = modelo;
            listaHorarios = lista;
        }

        panel.add(new JScrollPane(lista), BorderLayout.CENTER);

        //Botones agregar/quitar
        JPanel panelControles = new JPanel(new BorderLayout(5, 5));

        //agregar
        JPanel panelAgregar = new JPanel(new BorderLayout(5, 0));
        JTextField txtNuevo = new JTextField();
        JButton btnAgregar = new JButton("Agregar");
        panelAgregar.add(txtNuevo, BorderLayout.CENTER);
        panelAgregar.add(btnAgregar, BorderLayout.EAST);

        //eliminar
        JButton btnEliminar = new JButton("Eliminar");

        panelControles.add(panelAgregar, BorderLayout.NORTH);
        panelControles.add(btnEliminar, BorderLayout.SOUTH);

        panel.add(panelControles, BorderLayout.SOUTH);

        //listener
        btnAgregar.addActionListener(e -> {
            String nuevoDato = txtNuevo.getText().trim();
            if (!nuevoDato.isEmpty()) {
                if (esMateria) {
                    controlador.getCatalogo().agregarMateria(nuevoDato);
                } else {
                    controlador.getCatalogo().agregarHorario(nuevoDato);
                }
                txtNuevo.setText("");
                controlador.refrescarUI();
            }
        });

        btnEliminar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado != null) {
                if (esMateria) {
                    controlador.getCatalogo().removerMateria(seleccionado);
                } else {
                    controlador.getCatalogo().removerHorario(seleccionado);
                }
                controlador.refrescarUI();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un elemento de la lista para eliminar.");
            }
        });

        return panel;
    }

    private void cargarDatos() {
        modeloMaterias.clear();
        modeloHorarios.clear();

        if (controlador.getCatalogo() != null) {
            for (String m : controlador.getCatalogo().getMaterias()) {
                modeloMaterias.addElement(m);
            }
            for (String h : controlador.getCatalogo().getHorarios()) {
                modeloHorarios.addElement(h);
            }
        }
    }

    @Override
    public void actualizar() {
        cargarDatos();
    }
}