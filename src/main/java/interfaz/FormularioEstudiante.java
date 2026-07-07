package interfaz;

import javax.swing.*;
import java.awt.*;

public class FormularioEstudiante extends JDialog {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtRut;
    private boolean guardado = false;

    public FormularioEstudiante(Window parent) {
        super(parent, "Agregar Nuevo Estudiante", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 15));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelDatos.add(new JLabel("Nombre Completo:"));
        txtNombre= new JTextField();
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("RUT:"));
        txtRut= new JTextField();
        panelDatos.add(txtRut);

        panelDatos.add(new JLabel("Correo Electrónico:"));
        txtCorreo= new JTextField();
        panelDatos.add(txtCorreo);

        panelDatos.add(new JLabel("Teléfono:"));
        txtTelefono= new JTextField();
        panelDatos.add(txtTelefono);

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBotones= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar= new JButton("Guardar");
        JButton btnCancelar= new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().isBlank() || txtCorreo.getText().isBlank() || txtTelefono.getText().isBlank() || txtRut.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            guardado= true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    //Constructor edición
    public FormularioEstudiante(Window parent, logica.modelos.Estudiante estudiante) {
        this(parent);
        if (estudiante != null) {
            setTitle("Editar Estudiante");
            txtNombre.setText(estudiante.getNombre());
            txtCorreo.setText(estudiante.getCorreo());
            txtTelefono.setText(estudiante.getTelefono());
            txtRut.setText(estudiante.getRUT());
        }
    }

    public boolean isGuardado() { return guardado; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }
    public String getRUT() {return txtRut.getText().trim();}
}