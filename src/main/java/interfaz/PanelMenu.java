package interfaz;
import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {
    public JButton btnTutores= new JButton("Tutores");
    public JButton btnReservas= new JButton("Reservas");
    public JButton btnCalendario= new JButton("Calendario");

    public PanelMenu(){
        setBackground(new Color(33, 37,41));
        setLayout(new GridLayout(6, 1 ,10 ,10));

        add(btnTutores);
        add(btnReservas);
        add(btnCalendario);
    }
}
