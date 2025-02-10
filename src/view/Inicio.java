package view;

import javax.swing.*;

import components.CustomButton;
import controller.SalaController;
import model.Sala;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Inicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel panelBase;  
    private SalaController controlador;
    private MesasView mesasView;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;

    public Inicio(SalaController controlador, MesasView mesasView, JTabbedPane tabbedPane) {
        this.controlador = controlador;
        this.mesasView = mesasView;
        this.tabbedPane = tabbedPane;

        initComponents();
        cargarSalasEnPanel();
    }

    private void initComponents() {
        setSize(1100, 600);
        setLayout(new BorderLayout()); 

        panelBase = new JPanel();
        panelBase.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Espaciado entre botones
        
        scrollPane = new JScrollPane(panelBase);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Habilita scroll horizontal
        scrollPane.setPreferredSize(new Dimension(1050, 100));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarSalasEnPanel() {
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            agregarSalaPanel(sala);
        }
    }

    public void agregarSalaPanel(Sala sala) {
        // Crear un nuevo CustomButton
        CustomButton salaButton = new CustomButton();
        
        // Cargar la imagen
        ImageIcon imagenSala = new ImageIcon(getClass().getResource("/imgs/sala.png")); // Cambia la ruta a la ubicación de tu imagen
        Image img = imagenSala.getImage();
        Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // Ajusta el tamaño de la imagen
        ImageIcon iconoEscalado = new ImageIcon(newImg);
        
        // Establecer el icono de la imagen
        salaButton.setIcon(iconoEscalado);
        
        // Establecer el texto debajo de la imagen
        salaButton.setText(sala.getNombre());
        salaButton.setFont(new Font("Arial", Font.BOLD, 15));
        salaButton.setPreferredSize(new Dimension(200, 500)); 
        salaButton.setBackground(Color.BLACK);
        salaButton.setForeground(Color.WHITE);
        salaButton.setShadowColor(new java.awt.Color(169, 169, 169,255));
        salaButton.setShadowBlurRadius(10);  // Ajusta el radio de difuminado de la sombra
        salaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salaButton.setRound(30);
        
        // Usar un BoxLayout para apilar la imagen sobre el texto
        salaButton.setLayout(new BoxLayout(salaButton, BoxLayout.Y_AXIS)); // Layout vertical (imagen arriba, texto abajo)
        salaButton.setHorizontalAlignment(SwingConstants.CENTER); // Centra la imagen y el texto
        
        // La imagen debería tener un tamaño fijo
        salaButton.setIconTextGap(10);

        // Agregar acción al botón
        salaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedComponent(mesasView);
            }
        });

        // Agregar el botón al panel
        panelBase.add(salaButton);
        panelBase.setBackground(SystemColor.textHighlightText);
        panelBase.revalidate();
        panelBase.repaint();
    }


    public void eliminarSalaDePanel(String nombreSala) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreSala)) {
                    panelBase.remove(button);
                    panelBase.revalidate();
                    panelBase.repaint();
                    break;
                }
            }
        }
    }

    public void actualizarSalaPanel(String nombreAntiguo, String nuevoNombre) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreAntiguo)) { 
                    button.setText(nuevoNombre);  
                    panelBase.revalidate();  
                    panelBase.repaint();  
                    break;
                }
            }
        }
    }
}
