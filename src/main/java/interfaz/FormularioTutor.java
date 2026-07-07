package interfaz;

import logica.Controlador;
import javax.swing.*;
import java.awt.*;

public class FormularioTutor extends JDialog {
    private Controlador controlador;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTarifa;
    private JTextField txtRut;
    private JComboBox<String> cbMateria;
    private JComboBox<String> cbHorario;
    private boolean guardado = false;

    public FormularioTutor(Window parent, Controlador controlador) {
        super(parent, "Agregar Nuevo Tutor", Dialog.ModalityType.APPLICATION_MODAL);
        this.controlador= controlador;


        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 15));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelDatos.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("RUT:"));
        txtRut= new JTextField();
        panelDatos.add(txtRut);

        panelDatos.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField();
        panelDatos.add(txtCorreo);

        panelDatos.add(new JLabel("Tarifa:"));
        txtTarifa = new JTextField();
        panelDatos.add(txtTarifa);

        //Desplegable materia
        panelDatos.add(new JLabel("Materia:"));
        cbMateria = new JComboBox<>(controlador.getCatalogo().getMaterias().toArray(new String[0]));
        panelDatos.add(cbMateria);

        //Desplegable horarios
        panelDatos.add(new JLabel("Horario Inicial:"));
        cbHorario = new JComboBox<>(controlador.getCatalogo().getHorarios().toArray(new String[0]));
        panelDatos.add(cbHorario);

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().isBlank() || txtCorreo.getText().isBlank() || txtTarifa.getText().isBlank() || txtRut.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos de texto.", "Error", JOptionPane.ERROR_MESSAGE);
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

    //Constructor edicion
    public FormularioTutor(Window parent, Controlador controlador, logica.modelos.Tutor tutor) {
        this(parent, controlador);
        if (tutor != null) {
            setTitle("Editar Tutor");
            txtNombre.setText(tutor.getNombre());
            txtCorreo.setText(tutor.getCorreo());
            txtTarifa.setText(String.valueOf(tutor.getTarifaHora()));

            //Se deshabilitan los dropdowns porque en edición se usarán los botones de gestión
            cbMateria.setEnabled(false);
            cbHorario.setEnabled(false);
        }
    }

    public boolean isGuardado() { return guardado; }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public double getTarifa() { return Double.parseDouble(txtTarifa.getText().trim()); }
    public String getMateria() { return (String) cbMateria.getSelectedItem(); }
    public String getHorario() { return (String) cbHorario.getSelectedItem(); }
    public String getRUT() {return txtRut.getText().trim();}
}