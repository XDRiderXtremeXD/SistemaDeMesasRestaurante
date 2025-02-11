package view;

import javax.swing.*;
import components.CustomButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.*;
import controller.PlatoController;
import controller.SalaController;
import model.Sala;

public class MesasView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelBase;
    private SalaController controlador;
    private JTabbedPane tabbedPane;
    private RealizarPedidoView realizarPedidoView;
    private PlatoController platoController;
    private JScrollPane scrollPane;

    public MesasView(SalaController controlador, JTabbedPane tabbedPane, RealizarPedidoView realizarPedidoView) {
        this.controlador = controlador;
        this.tabbedPane = tabbedPane;
        this.realizarPedidoView = realizarPedidoView;
        initComponents();
        cargarMesasPanel();
    }

    private void initComponents() {
        platoController = new PlatoController();
        setSize(1100, 600);
        setLayout(new BorderLayout());

        // Usamos GridLayout para organizar los botones en filas y columnas
        panelBase = new JPanel();
        panelBase.setLayout(new GridLayout(0, 5, 15, 15));  // Filas dinámicas, 5 columnas, 15px de espacio

        // Creamos el JScrollPane para permitir desplazamiento en el panel de mesas
        scrollPane = new JScrollPane(panelBase);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Habilita scroll horizontal
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  

        // Añadimos el JScrollPane al panel principal
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarMesasPanel() {
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            agregarMesasPanel(sala.getIdSala(), sala.getMesas());
        }
    }

    public void agregarMesasPanel(final int salaId, int cantidad) {
        panelBase.removeAll();  // Limpia los botones existentes
        int anchoBoton = 240;  // Ancho de cada botón
        int altoBoton = 240;   // Alto de cada botón

        for (int i = 1; i <= cantidad; i++) {
            final int mesaNumber = i;

            CustomButton btnMesa = new CustomButton(); 
                
            // Cargar y ajustar la imagen
            ImageIcon imagenMesa = new ImageIcon(getClass().getResource("/imgs/mesa.png"));
            Image img = imagenMesa.getImage();
            Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(newImg);
                
            // Configurar el botón
            btnMesa.setIcon(iconoEscalado);
            btnMesa.setText("Mesa " + mesaNumber);
            btnMesa.setFont(new Font("Arial", Font.BOLD, 15));
            btnMesa.setPreferredSize(new Dimension(anchoBoton, altoBoton));
            btnMesa.setBackground(new Color(0, 100, 0));
            btnMesa.setForeground(Color.WHITE);
            btnMesa.setShadowColor(new Color(0, 0, 0, 255));
            btnMesa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnMesa.setRound(360);
            btnMesa.setLayout(new BoxLayout(btnMesa, BoxLayout.Y_AXIS));
            btnMesa.setHorizontalAlignment(SwingConstants.CENTER);
            btnMesa.setIconTextGap(10);

            // Al hacer clic en la mesa, se pasa el salaId y el número de mesa a RealizarPedidoView
            btnMesa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    realizarPedidoView.listar(platoController.listar());
                    realizarPedidoView.setDatosPedido(salaId, mesaNumber);
                    tabbedPane.setSelectedComponent(realizarPedidoView);
                }
            });

            panelBase.add(btnMesa);
        }

        panelBase.revalidate();
        panelBase.repaint();
    }

    // Método para actualizar las mesas (por ejemplo, cambiar nombre o características)
    public void actualizarMesaPanel(String nombreAntiguo, String nuevoNombre) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreAntiguo)) {  // Busca la mesa con el nombre antiguo
                    button.setText(nuevoNombre);  // Actualiza el nombre de la mesa
                    panelBase.revalidate();  // Revalida para actualizar el diseño
                    panelBase.repaint();       // Vuelve a pintar el panel
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
                    panelBase.remove(button);  // Elimina el botón de la mesa
                    panelBase.revalidate();    // Revalida para actualizar el diseño
                    panelBase.repaint();       // Vuelve a pintar el panel
                    break;
                }
            }
        }
    }
    
    public void actualizarNumeroMesas(final int salaId, int nuevaCantidad) {
        panelBase.removeAll();  // Remueve todos los botones existentes
        int anchoBoton = 240;   // Ancho de cada botón
        int altoBoton = 240;    // Alto de cada botón

        for (int i = 1; i <= nuevaCantidad; i++) {
            final int mesaNumber = i;

            CustomButton btnMesa = new CustomButton(); 

            // Cargar y ajustar la imagen
            ImageIcon imagenMesa = new ImageIcon(getClass().getResource("/imgs/mesa.png"));
            Image img = imagenMesa.getImage();
            Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(newImg);

            // Configurar el botón
            btnMesa.setIcon(iconoEscalado);
            btnMesa.setText("Mesa " + mesaNumber);
            btnMesa.setFont(new Font("Arial", Font.BOLD, 15));
            btnMesa.setPreferredSize(new Dimension(anchoBoton, altoBoton));
            btnMesa.setBackground(new Color(0, 100, 0));
            btnMesa.setForeground(Color.WHITE);
            btnMesa.setShadowColor(new Color(0, 0, 0, 255));
            btnMesa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnMesa.setRound(360);
            btnMesa.setLayout(new BoxLayout(btnMesa, BoxLayout.Y_AXIS));
            btnMesa.setHorizontalAlignment(SwingConstants.CENTER);
            btnMesa.setIconTextGap(10);

            // Al hacer clic en la mesa, se pasan los datos a RealizarPedidoView
            btnMesa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    realizarPedidoView.listar(platoController.listar());
                    realizarPedidoView.setDatosPedido(salaId, mesaNumber);
                    tabbedPane.setSelectedComponent(realizarPedidoView);
                }
            });

            panelBase.add(btnMesa);
        }
        
        panelBase.revalidate();
        panelBase.repaint();
    }
}
