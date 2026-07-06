package interfaz;

import logica.Controlador;
import logica.modelos.Estudiante;
import logica.modelos.Reserva;
import logica.modelos.Tutor;

import javax.swing.*;
import java.awt.*;

public class DetalleReservas extends JDialog {
    private Controlador controlador;
    //AGREGAR RESERVA!!
    public DetalleReservas(Window parent, Controlador controlador) {

        //Ventana
        super(parent, "Detalles Reserva", Dialog.ModalityType.APPLICATION_MODAL);

        this.controlador = controlador;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        //Panel Central
        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 15));
        JTextField txtId = new JTextField();

        JTextField txtMateria = new JTextField();

        JTextField txtHorario = new JTextField();
        JComboBox<Estudiante> comboEstudiantes =
                new JComboBox<>();
        for(Estudiante e : controlador.getEstudiantes()){

            comboEstudiantes.addItem(e);

        }
        JComboBox<Tutor> comboTutores = new JComboBox<>();

        for (Tutor t : controlador.getTutores()) {
            comboTutores.addItem(t);
        }
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelDatos.add(new JLabel("ID:"));
        panelDatos.add(txtId);

        panelDatos.add(new JLabel("Estudiante:"));
        panelDatos.add(comboEstudiantes);

        panelDatos.add(new JLabel("Tutor:"));
        panelDatos.add(comboTutores);

        panelDatos.add(new JLabel("Materia:"));
        panelDatos.add(txtMateria);

        panelDatos.add(new JLabel("Horario:"));
        panelDatos.add(txtHorario);
        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {

            controlador.registrarReserva(
                    "PRESENCIAL",
                    txtId.getText(),
                    (Estudiante) comboEstudiantes.getSelectedItem(),
                    (Tutor) comboTutores.getSelectedItem(),
                    txtMateria.getText(),
                    "06-07-2026",
                    txtHorario.getText(),
                    "Sala 201"
            );

            dispose();
        });
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBoton.add(btnGuardar);
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }
}