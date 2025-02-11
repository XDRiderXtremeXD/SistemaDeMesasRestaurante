package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.*;

import components.CustomAlert;
import components.CustomButton;
import controller.PlatoController;
import controller.SalaController;
import controller.UsuarioController;
import model.Usuario;
import raven.glasspanepopup.GlassPanePopup;

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
    private FinalizarPedidoView finalizarPedidoView;
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
                CustomAlert.showConfirmationAlert(
                    "Confirmación", 
                    "¿Estás seguro de cerrar sesión?",
                    evt -> {
                        // Acción al presionar "Aceptar": limpiar sesión, mostrar Login y cerrar Dashboard.
                        Preferences prefs = Preferences.userNodeForPackage(Login.class);
                        prefs.remove("id");
                        prefs.remove("username");
                        prefs.remove("email");
                        prefs.remove("password");
                        prefs.remove("role");
                        
                        new Login().setVisible(true);
                        dispose();
                        GlassPanePopup.closePopupLast();
                    },
                    evt -> {
                        // Acción al presionar "Cancelar": simplemente se cierra el popup.
                        GlassPanePopup.closePopupLast();
                    }
                );
            }
        });
    }

    private void initComponents() {
        // Panel del header con imagen
        JPanel headerImage = new JPanel();
        headerImage.setBackground(SystemColor.textHighlightText);
        JLabel lblHeaderImage = new JLabel(new ImageIcon(getClass().getResource("/imgs/fondo.png")));
        headerImage.add(lblHeaderImage);
        getContentPane().add(headerImage, BorderLayout.NORTH);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Configuramos FooterOptions con BoxLayout en eje X para controlar la alineación
        FooterOptions = new JPanel();
        FooterOptions.setLayout(new BoxLayout(FooterOptions, BoxLayout.X_AXIS));
        FooterOptions.setBackground(SystemColor.textHighlightText);
        // Agregamos el panel de footer al sur del mainPanel
        mainPanel.add(FooterOptions, BorderLayout.SOUTH);

        // Panel central: pestañas
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(SystemColor.textHighlightText);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        initViews();
        initButtons();
    }

    private void initViews() {
        realizarPedidoView = new RealizarPedidoView(platoController.listar(), usuario);
        mesasView = new MesasView(salaController, tabbedPane, realizarPedidoView);
        inicioView = new Inicio(salaController, mesasView, tabbedPane);
        salasView = new SalasView(salaController, inicioView, mesasView);
        cartaDelDiaView = new CartaDelDiaView(platoController.listar());
        usuariosView = new UsuariosView(usuarioController.listar());
        pedidosActualesView = new PedidosActualesView(true, true, false);
        historialPedidoView = new HistorialPedidoView();
        finalizarPedidoView = new FinalizarPedidoView();

        tabbedPane.addTab("Inicio", null, inicioView, null);
        tabbedPane.addTab("Mesas", null, mesasView, null);
        tabbedPane.addTab("Salas", null, salasView, null);
        tabbedPane.addTab("Carta del día", null, cartaDelDiaView, null);
        tabbedPane.addTab("Usuarios", null, usuariosView, null);
        tabbedPane.addTab("Pedidos Actuales", null, pedidosActualesView, null);
        tabbedPane.addTab("Historial Pedidos", null, historialPedidoView, null);
        tabbedPane.addTab("Detalle Pedido", null, finalizarPedidoView, null);
        tabbedPane.addTab("Realizar Pedidos", null, realizarPedidoView, null);

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setEnabledAt(i, false);
        }
        tabbedPane.setSelectedComponent(inicioView);
    }

    private void initButtons() {
        Dimension buttonSize = new Dimension(150, 40);
        btnInicio = createButton("Inicio", buttonSize);
        btnPedidos = createButton("Pedidos", buttonSize);
        btnSalas = createButton("Salas", buttonSize);
        btnHistorialPedidos = createButton("Historial Pedidos", buttonSize);
        btnCartaDelDia = createButton("Carta del día", buttonSize);
        btnUsuarios = createButton("Usuarios", buttonSize);
        
        // Agregamos los botones de navegación
        FooterOptions.add(btnInicio);
        FooterOptions.add(Box.createRigidArea(new Dimension(10, 0))); // Espacio entre botones
        FooterOptions.add(btnPedidos);
        FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        FooterOptions.add(btnSalas);
        FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        FooterOptions.add(btnHistorialPedidos);
        FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        FooterOptions.add(btnCartaDelDia);
        FooterOptions.add(Box.createRigidArea(new Dimension(10, 0)));
        FooterOptions.add(btnUsuarios);
        
        // Agregamos un "glue" para empujar el siguiente componente hacia la derecha
        FooterOptions.add(Box.createHorizontalGlue());
        
        btnCerrarSesion = new CustomButton();
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setPreferredSize(buttonSize);
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        btnCerrarSesion.setBackground(new Color(253, 83, 83));
        btnCerrarSesion.setForeground(new Color(245, 245, 245));
        btnCerrarSesion.setRippleColor(new Color(255, 255, 255));
        btnCerrarSesion.setShadowColor(new Color(253, 83, 83));
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
        for (Component component : FooterOptions.getComponents()) {
            if (component instanceof CustomButton && component != btnCerrarSesion) {
                component.setBackground(new Color(0, 0, 0));
            }
        }
    }

    private void cerrarSesion() {
    	CustomAlert.showConfirmationAlert("Confirmación", "¿Estás seguro de cerrar sesión?",
				evt -> {
			        // Acción al presionar "Aceptar"
					Preferences prefs = Preferences.userNodeForPackage(Login.class);
					prefs.remove("id");
			        prefs.remove("username");
			        prefs.remove("email");
			        prefs.remove("password");
			        prefs.remove("role");
			        new Login().setVisible(true);
			        
			        this.dispose();
					GlassPanePopup.closePopupLast();
			    },
			    evt -> {
			        // Acción al presionar "Cancelar"
			        GlassPanePopup.closePopupLast();
			    }
        );
    }

}
