package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import components.CustomButton;
import controller.PlatoController;
import controller.SalaController;
import controller.UsuarioController;
import model.Pedido;
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

    private CustomButton btnCartaDelDia, btnSalas, btnPedidos, btnInicio, btnUsuarios, btnHistorialPedidos;
    private JTabbedPane tabbedPane;
    private JPanel FooterOptions;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	   Usuario usuario = new Usuario();
                	   Dashboard frame =  new Dashboard(usuario);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bienvenido al Portal");
        setIconImage(new ImageIcon(getClass().getResource("/imgs/LogoIcon.png")).getImage());
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
    }

    private void initComponents() {
        JPanel headerImage = new JPanel();
        JLabel lblHeaderImage = new JLabel(new ImageIcon(getClass().getResource("/imgs/fondo.png")));
        headerImage.add(lblHeaderImage);
        getContentPane().add(headerImage, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        FooterOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(FooterOptions, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        initViews();
        initButtons();
    }

    private void initViews() {
        realizarPedidoView = new RealizarPedidoView(platoController.listar());
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

        for (int i = 0; i < tabbedPane.getTabCount(); i++)
            tabbedPane.setEnabledAt(i, false);
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
        FooterOptions.add(btnInicio);
        FooterOptions.add(btnPedidos);
        FooterOptions.add(btnSalas);
        FooterOptions.add(btnHistorialPedidos);
        FooterOptions.add(btnCartaDelDia);
        FooterOptions.add(btnUsuarios);
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
            if (component instanceof CustomButton) {
                component.setBackground(new Color(0, 0, 0));
            }
        }
    }
}
