package interfaz;

import logica.Controlador;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame{
    private Controlador controlador;
    private PanelMenu panelMenu;
    private JPanel panelContenido;
    private PanelReservas panelReservas;

    public VentanaPrincipal(){
        controlador = new Controlador();

        setTitle("Sistema de Reserva de Clases Particulares");
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panelMenu= new PanelMenu();
        add(panelMenu, BorderLayout.WEST);

        panelContenido= new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);

        cambiarPanel(new PanelInicio(controlador));

        panelMenu.getBtnInicio().addActionListener(e ->
                cambiarPanel(new PanelInicio(controlador)));

        panelMenu.getBtnTutores().addActionListener(e ->
                cambiarPanel(new PanelTutores(controlador)));

        panelMenu.getBtnEstudiantes().addActionListener(e ->
                cambiarPanel(new PanelEstudiantes(controlador)));

        panelMenu.getBtnCalendario().addActionListener(e ->
                cambiarPanel(new PanelReservas(controlador)));
    }

    private void cambiarPanel(JPanel nuevoPanel){
        panelContenido.removeAll();

        panelContenido.add(nuevoPanel, BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }


}
