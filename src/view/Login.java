package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import components.*;
import controller.UsuarioController;
import model.Usuario;
import raven.glasspanepopup.GlassPanePopup;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel labelTitle, labelSubtitle, labelUsername, labelPassword;
    private CustomTextField fieldUsername;
    private CustomPasswordField fieldPassword;
    private CustomButton btnLogin;
    private RoundPanel mainPanel;
    private UsuarioController usuarioController;

    public Login() {
        System.out.println(getClass().getResource("/imgs/LogoIcon.png"));

    	
        usuarioController = new UsuarioController();
        setNimbusLookAndFeel();
        setTitle("Iniciar Sesión - Roku");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/imgs/LogoIcon.png")).getImage());

        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        mainPanel = new RoundPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc_mainPanel = new GridBagConstraints();
        gbc_mainPanel.gridx = 0;
        gbc_mainPanel.gridy = 0;
        gbc_mainPanel.anchor = GridBagConstraints.CENTER;
        getContentPane().add(mainPanel, gbc_mainPanel);

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon(
                new ImageIcon(getClass().getResource("/imgs/Logo.png")).getImage()
                        .getScaledInstance(70, 70, Image.SCALE_SMOOTH)
        ));

        labelTitle = new JLabel("¡Bienvenido de nuevo!");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 24));

        labelSubtitle = new JLabel("Inicia sesión para disfrutar del sistema");
        labelSubtitle.setFont(new Font("Arial", Font.PLAIN, 15));

        fieldUsername = new CustomTextField();
        fieldUsername.setPreferredSize(new Dimension(350, 40));
        fieldUsername.setPlaceholder("Ingrese su nombre de usuario");

        fieldPassword = new CustomPasswordField();
        fieldPassword.setPreferredSize(new Dimension(350, 40));
        fieldPassword.setPlaceholder("Ingrese su contraseña");

        btnLogin = new CustomButton();
        btnLogin.setText("Iniciar Sesión");
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnLogin.addActionListener(e -> iniciarSesion());

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick();
                }
            }
        };

        fieldUsername.addKeyListener(enterKeyListener);
        fieldPassword.addKeyListener(enterKeyListener);

        // Diseño GridBagLayout
        GridBagConstraints gbc;

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(logoLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(labelTitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 25, 0);
        mainPanel.add(labelSubtitle, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(new JLabel("Nombre de Usuario"), gbc);

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(fieldUsername, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(new JLabel("Contraseña"), gbc);

        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(fieldPassword, gbc);

        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(btnLogin, gbc);

        setResizable(true);
        GlassPanePopup.install(this);
        SwingUtilities.invokeLater(mainPanel::requestFocusInWindow);

    }

    private void iniciarSesion() {
        String username = fieldUsername.getText();
        String password = new String(fieldPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            CustomAlert.showAlert("Error", "Por favor ingrese todos los campos.", "error");
        } else {
            Usuario usuario = usuarioController.login(username, password);
            if (usuario != null) {
                redirectToDashboard(usuario);
            } else {
                CustomAlert.showAlert("Error", "Usuario y/o contraseña incorrectos.", "error");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    private void redirectToDashboard(Usuario usuario) {
        Dashboard dashboard = new Dashboard(usuario);
        dashboard.setVisible(true);
        GlassPanePopup.install(dashboard);
        this.dispose();
    }

    private void setNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
