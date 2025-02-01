package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

public class EditUsuarioView extends JFrame implements DocumentListener,ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textContrasenna;
	private Usuario user;
	private JComboBox<String> comboBox;
	private UsuariosView usuarioView;
	private JLabel lblNombreError;
	private JLabel lblContrasennaErrror;
	private JButton btnEditar;

	private JLabel lblNombre;
	private JLabel lblRol;
	private JLabel lblCotrasenna;
	
	private String nombreNuevo;
	private String contrasennaNuevo;
	private String rolNuevo;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuario user = new Usuario();
					UsuariosView usuarioView = new UsuariosView();
					EditUsuarioView frame = new EditUsuarioView(user, usuarioView);
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
	public EditUsuarioView(Usuario user, UsuariosView usuarioView) {
		this.user = user;
		this.usuarioView = usuarioView;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 375);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textNombre = new JTextField();
		textNombre.setBounds(10, 47, 414, 25);
		contentPane.add(textNombre);
		textNombre.setColumns(10);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(this);
		btnEditar.setBounds(10, 265, 414, 23);
		contentPane.add(btnEditar);

		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 23, 414, 14);
		contentPane.add(lblNombre);

		lblRol = new JLabel("Rol");
		lblRol.setBounds(10, 183, 414, 14);
		contentPane.add(lblRol);

		lblCotrasenna = new JLabel("Contraseña");
		lblCotrasenna.setBounds(10, 97, 414, 14);
		contentPane.add(lblCotrasenna);

		textContrasenna = new JTextField();
		textContrasenna.setColumns(10);
		textContrasenna.setBounds(10, 122, 414, 25);
		contentPane.add(textContrasenna);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "mozo", "cocinero", "admin" }));
		comboBox.setBounds(10, 208, 414, 22);
		contentPane.add(comboBox);
		comboBox.addActionListener(this);

		lblNombreError = new JLabel("");
		lblNombreError.setForeground(new Color(255, 0, 0));
		lblNombreError.setBounds(10, 72, 414, 14);
		contentPane.add(lblNombreError);

		lblContrasennaErrror = new JLabel("");
		lblContrasennaErrror.setForeground(new Color(255, 0, 0));
		lblContrasennaErrror.setBounds(10, 145, 414, 14);
		contentPane.add(lblContrasennaErrror);

		textNombre.getDocument().addDocumentListener(this);
		textContrasenna.getDocument().addDocumentListener(this);

		IncializarValores();
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		ValidarCambioParametros();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		ValidarCambioParametros();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		ValidarCambioParametros();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboBox)
		ValidarCambioParametros();
		
		if (e.getSource() == btnEditar)
			AccionEditar();
	}
	
	private void IncializarValores() {
		textNombre.setText(user.getNombreUsuario());

		String rol = user.getRol();

		if (rol.contentEquals("mozo")) {
			comboBox.setSelectedItem(0);
		}
		if (rol.contentEquals("cocinero")) {
			comboBox.setSelectedItem(1);
		}
		if (rol.contentEquals("admin")) {
			comboBox.setSelectedItem(2);
		}
	}

	private String LeerContrasenna() {
		return textContrasenna.getText().trim();
	}

	private String LeerNombre() {
		return textNombre.getText().trim();
	}

	private String LeerRol() {
		return (String) comboBox.getSelectedItem();
	}

	private void ValidarCambioParametros() {
		boolean correcto = true;
		boolean cambioAlgunAtributo=false;

		if(VerificarCambioNombre())
			cambioAlgunAtributo=true;
		
		if(VerificarCambioContrasenna())
			cambioAlgunAtributo=true;
		
		if(VerificarCambioRol())
			cambioAlgunAtributo=true;
			
		if (!ValidarNombre())
			correcto = false;

		if (!ValidarContrasenna())
			correcto = false;

		CambiarHabilitacionBotonCrear( (correcto && cambioAlgunAtributo) );
	}

	private boolean VerificarCambioNombre() {
		String nombre = LeerNombre();
		nombreNuevo=nombre;

		if (!nombre.contentEquals(user.getNombreUsuario()) && !(nombre.isEmpty())) 
			lblNombre.setForeground(Color.BLUE);
		else {
			lblNombre.setForeground(Color.BLACK);
			nombreNuevo=user.getNombreUsuario();
			return false;
		}
		return true;
	}

	private boolean VerificarCambioContrasenna() {
		String contrasenna = LeerContrasenna();
		contrasennaNuevo=contrasenna;

		if (!contrasenna.contentEquals(user.getContrasena()) && !(contrasenna.isEmpty()))
			lblCotrasenna.setForeground(Color.BLUE);
		else {
			lblCotrasenna.setForeground(Color.BLACK);
			contrasennaNuevo=user.getContrasena();
			return false;
		}
		return true;

	}

	private boolean VerificarCambioRol() {
		String rol = LeerRol();
		rolNuevo=rol;
		if (!rol.contentEquals(user.getRol()))
			lblRol.setForeground(Color.BLUE);
		else {
			lblRol.setForeground(Color.BLACK);
			rolNuevo=user.getRol();
			return false;
		}
		return true;
	}

	private boolean ValidarNombre() {
		String nombre = LeerNombre();

		if (nombre.isEmpty()) {
			lblNombreError.setText("");
			return true;
		}

		if (!nombre.matches("^[A-Za-z]{3,}$")) {
			lblNombreError.setText("Debe tener minimo 2 caracteres y que sean solo letras");
			return false;
		} else
			lblNombreError.setText("");

		return true;
	}

	private boolean ValidarContrasenna() {
		String contrasenna = LeerContrasenna();

		if (contrasenna.isEmpty()) {
			lblContrasennaErrror.setText("");
			return true;
		}

		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#-_.])[A-Za-z\\d@$!%*?&]{8,}$";

		if (!contrasenna.matches(regex)) {
			lblContrasennaErrror
					.setText("Debe tener minimo 8 caracteres,1 Mayuscula,1 Minuscula,1 Numero y 1 caracter especial");
			return false;
		} else
			lblContrasennaErrror.setText("");

		return true;

	}

	private void CambiarHabilitacionBotonCrear(boolean estado) {
		btnEditar.setEnabled(estado);
		btnEditar.setBackground(estado ? Color.BLACK : Color.GRAY);
	}

	private void AccionEditar() {
		    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea editar este usuario "+user.getNombreUsuario()+"?", "Confirmación", JOptionPane.YES_NO_OPTION);
		    
		    if (confirmacion == JOptionPane.YES_OPTION) {
		        UsuarioController usuarioController = new UsuarioController();
				Usuario userUpdate=new Usuario(user.getIdUsuario(),nombreNuevo,user.getCorreo(),contrasennaNuevo,rolNuevo);
		        usuarioController.actualizarUsuario(userUpdate);
		        usuarioView.ReiniciarELementoEnTabla();		
		        dispose(); 
		    } else {
		        System.out.println("Eliminación cancelada.");
		    }
		
	}

}
