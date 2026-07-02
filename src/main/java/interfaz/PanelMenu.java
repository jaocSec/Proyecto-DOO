package interfaz;
import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {
    public JButton btnTutores= new JButton("Tutores");
    public JButton btnEstudiantes= new JButton("Estudiantes");
    public JButton btnReservas= new JButton("Reservar");
    public JButton btnCalendario= new JButton("Calendario");

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

        add(btnTutores, gbc);
        add(btnEstudiantes, gbc);
        add(btnReservas, gbc);
        add(btnCalendario, gbc);

    }
}
