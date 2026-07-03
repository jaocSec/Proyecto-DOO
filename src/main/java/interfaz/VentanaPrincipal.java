package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame{
    private PanelMenu panelMenu;
    private PanelCalendario panelCalendario;

    public VentanaPrincipal(){
        setTitle("SRCP");
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelMenu= new PanelMenu();
        panelCalendario= new PanelCalendario();

        add(panelMenu, BorderLayout.WEST);
        add(panelCalendario, BorderLayout.CENTER);
    }
}
