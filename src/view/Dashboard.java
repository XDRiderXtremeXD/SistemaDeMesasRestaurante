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
import controller.UsuarioController;
import model.Usuario;

public class Dashboard extends JFrame implements ActionListener {
    private Usuario usuario;
    private PlatoController platoController;
    private SalaController salaController;
    private UsuarioController usuarioController;

    private SalasView salasView;
    private MesasView mesasView;
    private Inicio inicioView;
    
    private CartaDelDiaView cartaDelDiaView;
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
    private JPanel FooterOptions;

    public Dashboard(Usuario usuario) {
        // Asignar el usuario actual
        this.usuario = usuario;
        
        // Inicializar controladores
        platoController = new PlatoController();
        usuarioController = new UsuarioController();
        salaController = new SalaController();
        
        // Configurar la ventana principal
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bienvenido al Portal");
        
        // Configurar el icono de la aplicación
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/imgs/LogoIcon.png"));
        setIconImage(logoIcon.getImage());
        setLocationRelativeTo(null);
        
        // Establecer el diseño de la ventana
        getContentPane().setBackground(SystemColor.control);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        // Crear el panel de cabecera
        JPanel HeaderImage = new JPanel();
        getContentPane().add(HeaderImage, BorderLayout.NORTH);
        
        // Agregar imagen de cabecera
        JLabel lblHeaderImage = new JLabel("");
        lblHeaderImage.setIcon(new ImageIcon(Dashboard.class.getResource("/imgs/fondo.png")));
        HeaderImage.add(lblHeaderImage);
        
        lblHeaderImage.setBackground(SystemColor.activeCaption);
        lblHeaderImage.setForeground(Color.BLACK);
        
        // Panel principal que contiene el contenido central y el footer
        JPanel Main = new JPanel();
        getContentPane().add(Main, BorderLayout.CENTER);
        Main.setLayout(new BorderLayout(0, 0));
        
        // Panel de opciones en el pie de la ventana
        FooterOptions = new JPanel();
        Main.add(FooterOptions, BorderLayout.SOUTH);
        
        // Panel de contenido central
        JPanel Contenido = new JPanel();
        Main.add(Contenido, BorderLayout.CENTER);
        Contenido.setLayout(new CardLayout(0, 0));
        
        // Crear el TabbedPane para la navegación
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        Contenido.add(tabbedPane, "name_11813298604300");
        
        // Inicializar las diferentes vistas dentro de las pestañas
        mesasView = new MesasView(salaController, tabbedPane, realizarPedidoView);
        tabbedPane.addTab("Mesas", null, mesasView, null);
        
        inicioView = new Inicio(salaController, mesasView, tabbedPane);
        tabbedPane.addTab("Inicio", null, inicioView, null);
        
        salasView = new SalasView(salaController, inicioView, mesasView);
        tabbedPane.addTab("Salas", null, salasView, null);
        
        cartaDelDiaView = new CartaDelDiaView(platoController.listar());
        tabbedPane.addTab("Carta del día", null, cartaDelDiaView, null);
        
        usuariosView = new UsuariosView(usuarioController.listar());
        tabbedPane.addTab("Usuarios", null, usuariosView, null);
        
        pedidosActualesView = new PedidosActualesView(true, true, false);
        tabbedPane.addTab("Pedidos Actuales", null, pedidosActualesView, null);
        
        historialPedidoView = new HistorialPedidoView();
        tabbedPane.addTab("Historial Pedidos", null, historialPedidoView, null);
        
        finalizarPedidoView = new FinalizarPedidoView();
        tabbedPane.addTab("Detalle Pedido", null, finalizarPedidoView, null);
        
        realizarPedidoView = new RealizarPedidoView(platoController.listar());
        tabbedPane.addTab("Realizar Pedidos", null, realizarPedidoView, null);
        
        // Configurar los botones de navegación en el pie de página
        FooterOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Dimension buttonSize = new Dimension(150, 40);
        
        btnInicio = createButton("Inicio", buttonSize, FooterOptions);
        btnPedidos = createButton("Pedidos", buttonSize, FooterOptions);
        btnSalas = createButton("Salas", buttonSize, FooterOptions);
        btnHistorialPedidos = createButton("Historial Pedidos", buttonSize, FooterOptions);
        btnCartaDelDia = createButton("Carta del día", buttonSize, FooterOptions);
        btnUsuarios = createButton("Usuarios", buttonSize, FooterOptions);
        
        // Deshabilitar todas las pestañas inicialmente
        for (int i = 0; i < tabbedPane.getTabCount(); i++)
            tabbedPane.setEnabledAt(i, false);
        
        // Seleccionar la pestaña de inicio por defecto
        tabbedPane.setSelectedComponent(inicioView);
        btnInicio.setBackground(new java.awt.Color(24, 86, 56));
        
        // Hacer visible la ventana
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
			cartaDelDiaView.listar(platoController.listar());
			cartaDelDiaView.valoresIniciales();
            tabbedPane.setSelectedComponent(cartaDelDiaView);
        } else if (sourceButton == btnSalas) {
        	tabbedPane.setSelectedComponent(salasView);
        } else if (sourceButton == btnPedidos) {
        	tabbedPane.setSelectedComponent(pedidosActualesView);
        } else if (sourceButton == btnHistorialPedidos) {
        	tabbedPane.setSelectedComponent(historialPedidoView);
        } else if (sourceButton == btnUsuarios) {
			usuariosView.listar(usuarioController.listar());
			usuariosView.valoresIniciales();
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
