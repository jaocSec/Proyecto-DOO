package interfaz;

import javax.swing.*;
import java.awt.*;

public class FormularioTutor extends JDialog {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTarifa;
    private JTextField txtMateria;
    private JTextField txtHorario;
    private boolean guardado = false;

    public FormularioTutor(Window parent) {
        super(parent, "Agregar Nuevo Tutor", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(5, 2, 10, 15));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelDatos.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField();
        panelDatos.add(txtCorreo);

        panelDatos.add(new JLabel("Tarifa:"));
        txtTarifa = new JTextField();
        panelDatos.add(txtTarifa);

        panelDatos.add(new JLabel("Materia:"));
        txtMateria = new JTextField();
        panelDatos.add(txtMateria);

        panelDatos.add(new JLabel("Horario Disponible"));
        txtHorario = new JTextField();
        panelDatos.add(txtHorario);

        add(panelDatos, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().isBlank() || txtCorreo.getText().isBlank() || txtTarifa.getText().isBlank() ||
                    txtMateria.getText().isBlank() || txtHorario.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Double.parseDouble(txtTarifa.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La tarifa debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardado = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public FormularioTutor(Window parent, logica.modelos.Tutor tutor) {
        this(parent);
        if (tutor != null) {
            setTitle("Editar Tutor");
            txtNombre.setText(tutor.getNombre());
            txtCorreo.setText(tutor.getCorreo());
            txtTarifa.setText(String.valueOf(tutor.getTarifaHora()));

            txtMateria.setEnabled(false);
            txtHorario.setEnabled(false);

            if (!tutor.getMaterias().isEmpty()) {
                txtMateria.setText(tutor.getMaterias().keySet().iterator().next());
            }
            if (!tutor.getHorariosDisponibles().isEmpty()) {
                txtHorario.setText(tutor.getHorariosDisponibles().iterator().next());
            }
        }
    }

    public boolean isGuardado() { return guardado; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public double getTarifa() { return Double.parseDouble(txtTarifa.getText().trim()); }
    public String getMateria() { return txtMateria.getText().trim(); }
    public String getHorario() { return txtHorario.getText().trim(); }
}