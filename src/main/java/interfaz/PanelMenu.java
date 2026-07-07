package interfaz;
import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {
    public JButton btnInicio= new JButton("Inicio");
    public JButton btnTutores= new JButton("Tutores");
    public JButton btnEstudiantes= new JButton("Estudiantes");
    public JButton btnReservas= new JButton("Reservas");
    public JButton btnConfiguracion = new JButton("Configuración");

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

    public JButton getBtnInicio(){return btnInicio;}
    public JButton getBtnTutores(){return btnTutores;}
    public JButton getBtnEstudiantes(){return btnEstudiantes;}
    public JButton getBtnCalendario(){return btnReservas;}
    public JButton getBtnConfiguracion(){return btnConfiguracion;}
}
