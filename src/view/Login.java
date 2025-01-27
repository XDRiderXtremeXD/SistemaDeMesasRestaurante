package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Login extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblUsuario;
	private JLabel lblContraseña;
	private JTextField txtUsuario;
	private JTextField txtContrasena;
	private JButton btnIngresar;
	private JLabel lblCorreo;
	private JTextField txtCorreo;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 884, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("REGISTRO");
		lblNewLabel.setBounds(134, 40, 64, 13);
		contentPane.add(lblNewLabel);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(63, 93, 45, 13);
		contentPane.add(lblUsuario);
		
		lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(63, 251, 79, 13);
		contentPane.add(lblContraseña);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(152, 90, 96, 19);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtContrasena = new JTextField();
		txtContrasena.setBounds(152, 248, 96, 19);
		contentPane.add(txtContrasena);
		txtContrasena.setColumns(10);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(this);
		btnIngresar.setBounds(134, 373, 85, 21);
		contentPane.add(btnIngresar);
		
		lblCorreo = new JLabel("Correo: ");
		lblCorreo.setBounds(63, 174, 45, 13);
		contentPane.add(lblCorreo);
		
		txtCorreo = new JTextField();
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(152, 171, 96, 19);
		contentPane.add(txtCorreo);
		
		lblNewLabel_1 = new JLabel("");
	//	lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/img/res2.jfif")));

		lblNewLabel_1.setBounds(332, 0, 538, 466);
		contentPane.add(lblNewLabel_1);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnIngresar) {
			actionPerformedBtnIngresar(e);
		}
	}
	protected void actionPerformedBtnIngresar(ActionEvent e) {
	}
}
