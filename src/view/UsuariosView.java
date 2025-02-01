package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.UsuarioController;
import model.Usuario;

import components.CustomButtonEditorTable;
import utils.ButtonActionsUsuario;
import utils.ButtonRenderer;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;

public class UsuariosView extends JPanel implements ActionListener, DocumentListener {

	private static final long serialVersionUID = 1L;
	private JTextField textField_CorreoElectronico;
	private JTextField textField_Contraseña;
	private JTextField textField_Nombre;
	private JTable table;
	private JButton btnRegistrar;
	private JComboBox<String> comboBoxRol;
	private JLabel lblCorreoError;
	private JLabel lblContrasennaErrror;
	private JLabel lblNombreError;
	private JTextField textBuscador;
	private String filtro;

	/**
	 * Create the panel.
	 */
	public UsuariosView() {
		setLayout(new BorderLayout(0, 0));
		
		this.filtro="";

		// Panel izquierdo
		JPanel panelIzquierdo = new JPanel();
		panelIzquierdo.setBackground(new Color(192, 192, 192));
		add(panelIzquierdo, BorderLayout.WEST);

		// Panel principal
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));

		JLabel lblNewLabel = new JLabel("Nuevo Usuario");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Panel para contenidos
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));

		// Layout para el panel izquierdo
		GroupLayout gl_panelIzquierdo = new GroupLayout(panelIzquierdo);
		gl_panelIzquierdo.setHorizontalGroup(gl_panelIzquierdo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIzquierdo.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelIzquierdo.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(panel, Alignment.LEADING, 0, 0, Short.MAX_VALUE).addComponent(panel_1,
										Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelIzquierdo.setVerticalGroup(gl_panelIzquierdo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIzquierdo.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE).addContainerGap()));

		// Layout para el panel principal
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addContainerGap().addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(5)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		// Componentes del formulario
		JLabel lblCorreoElectronico = new JLabel("Correo Electronico");
		textField_CorreoElectronico = new JTextField();
		textField_CorreoElectronico.setColumns(10);

		JLabel lblContraseña = new JLabel("Contraseña");
		textField_Contraseña = new JTextField();
		textField_Contraseña.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		textField_Nombre = new JTextField();
		textField_Nombre.setColumns(10);

		textField_CorreoElectronico.getDocument().addDocumentListener(this);
		textField_Contraseña.getDocument().addDocumentListener(this);
		textField_Nombre.getDocument().addDocumentListener(this);

		JLabel lblNewLabel_1_1_2 = new JLabel("Rol");
		comboBoxRol = new JComboBox<String>();
		comboBoxRol.setModel(new DefaultComboBoxModel<String>(new String[] { "mozo", "cocinero", "admin" }));

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(new Color(255, 255, 255));
		btnRegistrar.setBackground(new Color(0, 0, 0));
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRegistrar.addActionListener(this);

		lblCorreoError = new JLabel("");
		lblCorreoError.setForeground(new Color(255, 0, 0));

		lblContrasennaErrror = new JLabel("");
		lblContrasennaErrror.setForeground(new Color(255, 0, 0));

		lblNombreError = new JLabel("");
		lblNombreError.setForeground(new Color(255, 0, 0));

		// Layout para el panel de contenido
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING).addComponent(comboBoxRol, 0, 506, Short.MAX_VALUE)
						.addComponent(textField_CorreoElectronico, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(lblCorreoElectronico, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(textField_Contraseña, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(lblContraseña, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(textField_Nombre, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(lblNombre, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1_2, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(btnRegistrar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
						.addComponent(lblCorreoError).addComponent(lblContrasennaErrror).addComponent(lblNombreError))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addGap(58)
				.addComponent(lblCorreoElectronico, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(textField_CorreoElectronico, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblCorreoError)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblContraseña, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(textField_Contraseña, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblContrasennaErrror)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(textField_Nombre, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNombreError)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblNewLabel_1_1_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(comboBoxRol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(54)
				.addComponent(btnRegistrar, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(46, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		panelIzquierdo.setLayout(gl_panelIzquierdo);

		// Panel derecho
		JPanel panelDerecho = new JPanel();
		panelDerecho.setBackground(new Color(192, 192, 192));
		add(panelDerecho, BorderLayout.CENTER);

		// Scroll para la tabla
		JScrollPane scrollPane = new JScrollPane();

		JLabel lblBuscador = new JLabel("Buscador:");

		textBuscador = new JTextField();
		textBuscador.setColumns(10);
		textBuscador.getDocument().addDocumentListener(this);

		GroupLayout gl_panelDerecho = new GroupLayout(panelDerecho);
		gl_panelDerecho.setHorizontalGroup(gl_panelDerecho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDerecho.createSequentialGroup().addContainerGap().addGroup(gl_panelDerecho
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDerecho.createSequentialGroup()
								.addComponent(lblBuscador, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(textBuscador,
										GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)).addContainerGap()));
		gl_panelDerecho.setVerticalGroup(gl_panelDerecho.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelDerecho.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelDerecho.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBuscador, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
								.addComponent(textBuscador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 607, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		CrearTabla(scrollPane);
		ReiniciarELementoEnTabla();
		ValidarCambioParametros();

		panelDerecho.setLayout(gl_panelDerecho);
	}

	public void ReiniciarELementoEnTabla() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		UsuarioController usuarioController = new UsuarioController();
		List<Usuario> usuarios = usuarioController.listarUsuarios(filtro);

		for (Usuario u : usuarios) {
			System.out.println(u.getNombreUsuario());
			Object[] row = { u.getIdUsuario(), u.getNombreUsuario(), u.getCorreo(), u.getRol() };
			model.addRow(row);
		}
	}

	private void CrearTabla(JScrollPane scrollPane) {
		// Tabla de usuarios
		table = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "IdUsuario", "Nombre", "Correo", "Rol", "Editar", "Eliminar" })) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 4 || column == 5; 
			}
		};
		scrollPane.setViewportView(table);

		table.setRowHeight(40); 

		// Rutas de los iconos
		String rutaIconoEditar = "/resources/editar.png";
		String rutaIconoEliminar = "/resources/eliminar.png";

		ImageIcon iconoEditar = new ImageIcon(getClass().getResource(rutaIconoEditar));
		ImageIcon iconoEliminar = new ImageIcon(getClass().getResource(rutaIconoEliminar));

		table.getColumn("Editar").setCellRenderer(new ButtonRenderer(iconoEditar));
		table.getColumn("Editar").setCellEditor(new CustomButtonEditorTable(new JButton(), iconoEditar,
				e -> ButtonActionsUsuario.editarUsuario(e, this)));

		table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer(iconoEliminar));
		table.getColumn("Eliminar").setCellEditor(new CustomButtonEditorTable(new JButton(), iconoEliminar,
				e -> ButtonActionsUsuario.eliminarUsuario(e, this)));
	}

	private String LeerCorreo() {
		return textField_CorreoElectronico.getText().trim();
	}

	private String LeerContrasenna() {
		return textField_Contraseña.getText().trim();
	}

	private String LeerNombre() {
		return textField_Nombre.getText().trim();
	}

	private String LeerRol() {
		return (String) comboBoxRol.getSelectedItem();
	}

	private void CambiarRol(int select) {
		comboBoxRol.setSelectedItem(select);
	}

	private void EscribirCorreo(String nuevoTexto) {
		textField_CorreoElectronico.setText(nuevoTexto);
	}

	private void EscribirContrasenna(String nuevoTexto) {
		textField_Contraseña.setText(nuevoTexto);
	}

	private void EscribirNombre(String nuevoTexto) {
		textField_Nombre.setText(nuevoTexto);
	}

	private void CrearUsuario() {
		String correoElectronico = LeerCorreo();
		String contrasena = LeerContrasenna();
		String nombre = LeerNombre();
		String rol = LeerRol();

		try {
			Usuario nuevoUsuario = new Usuario(1, nombre, correoElectronico, contrasena, rol);
			UsuarioController usuarioController = new UsuarioController();

			System.out.println("NOMBRE " + nuevoUsuario.getNombreUsuario());

			boolean registrado = usuarioController.crearUsuario(nuevoUsuario);

			if (registrado) {
				javax.swing.JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito",
						javax.swing.JOptionPane.INFORMATION_MESSAGE);

				textField_CorreoElectronico.setText("");
				textField_Contraseña.setText("");
				textField_Nombre.setText("");
				comboBoxRol.setSelectedIndex(0);

				ReiniciarELementoEnTabla();
				limpiarFormulario();

			} else {
				javax.swing.JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario. Intente nuevamente.",
						"Error", javax.swing.JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"Ocurrió un error al registrar el usuario: " + ex.getMessage(), "Error",
					javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiarFormulario() {
		EscribirCorreo("");
		EscribirNombre("");
		EscribirContrasenna("");
		CambiarRol(0);
		ValidarCambioParametros();
	}

	private void ValidarCambioParametros() {
		boolean correcto = true;

		if (!ValidarCorreo())
			correcto = false;

		if (!ValidarNombre())
			correcto = false;

		if (!ValidarContrasenna())
			correcto = false;

		CambiarHabilitacionBotonCrear(correcto);
	}

	private void CambiarHabilitacionBotonCrear(boolean estado) {
		btnRegistrar.setEnabled(estado);
		btnRegistrar.setBackground(estado ? Color.BLACK : Color.GRAY);
		btnRegistrar.setForeground(Color.WHITE);
	}

	private boolean ValidarCorreo() {
		// TODO Auto-generated method stub
		String correoElectronico = LeerCorreo();
		if (correoElectronico.isEmpty()) {
			lblCorreoError.setText("");
			return false;
		}

		if (!correoElectronico.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			lblCorreoError.setText("Debe ser un correo valido");
			return false;
		} else
			lblCorreoError.setText("");

		return true;
	}

	private boolean ValidarNombre() {
		// TODO Auto-generated method stub
		String nombre = LeerNombre();

		if (nombre.isEmpty()) {
			lblNombreError.setText("");
			return false;
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
			return false;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnRegistrar) {
			CrearUsuario();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if (e.getDocument() != textBuscador.getDocument())
			ValidarCambioParametros();
		else {
			SetearFiltroBuscador(leerTextoBuscador());
		    ReiniciarELementoEnTabla();	
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if (e.getDocument() != textBuscador.getDocument())
			ValidarCambioParametros();
		else {
			SetearFiltroBuscador(leerTextoBuscador());
		    ReiniciarELementoEnTabla();	
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		if (e.getDocument() != textBuscador.getDocument())
			ValidarCambioParametros();
		else {
			SetearFiltroBuscador(leerTextoBuscador());
		    ReiniciarELementoEnTabla();	
		}
	}
	
	private String leerTextoBuscador() {
		// TODO Auto-generated method stub
		return textBuscador.getText().trim();
	}

	private void SetearFiltroBuscador(String nuevoFiltro) {
		// TODO Auto-generated method stub
		filtro=nuevoFiltro; ;
	}


}
