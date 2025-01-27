package view;

import javax.swing.*;
import java.awt.*;

public class PanelView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel contenedorSalas; // Panel donde se agregan las "salas"
    private JScrollPane scrollPane;

    public PanelView() {
        setLayout(new BorderLayout());

        // Creamos un panel contenedor para las salas
        contenedorSalas = new JPanel();
        contenedorSalas.setLayout(new BoxLayout(contenedorSalas, BoxLayout.Y_AXIS));  // Añadir en fila

        // Establecer un tamaño preferido al contenedor de salas (ajusta según tus necesidades)
        contenedorSalas.setPreferredSize(new Dimension(300, 400)); // Establece un tamaño adecuado

        // Creamos el JScrollPane para que se pueda hacer scroll
        scrollPane = new JScrollPane(contenedorSalas);
        add(scrollPane, BorderLayout.CENTER);

        // También podrías asignar un tamaño preferido al JScrollPane si es necesario:
        scrollPane.setPreferredSize(new Dimension(400, 500));
    }

    // Método para agregar una sala al PanelView
    public void agregarSalaPanel(String nombreSala) {
        // Crear el panel contenedor para la sala
        JPanel salaPanel = new JPanel();
        salaPanel.setLayout(new BorderLayout());
        salaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Etiqueta con el nombre de la sala
        JLabel salaLabel = new JLabel(nombreSala);
        salaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        salaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        salaPanel.add(salaLabel, BorderLayout.CENTER);

        // Añadir la sala al contenedor
        contenedorSalas.add(salaPanel);

        // Actualizar la interfaz para mostrar la nueva sala
        SwingUtilities.invokeLater(() -> {
            contenedorSalas.revalidate();
            contenedorSalas.repaint();
        });
    }
}
