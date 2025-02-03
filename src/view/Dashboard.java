package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.PlatoController;
import controller.SalaController;

public class Dashboard extends JFrame implements ActionListener {
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
    private PlatosView platosView;
    
    // Crear botones
    private JButton btnCartaDelDia;
    private JButton btnSalas;
    private JButton btnPedidos;
    private JButton btnInicio;
    private JButton btnUsuarios;
    private JButton btnHistorialPedidos;
    
    private JTabbedPane tabbedPane;
    

    

    private static final long serialVersionUID = 1L;

    public Dashboard() {
    	System.out.println("Se creó el dashboard");
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);
        
        setLocationRelativeTo(null);

        // Usamos BorderLayout para la ventana principal
        getContentPane().setLayout(new BorderLayout());
        
        salaController = new SalaController();
        
        // Panel lateral (izquierdo)
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        getContentPane().add(sidePanel, BorderLayout.WEST);

        // Espaciado después del logo
        sidePanel.add(Box.createVerticalStrut(50));

        // Definir tamaño preferido para los botones
        Dimension buttonSize = new Dimension(150, 40); // Anchura fija y altura personalizada

        // Crear botones
        btnCartaDelDia = new JButton("Carta del dia");
        btnCartaDelDia.setPreferredSize(buttonSize);
        btnCartaDelDia.setMaximumSize(buttonSize);
        btnCartaDelDia.addActionListener(this);

        btnSalas = new JButton("Salas");
        btnSalas.setPreferredSize(buttonSize);
        btnSalas.setMaximumSize(buttonSize);
        btnSalas.addActionListener(this);

        btnPedidos = new JButton("Pedidos");
        btnPedidos.setPreferredSize(buttonSize);
        btnPedidos.setMaximumSize(buttonSize);
        btnPedidos.addActionListener(this);
        
        btnHistorialPedidos = new JButton("Historial Pedidos");
        btnHistorialPedidos.setPreferredSize(buttonSize);
        btnHistorialPedidos.setMaximumSize(buttonSize);
        btnHistorialPedidos.addActionListener(this);
        
        btnPedidos = new JButton("Pedidos Actuales");
        btnPedidos.setPreferredSize(buttonSize);
        btnPedidos.setMaximumSize(buttonSize);
        btnPedidos.addActionListener(this);

        btnInicio = new JButton("Inicio");
        btnInicio.setPreferredSize(buttonSize);
        btnInicio.setMaximumSize(buttonSize);
        btnInicio.addActionListener(this);
        
        btnUsuarios = new JButton("Usuarios");
        btnUsuarios.setPreferredSize(buttonSize);
        btnUsuarios.setMaximumSize(buttonSize);
        btnUsuarios.addActionListener(this);
        

        // Añadir botones al panel lateral con espaciado uniforme
        sidePanel.add(btnInicio);
        sidePanel.add(Box.createVerticalStrut(50));
        sidePanel.add(btnPedidos);
        sidePanel.add(Box.createVerticalStrut(50));
        sidePanel.add(btnHistorialPedidos);
        sidePanel.add(Box.createVerticalStrut(50));
        sidePanel.add(btnCartaDelDia);
        sidePanel.add(Box.createVerticalStrut(50)); // Espaciado entre botones
        sidePanel.add(btnSalas);
        sidePanel.add(Box.createVerticalStrut(50));
        sidePanel.add(btnUsuarios);
        sidePanel.add(Box.createVerticalGlue()); // Espaciado flexible para ajustar al tamaño del panel

        // Panel superior (cabecera)
        JLabel headerLabel = new JLabel("IMAGEN", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(SystemColor.activeCaption);
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setPreferredSize(new Dimension(100, 150));
        getContentPane().add(headerLabel, BorderLayout.NORTH);
        
     // Crear el controlador
        // Panel central con pestañas
        tabbedPane = new JTabbedPane();
        platosView = new PlatosView();
        tabbedPane.addTab("Platos", null, platosView, null);
        mesasView = new MesasView(salaController,tabbedPane, platosView);
        tabbedPane.addTab("Mesas", null, mesasView, null);
        inicioView = new Inicio(salaController, mesasView, tabbedPane);
        tabbedPane.addTab("Inicio", null, inicioView, null);
        salasView = new SalasView(salaController, inicioView, mesasView);
        tabbedPane.addTab("Salas", null, salasView, null);
        
        
        cartaDelDia = new CartaDelDiaView();
        tabbedPane.addTab("Carta del dia", null, cartaDelDia, null);
        platoController = new PlatoController(cartaDelDia);
        usuariosView = new UsuariosView();
        tabbedPane.addTab("Usuarios", null, usuariosView, null);   
        pedidosActualesView = new PedidosActualesView();
        tabbedPane.addTab("Pedidos Actuales", null, pedidosActualesView, null);
        historialPedidoView = new HistorialPedidoView();
        tabbedPane.addTab("Historial Pedidos", null, historialPedidoView, null);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        finalizarPedidoView = new FinalizarPedidoView();
        tabbedPane.addTab("Detalle Pedido", null, finalizarPedidoView, null);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(414, 158, 1, 2);
        finalizarPedidoView.add(separator);
        
        for (int i = 0; i < tabbedPane.getTabCount(); i++)
	        tabbedPane.setEnabledAt(i, false);
	   
        // Hacer visible
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Dashboard());
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCartaDelDia) {
            platoController.listarPlatos();
            tabbedPane.setSelectedComponent(cartaDelDia);
        	}
		if (e.getSource() == btnSalas) {
			tabbedPane.setSelectedComponent(salasView);
			}
		if (e.getSource() == btnPedidos) {
			tabbedPane.setSelectedComponent(pedidosActualesView);
			}
		if (e.getSource() == btnHistorialPedidos) {
			tabbedPane.setSelectedComponent(historialPedidoView);
			}
		if (e.getSource() == btnUsuarios) {
			tabbedPane.setSelectedComponent(usuariosView);
			}
		if (e.getSource() == btnInicio) {
			tabbedPane.setSelectedComponent(inicioView);
			}
	}
}
