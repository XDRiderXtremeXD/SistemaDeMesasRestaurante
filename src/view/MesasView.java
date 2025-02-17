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
        setBackground(new Color(240, 240, 240));

        panelBase = new JPanel();
        panelBase.setLayout(new GridLayout(0, 5, 15, 15));
        panelBase.setBackground(new Color(240, 240, 240));
        
        scrollPane = new JScrollPane(panelBase);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(new Color(240, 240, 240));

        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarMesasPanel() {
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            agregarMesasPanel(sala.getIdSala(), sala.getMesas());
        }
    }

    public void agregarMesasPanel(final int salaId, int cantidad) {
        panelBase.removeAll();
        int anchoBoton = 240;
        int altoBoton = 240;

        for (int i = 1; i <= cantidad; i++) {
            final int mesaNumber = i;

            CustomButton btnMesa = new CustomButton(); 
            
            ImageIcon imagenMesa = new ImageIcon(getClass().getResource("/imgs/mesa.png"));
            Image img = imagenMesa.getImage();
            Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(newImg);
            
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

    public void actualizarMesaPanel(String nombreAntiguo, String nuevoNombre) {
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

    public void eliminarMesaDePanel(String nombreMesa) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreMesa)) {
                    panelBase.remove(button);
                    panelBase.revalidate();
                    panelBase.repaint();
                    break;
                }
            }
        }
    }
    
    public void actualizarNumeroMesas(final int salaId, int nuevaCantidad) {
        panelBase.removeAll();
        int anchoBoton = 240;
        int altoBoton = 240;

        for (int i = 1; i <= nuevaCantidad; i++) {
            final int mesaNumber = i;

            CustomButton btnMesa = new CustomButton(); 

            ImageIcon imagenMesa = new ImageIcon(getClass().getResource("/imgs/mesa.png"));
            Image img = imagenMesa.getImage();
            Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(newImg);

            btnMesa.setIcon(iconoEscalado);
            btnMesa.setText("Mesa " + mesaNumber);
            btnMesa.setFont(new Font("Arial", Font.BOLD, 15));
            btnMesa.setPreferredSize(new Dimension(anchoBoton, altoBoton));
            btnMesa.setBackground(new Color(0, 100, 0));
            btnMesa.setForeground(Color.WHITE);
            btnMesa.setShadowColor(new Color(0, 0, 0, 255));
            btnMesa.setRound(360);
            btnMesa.setLayout(new BoxLayout(btnMesa, BoxLayout.Y_AXIS));
            btnMesa.setHorizontalAlignment(SwingConstants.CENTER);
            btnMesa.setIconTextGap(10);

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
