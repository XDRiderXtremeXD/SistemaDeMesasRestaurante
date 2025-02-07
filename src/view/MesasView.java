package view;

import javax.swing.*;

import components.CustomButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import controller.SalaController;
import model.Sala;

public class MesasView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelBase;
    private SalaController controlador;
    private JTabbedPane tabbedPane;
    private RealizarPedidoView realizarPedidoView;  // Asegúrate de que tienes esta clase
    private int cantidadMesas;
    private JScrollPane scrollPane;

    public MesasView(SalaController controlador, JTabbedPane tabbedPane, RealizarPedidoView realizarPedidoView) {
        this.controlador = controlador;
        this.tabbedPane = tabbedPane;
        this.realizarPedidoView = realizarPedidoView;
        initComponents();
        cargarMesasPanel();
    }

    private void initComponents() {
        setSize(1100, 600);
        setLayout(new BorderLayout());

        // Usamos GridLayout para organizar los botones en filas y columnas
        panelBase = new JPanel();
        panelBase.setLayout(new GridLayout(0, 5, 15, 15));  // Filas dinámicas, 5 columnas, 15px de espacio

        // Creamos el JScrollPane para permitir desplazamiento en el panel de mesas
        scrollPane = new JScrollPane(panelBase);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Habilita scroll horizontal
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // Habilita s

        // Añadimos el JScrollPane al panel principal
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarMesasPanel() {
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            agregarMesasPanel(sala.getMesas());
        }
    }

    public void agregarMesasPanel(int cantidad) {
        panelBase.removeAll();  // Limpiar cualquier mesa anterior
        this.cantidadMesas = cantidad;  // Actualizar la cantidad de mesas

        int posX = 10;  // Posición inicial en X
        int posY = 10;  // Posición inicial en Y
        int espacio = 15;  // Espacio entre botones
        int columnas = 5;  // Número de columnas por fila
        int anchoBoton = 240;  // Ancho de cada botón
        int altoBoton = 240;  // Alto de cada botón

        for (int i = 1; i <= cantidadMesas; i++) {
            CustomButton btnMesa = new CustomButton(); 
            
            // Cargar la imagen
            ImageIcon imagenMesa = new ImageIcon(getClass().getResource("/imgs/mesa.png")); // Cambia la ruta a la ubicación de tu imagen
            Image img = imagenMesa.getImage();
            Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // Ajusta el tamaño de la imagen
            ImageIcon iconoEscalado = new ImageIcon(newImg);
            
            // Establecer la imagen en el botón
            btnMesa.setIcon(iconoEscalado);
            
            // Establecer el texto debajo de la imagen
            btnMesa.setText("Mesa " + i);
            btnMesa.setFont(new Font("Arial", Font.BOLD, 15));
            btnMesa.setPreferredSize(new Dimension(anchoBoton, altoBoton)); // Tamaño uniforme
            btnMesa.setBackground(new Color(0, 100, 0)); // Verde oscuro
            btnMesa.setForeground(Color.WHITE);
            btnMesa.setShadowColor(new java.awt.Color(0, 0, 0,255));
            btnMesa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnMesa.setRound(360);
            
            // Usar un BoxLayout para apilar la imagen sobre el texto
            btnMesa.setLayout(new BoxLayout(btnMesa, BoxLayout.Y_AXIS)); // Layout vertical (imagen arriba, texto abajo)
            btnMesa.setHorizontalAlignment(SwingConstants.CENTER); // Centrar la imagen y el texto
            btnMesa.setIconTextGap(10); // Espacio entre la imagen y el texto

            // Agregar acción al botón
            btnMesa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabbedPane.setSelectedComponent(realizarPedidoView);  // Cambiar a la vista de platos
                }
            });

            // Agregar el botón al panel
            panelBase.add(btnMesa);  

            // Actualizar la posición para el siguiente botón
            posX += anchoBoton + espacio;  // Moverse a la siguiente columna

            // Si hemos llegado a la cantidad de columnas, movemos a la siguiente fila
            if (i % columnas == 0) {
                posX = 10;  // Resetear la posición X
                posY += altoBoton + espacio;  // Moverse a la siguiente fila
            }
        }

        panelBase.revalidate();  // Actualizar el panel de mesas
        panelBase.repaint();  // Redibujar el panel
    }




    // Método para actualizar las mesas (por ejemplo, cambiar nombre o características)
    public void actualizarMesaPanel(String nombreAntiguo, String nuevoNombre) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreAntiguo)) {  // Busca la mesa con el nombre antiguo
                    button.setText(nuevoNombre);  // Actualiza el nombre de la mesa
                    panelBase.revalidate();  // Revalidar para actualizar el diseño
                    panelBase.repaint();  // Vuelve a pintar el panel
                    break;
                }
            }
        }
    }

    // Método para eliminar una mesa
    public void eliminarMesaDePanel(String nombreMesa) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreMesa)) {  // Si encontramos la mesa por su nombre
                    panelBase.remove(button);  // Eliminar el botón de la mesa
                    panelBase.revalidate();  // Revalidar para actualizar el diseño
                    panelBase.repaint();  // Vuelve a pintar el panel
                    break;
                }
            }
        }
    }
}
