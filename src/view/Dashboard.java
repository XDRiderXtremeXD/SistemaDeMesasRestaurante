package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import components.CustomAlert;
import components.CustomButton;
import controller.PlatoController;
import controller.SalaController;
import controller.UsuarioController;
import model.Usuario;

public class Dashboard extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private Usuario usuario;
    private PlatoController platoController;
    private SalaController salaController;
    private UsuarioController usuarioController;
    
    private SalasView salasView;
    private MesasView mesasView;
    private Inicio inicioView;
    private CartaDelDiaView cartaDelDiaView;
    private PedidosActualesView pedidosActualesView;
    private HistorialPedidoView historialPedidoView;
    private UsuariosView usuariosView;
    private RealizarPedidoView realizarPedidoView;

    private CustomButton btnCartaDelDia, btnSalas, btnPedidos, btnInicio, btnUsuarios, btnHistorialPedidos, btnCerrarSesion;
    private JTabbedPane tabbedPane;
    private JPanel FooterOptions;

    public Dashboard(Usuario usuario) {
        this.usuario = usuario;
        initControllers();
        initFrame();
        initComponents();
    }

    private void initControllers() {
        platoController = new PlatoController();
        usuarioController = new UsuarioController();
        salaController = new SalaController();
    }

    private void initFrame() {
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Bienvenido al Portal " + usuario.getNombreUsuario() + "!");
        setIconImage(new ImageIcon(getClass().getResource("/imgs/LogoIcon.png")).getImage());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarSesion();
            }
        });
    }

    private void initComponents() {
        JPanel headerImage = new JPanel();
        headerImage.setBackground(SystemColor.textHighlightText);
        JLabel lblHeaderImage = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("imgs/fondo.png")));
        headerImage.add(lblHeaderImage);
        getContentPane().add(headerImage, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        FooterOptions = new JPanel();
        FooterOptions.setLayout(new BoxLayout(FooterOptions, BoxLayout.X_AXIS));
        FooterOptions.setBackground(SystemColor.textHighlightText);
        mainPanel.add(FooterOptions, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(SystemColor.textHighlightText);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        initViews();
        initButtons();
    }

    private void initViews() {
        historialPedidoView = new HistorialPedidoView();
        
        if (usuario.getRol().equals("Cocinero")) 
			pedidosActualesView = new PedidosActualesView(true, false, false);
		else 
			pedidosActualesView = new PedidosActualesView(true, true, false);
        
        realizarPedidoView = new RealizarPedidoView(platoController.listar(), usuario, pedidosActualesView);
        mesasView = new MesasView(salaController, tabbedPane, realizarPedidoView);
        inicioView = new Inicio(salaController, mesasView, tabbedPane);
        salasView = new SalasView(salaController, inicioView, mesasView);
        cartaDelDiaView = new CartaDelDiaView(platoController.listar());
        usuariosView = new UsuariosView(usuarioController.listar());
        
    	tabbedPane.addTab("Inicio", null, inicioView, null);
        tabbedPane.addTab("Mesas", null, mesasView, null);
        tabbedPane.addTab("Salas", null, salasView, null);
        tabbedPane.addTab("Carta del día", null, cartaDelDiaView, null);
        tabbedPane.addTab("Usuarios", null, usuariosView, null);
        tabbedPane.addTab("Pedidos Actuales", null, pedidosActualesView, null);
        tabbedPane.addTab("Historial Pedidos", null, historialPedidoView, null);
        tabbedPane.addTab("Realizar Pedidos", null, realizarPedidoView, null);

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, false);
        }
        
        tabbedPane.setSelectedComponent(inicioView);
    }

    private void initButtons() {
        Dimension buttonSize = new Dimension(150, 40);
        btnInicio = createButton("Inicio", buttonSize);
        btnPedidos = createButton("Pedidos Actuales", buttonSize);
        btnSalas = createButton("Salas", buttonSize);
        btnHistorialPedidos = createButton("Historial Pedidos", buttonSize);
        btnCartaDelDia = createButton("Carta del día", buttonSize);
        btnUsuarios = createButton("Usuarios", buttonSize);
        
        if (usuario.getRol().equals("Mozo")) {
        	FooterOptions.add(btnInicio);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnPedidos);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnHistorialPedidos);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnCartaDelDia);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        } else if (usuario.getRol().equals("Cocinero")) {
            FooterOptions.add(btnPedidos);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnHistorialPedidos);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnCartaDelDia);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        } else if (usuario.getRol().equals("Administrador")) {
        	FooterOptions.add(btnInicio);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnPedidos);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnSalas);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnHistorialPedidos);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnCartaDelDia);
            FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
            FooterOptions.add(btnUsuarios);
        }
        
        FooterOptions.add(Box.createHorizontalGlue());
     
        btnCerrarSesion = new CustomButton();
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setPreferredSize(buttonSize);
        btnCerrarSesion.setBackground(new Color(253, 83, 83));
        btnCerrarSesion.setForeground(new Color(245, 245, 245));
        btnCerrarSesion.setRippleColor(new Color(255, 255, 255));
        btnCerrarSesion.setShadowColor(new Color(253, 83, 83));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        FooterOptions.add(btnCerrarSesion);
    }

    private CustomButton createButton(String text, Dimension size) {
        CustomButton button = new CustomButton();
        button.setText(text);
        button.setPreferredSize(size);
        button.addActionListener(this);
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(new Color(245, 245, 245));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetButtonColors();
        CustomButton sourceButton = (CustomButton) e.getSource();
        sourceButton.setBackground(new Color(24, 86, 56));

        if (sourceButton == btnCartaDelDia) {
            cartaDelDiaView.listar(platoController.listar());
            cartaDelDiaView.valoresIniciales();
            tabbedPane.setSelectedComponent(cartaDelDiaView);
        } else if (sourceButton == btnSalas) {
            tabbedPane.setSelectedComponent(salasView);
        } else if (sourceButton == btnPedidos) {
            tabbedPane.setSelectedComponent(pedidosActualesView);
            pedidosActualesView.CargarPedidosEnTabla();
        } else if (sourceButton == btnHistorialPedidos) {
            tabbedPane.setSelectedComponent(historialPedidoView);
            historialPedidoView.CargarPedidosEnTabla();
        } else if (sourceButton == btnUsuarios) {
            usuariosView.listar(usuarioController.listar());
            usuariosView.valoresIniciales();
            tabbedPane.setSelectedComponent(usuariosView);
        } else if (sourceButton == btnInicio) {
            tabbedPane.setSelectedComponent(inicioView);
        }
    }

    private void resetButtonColors() {
        for (Component component : FooterOptions.getComponents()) {
            if (component instanceof CustomButton && component != btnCerrarSesion) {
                component.setBackground(new Color(0, 0, 0));
            }
        }
    }

    private void cerrarSesion() {
        CustomAlert.showConfirmationAlert(
            "Cerrar Sesión", 
            "¿Estás seguro de cerrar sesión?", 
            e -> {
                new Login().setVisible(true);
                dispose(); 
            },
            e -> {
                
            }
        );
    }
}
