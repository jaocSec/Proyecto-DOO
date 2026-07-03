package interfaz;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame{
    private PanelMenu panelMenu;
    private JPanel panelContenido;
    private PanelCalendario panelCalendario;

    public VentanaPrincipal(){
        setTitle("Sistema de Reserva de Clases Particulares");
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelMenu= new PanelMenu();
        add(panelMenu, BorderLayout.WEST);

        panelContenido= new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);

        panelMenu.getBtnTutores().addActionListener(e-> cambiarPanel(new PanelTutores()));
        panelMenu.getBtnEstudiantes().addActionListener(e-> cambiarPanel(new PanelEstudiantes()));
        panelMenu.getBtnCalendario().addActionListener(e-> cambiarPanel(new PanelCalendario()));
    }

    private void cambiarPanel(JPanel nuevoPanel){
        panelContenido.removeAll();

        panelContenido.add(nuevoPanel, BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }


}
