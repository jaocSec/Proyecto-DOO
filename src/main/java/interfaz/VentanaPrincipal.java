package interfaz;

import logica.Controlador;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación que actúa como el contenedor del sistema.
 * Esta clase hereda de {@link JFrame} y es responsable de inicializar la lógica central mediante la instanciación del {@link Controlador}.
 * Gestiona el evento de cierre de la aplicación para garantizar la persistencia segura de los datos.
 */
public class VentanaPrincipal extends JFrame{
    private Controlador controlador;
    private PanelMenu panelMenu;
    private JPanel panelContenido;
    private PanelReservas panelReservas;

    /**
     * Construye e inicializa la ventana principal de la interfaz.
     * Configura las dimensiones mínimas, el título y el comportamiento de cierre, cambiando la acción de cerrar la ventana para invocar el guardado de datos antes de terminar la ejecución.
     * Mapea cada botón del menú lateral a su vista correspondiente.
     */
    public VentanaPrincipal(){
        controlador = new Controlador();

        setTitle("Sistema de Reserva de Clases Particulares");
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//Guardado de datos
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controlador.guardarDatos();
                System.exit(0);
            }
        });
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

        panelMenu.getBtnConfiguracion().addActionListener(e ->
                cambiarPanel(new PanelConfiguracion(controlador)));
    }

    private void cambiarPanel(JPanel nuevoPanel){
        panelContenido.removeAll();

        panelContenido.add(nuevoPanel, BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }


}
