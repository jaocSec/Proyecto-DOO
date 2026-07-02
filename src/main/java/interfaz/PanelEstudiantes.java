package interfaz;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelEstudiantes extends JPanel {
    private DefaultListModel<String> modeloLista;
    private JList<String> listaEstudiantes;
    private JButton btnAgregar, btnEditar, btnEliminar;

    //Componentes panel derecho
    private JLabel lblAvatar;
    private JLabel lblNombre;
    private JTextArea txtInformacion;
    private JButton btnBuscarTutor;

    public PanelEstudiantes() {
        setLayout(new BorderLayout());

        //Panel Lista (izquierdo)
        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        //Panel BotonesLista
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 0));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        //Lista de tutores
        modeloLista = new DefaultListModel<>(); //Lista con nombres
        modeloLista.addElement("John Wick"); //Ejemplo
        modeloLista.addElement("Jack Reacher"); //Ejemplo
        modeloLista.addElement("Jack Ryan"); //Ejemplo

        listaEstudiantes = new JList<>(modeloLista); //Lista gráfica
        listaEstudiantes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollLista = new JScrollPane(listaEstudiantes);

        panelIzquierdo.add(panelBotones, BorderLayout.NORTH);
        panelIzquierdo.add(scrollLista, BorderLayout.CENTER);


        //Panel detalles (derecho)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Nombre y foto
        JPanel panelHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));


        ImageIcon iconoOriginal = new ImageIcon("src/imagenes/perfil.png"); //no funciona, arreglar
        Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon iconoFinal = new ImageIcon(imagenRedimensionada);

        lblAvatar = new JLabel(iconoFinal);
        lblAvatar.setFont(new Font("Segoe UI", Font.PLAIN, 60));

        lblNombre = new JLabel("Seleccione un estudiante");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));

        panelHeader.add(lblAvatar);
        panelHeader.add(lblNombre);

        //Información panel derecho
        txtInformacion = new JTextArea("Seleccione estudiante de la lista para ver sus detalles.");
        txtInformacion.setEditable(false);
        txtInformacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInformacion.setOpaque(false);
        txtInformacion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnBuscarTutor = new JButton("Buscar Tutor Compatible");
        btnBuscarTutor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscarTutor.setPreferredSize(new Dimension(250, 40));
        btnBuscarTutor.setEnabled(false);
        panelBoton.add(btnBuscarTutor);

        panelDerecho.add(panelHeader, BorderLayout.NORTH);
        panelDerecho.add(txtInformacion, BorderLayout.CENTER);
        panelDerecho.add(panelBoton, BorderLayout.SOUTH);


        //Listener click de la lista
        listaEstudiantes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String seleccionado = listaEstudiantes.getSelectedValue();
                    if (seleccionado != null) {
                        actualizarDetalles(seleccionado);
                    }
                }
            }
        });



        //Divisor
        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
        divisor.setDividerLocation(450);
        divisor.setDividerSize(5);
        divisor.setBorder(null);

        add(divisor, BorderLayout.CENTER);
    }

    private void actualizarDetalles(String nombre) {
        lblNombre.setText(nombre);
        txtInformacion.setText(
                //Ejemplo
                "\nMaterias impartidas: \n   - Matemáticas\n   - Física\n\n" +
                        "Tarifa por hora: \n   - $15.000\n\n" +
                        "Capacidad máxima por materia: \n   - 5 estudiantes\n\n" +
                        "Bloques de disponibilidad: \n   - Lunes a Miércoles (14:00 - 18:00)"
        );
    }
}