package interfaz;

import javax.swing.*;
import java.awt.*;

public class DetalleReservas extends JDialog {

    //AGREGAR RESERVA!!
    public DetalleReservas(Window parent) {

        //Ventana
        super(parent, "Detalles Reserva", Dialog.ModalityType.APPLICATION_MODAL);

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        //Panel Central
        JPanel panelDatos = new JPanel(new GridLayout(6, 2, 10, 15));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }
}