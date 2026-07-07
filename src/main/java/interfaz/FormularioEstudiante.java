package interfaz;

import javax.swing.*;
import java.awt.*;

/**
 * Cuadro de diálogo que contiene un formulario para gestionar estudiantes.
 * Permite tanto el registro de nuevos estudiantes y la edición de los datos de estudiantes ya existentes.
 */
public class FormularioEstudiante extends JDialog {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtRut;
    private boolean guardado = false;

    /**
     * Construye el formulario para la creación de un nuevo estudiante.
     * Configura los campos de texto vacíos y los botones de acción para guardar o cancelar. Implementa una validación básica de los datos.
     *
     * @param parent La ventana principal que será padre de este diálogo.
     */
    public FormularioEstudiante(Window parent) {
        super(parent, "Agregar Nuevo Estudiante", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 10, 15));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelDatos.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("RUT:"));
        txtRut = new JTextField();
        panelDatos.add(txtRut);

        panelDatos.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField();
        panelDatos.add(txtCorreo);

        panelDatos.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelDatos.add(txtTelefono);

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().isBlank() || txtCorreo.getText().isBlank() || txtTelefono.getText().isBlank() || txtRut.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
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

    /**
     * Construye el formulario para la edición de un estudiante existente.
     * Reutiliza la estructura visual del constructor principal y rellena los campos de texto con la información ya existente.
     *
     * @param parent     La ventana principal será padre de este diálogo.
     * @param estudiante El objeto estudiante el cual se editará y se usarán sus datos para rellenar el diálogo.
     */
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

    /**
     * Verifica si el usuario completó el formulario y presionó el botón de guardar.
     *
     * @return true si los datos fueron guardados, false si se canceló.
     */
    public boolean isGuardado() {return guardado;}

    /**
     * Obtiene el nombre ingresado en el formulario.
     *
     * @return Una cadena de texto con el nombre del estudiante, sin espacios adicionales.
     */
    public String getNombre() {return txtNombre.getText().trim();}

    /**
     * Obtiene el correo electrónico ingresado en el formulario.
     *
     * @return Una cadena de texto con el correo electrónico del estudiante.
     */
    public String getCorreo()  {return txtCorreo.getText().trim();}

    /**
     * Obtiene el número de teléfono ingresado en el formulario.
     *
     * @return Una cadena de texto con el teléfono del estudiante.
     */
    public String getTelefono() {return txtTelefono.getText().trim();}

    /**
     * Obtiene el RUT ingresado en el formulario.
     *
     * @return Una cadena de texto con el RUT del estudiante.
     */
    public String getRUT() {return txtRut.getText().trim();}
}