package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

import components.CustomButton;
import controller.PlatoController;
import controller.SalaController;
import model.Usuario;

public class Dashboard extends JFrame implements ActionListener {
    private Usuario usuario;
    private PlatoController platoController;
    private SalaController salaController;

    private SalasView salasView;
    private MesasView mesasView;
    private Inicio inicioView;

    private CartaDelDiaView cartaDelDia;
    private FinalizarPedidoView finalizarPedidoView;
    private PedidosActualesView pedidosActualesView;
    private HistorialPedidoView historialPedidoView;
    private UsuariosView usuariosView;
    private RealizarPedidoView realizarPedidoView;

    // Crear botones
    private CustomButton btnCartaDelDia;
    private CustomButton btnSalas;
    private CustomButton btnPedidos;
    private CustomButton btnInicio;
    private CustomButton btnUsuarios;
    private CustomButton btnHistorialPedidos;

    private JTabbedPane tabbedPane;

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel label;

    public Dashboard(Usuario usuario) {
        getContentPane().setBackground(SystemColor.control);
        this.usuario = usuario;
    	platoController = new PlatoController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bienvenido al Portál");
        setSize(1600, 900);
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/imgs/LogoIcon.png"));
        setIconImage(logoIcon.getImage());
        setLocationRelativeTo(null);

        salaController = new SalaController();
        getContentPane().setLayout(null);

        // Definir tamaño preferido para los botones
        Dimension buttonSize = new Dimension(150, 40);

        // Panel superior (cabecera)
        JLabel headerLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        headerLabel.setBounds(10, 0, 1564, 157);
        headerLabel.setOpaque(true);

        ImageIcon headerImage = new ImageIcon(getClass().getResource("/imgs/fondo.png"));
        Image image = headerImage.getImage();
        Image scaledImage = image.getScaledInstance(headerLabel.getWidth(), headerLabel.getHeight(), Image.SCALE_SMOOTH);
        headerImage = new ImageIcon(scaledImage);
        headerLabel.setIcon(headerImage);

        headerLabel.setBackground(SystemColor.activeCaption);
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setPreferredSize(new Dimension(100, 150));
        getContentPane().add(headerLabel, BorderLayout.NORTH);
        
        // Crear el controlador
        // Panel central con pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder()); // Elimina el borde del TabbedPane

        tabbedPane.setBounds(10, 160, 1564, 575);
        tabbedPane.setBackground(SystemColor.control);
        tabbedPane.setOpaque(true); 
        usuariosView = new UsuariosView();
        tabbedPane.addTab("Usuarios", null, usuariosView, null);
        mesasView = new MesasView(salaController, tabbedPane, realizarPedidoView);
        tabbedPane.addTab("Mesas", null, mesasView, null);
        inicioView = new Inicio(salaController, mesasView, tabbedPane);
        tabbedPane.addTab("Inicio", null, inicioView, null);
        salasView = new SalasView(salaController, inicioView, mesasView);
        tabbedPane.addTab("Salas", null, salasView, null);
                
        cartaDelDia = new CartaDelDiaView(platoController.listar());
        tabbedPane.addTab("Carta del dia", null, cartaDelDia, null);
        usuariosView = new UsuariosView();
        tabbedPane.addTab("Usuarios", null, usuariosView, null);   
        pedidosActualesView = new PedidosActualesView(true,true,false);
        tabbedPane.addTab("Pedidos Actuales", null, pedidosActualesView, null);
        historialPedidoView = new HistorialPedidoView();
        tabbedPane.addTab("Historial Pedidos", null, historialPedidoView, null);
        getContentPane().add(tabbedPane);
        finalizarPedidoView = new FinalizarPedidoView();
        tabbedPane.addTab("Detalle Pedido", null, finalizarPedidoView, null);
        

           // Configurar las vistas de las pestañas
           realizarPedidoView = new RealizarPedidoView(platoController.listar());
           tabbedPane.addTab("Realizar Pedidos", null, realizarPedidoView, null);

        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(0, 746, 1574, 104);
        getContentPane().add(panel);
        panel.setLayout(null);

        // Panel lateral (botones horizontales)
        JPanel sidePanel = new JPanel();
        sidePanel.setBounds(250, 10, 1217, 60);
        panel.add(sidePanel);
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Configurar los botones y añadirlos al panel lateral
        btnInicio = createButton("Inicio", buttonSize, sidePanel);
        btnPedidos = createButton("Pedidos", buttonSize, sidePanel);
        btnSalas = createButton("Salas", buttonSize, sidePanel);
        btnHistorialPedidos = createButton("Historial Pedidos", buttonSize, sidePanel);
        btnCartaDelDia = createButton("Carta del día", buttonSize, sidePanel);
        btnUsuarios = createButton("Usuarios", buttonSize, sidePanel);
        
        label = new JLabel("New label");
        label.setBounds(653, 82, 45, 13);
        getContentPane().add(label);

        // Deshabilitar pestañas inicialmente
        for (int i = 0; i < tabbedPane.getTabCount(); i++)
            tabbedPane.setEnabledAt(i, false);

        // Hacer visible
        setVisible(true);
    }

    private CustomButton createButton(String text, Dimension size, JPanel panel) {
        CustomButton button = new CustomButton();
        button.setText(text);
        button.setPreferredSize(size);
        button.addActionListener(this);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new java.awt.Color(0, 0, 0)); // Fondo negro inicial
        button.setForeground(new java.awt.Color(245, 245, 245)); // Texto blanco grisáceo
        button.setRippleColor(new java.awt.Color(255, 255, 255)); // Color del efecto de onda
        button.setShadowColor(new java.awt.Color(18, 61, 42)); // Sombra verde oscuro
        panel.add(button);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CustomButton sourceButton = (CustomButton) e.getSource();
        resetButtonColors();

        // Cambiar el color del botón seleccionado
        sourceButton.setBackground(new java.awt.Color(24, 86, 56)); // Verde oscuro

        if (sourceButton == btnCartaDelDia) {
			cartaDelDia.listar(platoController.listar());
            tabbedPane.setSelectedComponent(cartaDelDia);
        } else if (sourceButton == btnSalas) {
            tabbedPane.setSelectedComponent(salasView);
        } else if (sourceButton == btnPedidos) {
            tabbedPane.setSelectedComponent(pedidosActualesView);
        } else if (sourceButton == btnHistorialPedidos) {
            tabbedPane.setSelectedComponent(historialPedidoView);
        } else if (sourceButton == btnUsuarios) {
            tabbedPane.setSelectedComponent(usuariosView);
        } else if (sourceButton == btnInicio) {
            tabbedPane.setSelectedComponent(inicioView);
        }
    }

    private void resetButtonColors() {
        btnInicio.setBackground(new java.awt.Color(0, 0, 0));
        btnPedidos.setBackground(new java.awt.Color(0, 0, 0));
        btnSalas.setBackground(new java.awt.Color(0, 0, 0));
        btnHistorialPedidos.setBackground(new java.awt.Color(0, 0, 0));
        btnCartaDelDia.setBackground(new java.awt.Color(0, 0, 0));
        btnUsuarios.setBackground(new java.awt.Color(0, 0, 0));
    }

    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        EventQueue.invokeLater(() -> new Dashboard(usuario));
    }
}
