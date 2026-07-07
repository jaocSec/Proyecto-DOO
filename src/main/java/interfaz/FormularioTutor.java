package interfaz;

import logica.Controlador;
import javax.swing.*;
import java.awt.*;

import logica.modelos.Tutor;
import logica.*;

/**
 * Cuadro de diálogo que contiene un formulario para gestionar tutores.
 * Permite tanto el registro de nuevos tutores y la edición de los datos de tutores ya existentes.
 */
public class FormularioTutor extends JDialog {
    private Controlador controlador;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTarifa;
    private JTextField txtRut;
    private JComboBox<String> cbMateria;
    private JComboBox<String> cbHorario;
    private JTextField txtCupo;
    private boolean guardado = false;

    /**
     * Construye el formulario para la creación de un nuevo tutor.
     * Carga las opciones de materias y horarios disponibles en los catálogos {@link Catalogos}
     *
     * @param parent La ventana principal que será padre de este diálogo.
     * @param controlador El gestor de la lógica para obtener los catálogos.
     */
    public FormularioTutor(Window parent, Controlador controlador) {
        super(parent, "Agregar Nuevo Tutor", Dialog.ModalityType.APPLICATION_MODAL);
        this.controlador= controlador;


        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(0, 2, 10, 15));
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
        panelDatos.add(new JLabel("Horario: "));
        cbHorario = new JComboBox<>(controlador.getCatalogo().getHorarios().toArray(new String[0]));
        panelDatos.add(cbHorario);

        panelDatos.add(new JLabel("Cupo:"));
        txtCupo = new JTextField("0");
        panelDatos.add(txtCupo);

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

    /**
     * Construye el formulario para la edición de un tutor existente.
     * Reutiliza la estructura visual del constructor principal y rellena los campos de texto con la información ya existente.
     * Deshabilita los campos de materia y horario, ya que estos se gestionan en ventanas especificas.
     *
     * @param parent     La ventana principal que será padre de este diálogo.
     * @param controlador El gestor principal de la lógica.
     * @param tutor El objeto tutor el cual se editará y se usarán sus datos para rellenar el diálogo.
     */
    public FormularioTutor(Window parent, Controlador controlador, Tutor tutor) {
        this(parent, controlador);
        setTitle("Editar Tutor");
        txtNombre.setText(tutor.getNombre());
        txtRut.setText(tutor.getRUT());
        txtCorreo.setText(tutor.getCorreo());
        txtTarifa.setText(String.valueOf(tutor.getTarifaHora()));

        cbMateria.setEnabled(false);
        cbHorario.setEnabled(false);
        txtCupo.setEnabled(false);
        txtCupo.setText("");
    }

    /**
     * Verifica si el usuario completó el formulario y confirmó el guardado.
     *
     * @return true si los datos pasaron la validación y se guardaron, false si se canceló.
     */
    public boolean isGuardado() {return guardado;}

    /**
     * Obtiene el nombre ingresado en el formulario.
     *
     * @return Una cadena de texto con el nombre del tutor.
     */
    public String getNombre() {return txtNombre.getText().trim();}

    /**
     * Obtiene el correo ingresado en el formulario.
     *
     * @return Una cadena de texto con el correo del tutor.
     */
    public String getCorreo() {return txtCorreo.getText().trim();}

    /**
     * Obtiene la tarifa ingresada en el formulario.
     *
     * @return El valor numérico de la tarifa por hora.
     */
    public double getTarifa() {return Double.parseDouble(txtTarifa.getText().trim());}

    /**
     * Obtiene la materia seleccionada al registrar el tutor.
     *
     * @return Una cadena de texto con el nombre de la materia seleccionada.
     */
    public String getMateria() {return (String) cbMateria.getSelectedItem();}

    /**
     * Obtiene el cupo máximo asignado para la primera materia del tutor.
     *
     * @return El número entero que representa el cupo para la materia.
     */
    public String getHorario() { return (String) cbHorario.getSelectedItem(); }
    public int getCupo() {
        try {
            return Integer.parseInt(txtCupo.getText().trim());
        } catch (NumberFormatException e) {
            return 5;
        }
    }

    /**
     * Obtiene el RUT ingresado en el formulario.
     *
     * @return Una cadena de texto con el RUT del tutor.
     */
    public String getRUT() {return txtRut.getText().trim();}
}