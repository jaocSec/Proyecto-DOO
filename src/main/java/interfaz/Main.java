package interfaz;

import javax.swing.*;
import java.awt.Color;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args){

        try {
            UIManager.put("Button.background", new Color(33, 37, 41));
            UIManager.put("Button.foreground", new Color(242, 239, 225));
            UIManager.put("Button.focusWidth", 0);

            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("Error: Flatlaf");
        }


        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana= new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
