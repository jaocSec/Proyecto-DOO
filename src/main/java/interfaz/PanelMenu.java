package interfaz;
import javax.swing.*;
import java.awt.*;


/**
 * Panel del menú lateral de navegación de la aplicación.
 * Contiene los botones que permiten al usuario alternar entre las distintas pestañas del sistema (Inicio, Tutores, Estudiantes, Reservas y Configuración).
 */
public class PanelMenu extends JPanel {
    public JButton btnInicio= new JButton("Inicio");
    public JButton btnTutores= new JButton("Tutores");
    public JButton btnEstudiantes= new JButton("Estudiantes");
    public JButton btnReservas= new JButton("Reservas");
    public JButton btnConfiguracion = new JButton("Configuración");

    /**
     * Construye el panel de navegación lateral.
     * Define el color de fondo y configura las restricciones del diseño (GridBagConstraints) para que los botones ocupen el espacio horizontalmente y se vean simetricos.
     */
    public PanelMenu(){
        setBackground(new Color(43, 43, 43));
        setPreferredSize(new Dimension(125, 0));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc= new GridBagConstraints();

        gbc.gridx= 0;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.weightx= 1.0;

        gbc.insets= new Insets(10,10,10,10);
        gbc.ipady= 40;

        add(btnInicio, gbc);
        add(btnTutores, gbc);
        add(btnEstudiantes, gbc);
        add(btnReservas, gbc);
        add(btnConfiguracion, gbc);
    }

    /**
     * Obtiene el botón de navegación hacia la vista de Inicio.
     *
     * @return El componente {@link JButton} de Inicio.
     */
    public JButton getBtnInicio(){return btnInicio;}

    /**
     * Obtiene el botón de navegación hacia la vista de Tutores.
     *
     * @return El componente {@link JButton} de Tutores.
     */
    public JButton getBtnTutores(){return btnTutores;}

    /**
     * Obtiene el botón de navegación hacia la vista de Estudiantes.
     *
     * @return El componente {@link JButton} de Estudiantes.
     */
    public JButton getBtnEstudiantes(){return btnEstudiantes;}

    /**
     * Obtiene el botón de navegación hacia la vista de Reservas (Calendario).
     *
     * @return El componente {@link JButton} de Reservas.
     */
    public JButton getBtnCalendario(){return btnReservas;}

    /**
     * Obtiene el botón de navegación hacia la vista de Configuración.
     *
     * @return El componente {@link JButton} de Configuración.
     */
    public JButton getBtnConfiguracion(){return btnConfiguracion;}
}
