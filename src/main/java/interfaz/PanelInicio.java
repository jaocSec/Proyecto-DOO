package interfaz;

import logica.Controlador;
import logica.observer.InterfazObserver;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel implements InterfazObserver {
    private Controlador controlador;
    private JPanel panelEstadisticas;

    public PanelInicio(Controlador controlador) {

        this.controlador = controlador;
        controlador.agregarObservador(this);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        //titulo
        JLabel lblBienvenida = new JLabel("Resumen del Sistema de Reservas");
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblBienvenida, BorderLayout.NORTH);

        //Panel estadisticas
        panelEstadisticas = new JPanel(new GridLayout(1, 3, 20, 0));

        //PanelResumen
        actualizarResumen();

        add(panelEstadisticas, BorderLayout.CENTER);

        //espacio vacío
        add(new JPanel(), BorderLayout.SOUTH);
    }

    private void actualizarResumen() {

        panelEstadisticas.removeAll();

        panelEstadisticas.add(crearResumen(
                "Tutores Registrados",
                String.valueOf(controlador.getTutores().size())
        ));

        panelEstadisticas.add(crearResumen(
                "Estudiantes Registrados",
                String.valueOf(controlador.getEstudiantes().size())
        ));

        panelEstadisticas.add(crearResumen(
                "Reservas Pendientes",
                String.valueOf(controlador.getCantidadReservasPendientes())
        ));

        panelEstadisticas.revalidate();
        panelEstadisticas.repaint();
    }

    private JPanel crearResumen(String titulo, String valor) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjeta.setBackground(new Color(220, 222, 224));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTitulo.setForeground(Color.DARK_GRAY); //Decidir después

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 36));

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);

        return tarjeta;
    }
    @Override
    public void actualizar() {

        actualizarResumen();

    }
}
