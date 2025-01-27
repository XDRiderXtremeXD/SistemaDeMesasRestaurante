package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
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



public class UsuariosView extends JPanel implements ActionListener{

    private static final long serialVersionUID = 1L;
    private JTextField textField_CorreoElectronico;
    private JTextField textField_Contraseña;
    private JTextField textField_Nombre;
    private JTable table;
    private JButton btnRegistrar;
    private JComboBox<String> comboBoxRol;

    /**
     * Create the panel.
     */
    public UsuariosView() {
        setLayout(new BorderLayout(0, 0));

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
        gl_panelIzquierdo.setHorizontalGroup(
            gl_panelIzquierdo.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelIzquierdo.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panelIzquierdo.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(panel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                    .addContainerGap())
        );
        gl_panelIzquierdo.setVerticalGroup(
            gl_panelIzquierdo.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelIzquierdo.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                    .addContainerGap())
        );
        
        // Layout para el panel principal
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(5)
                    .addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
        );
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

        JLabel lblNewLabel_1_1_2 = new JLabel("Rol");
        comboBoxRol = new JComboBox<String>();
        comboBoxRol.setModel(new DefaultComboBoxModel<String>(new String[] {"mozo", "cocinero", "admin"}));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setForeground(new Color(255, 255, 255));
        btnRegistrar.setBackground(new Color(0, 0, 0));
        btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnRegistrar.addActionListener(this);

        // Layout para el panel de contenido
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
            gl_panel_1.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addComponent(comboBoxRol, Alignment.LEADING, 0, 506, Short.MAX_VALUE)
                        .addComponent(textField_CorreoElectronico, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(lblCorreoElectronico, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(textField_Contraseña, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(lblContraseña, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(textField_Nombre, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(lblNombre, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(lblNewLabel_1_1_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                    .addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addGap(58)
                    .addComponent(lblCorreoElectronico, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_CorreoElectronico, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblContraseña, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_Contraseña, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(textField_Nombre, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblNewLabel_1_1_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(comboBoxRol, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                    .addGap(54)
                    .addComponent(btnRegistrar, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE))
        );
        panel_1.setLayout(gl_panel_1);
        panelIzquierdo.setLayout(gl_panelIzquierdo);

        // Panel derecho
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(new Color(192, 192, 192));
        add(panelDerecho, BorderLayout.CENTER);

        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_panelDerecho = new GroupLayout(panelDerecho);
        gl_panelDerecho.setHorizontalGroup(
            gl_panelDerecho.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                .addContainerGap()
        );
        gl_panelDerecho.setVerticalGroup(
            gl_panelDerecho.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                .addContainerGap()
        );
        
        
        CrearTabla(scrollPane);
        ReiniciarELementoEnTabla();
     
       
        panelDerecho.setLayout(gl_panelDerecho);
    }

	public void ReiniciarELementoEnTabla() {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); 
		
		UsuarioController usuarioController = new UsuarioController();
		List<Usuario> usuarios =usuarioController.listarUsuarios();
		
		for (Usuario u : usuarios) {
			System.out.println(u.getNombreUsuario());
		    Object[] row = {u.getIdUsuario(), u.getNombreUsuario(), u.getCorreo(), u.getRol()};
		    model.addRow(row);
		}
	}

	private void CrearTabla(JScrollPane scrollPane) {
		// TODO Auto-generated method stub
		   // Tabla de usuarios
		table = new JTable(new DefaultTableModel(new Object[][] {},new String[] { "IdUsuario","Correo", "Nombre", "Rol", "Editar", "Eliminar" })) 
		{
			    @Override
			    public boolean isCellEditable(int row, int column) {
			        return column == 4 || column == 5;
			    }
			};

        scrollPane.setViewportView(table);
        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Editar").setCellEditor(new CustomButtonEditorTable(new JButton("Editar"),e -> ButtonActionsUsuario.eliminarUsuario(e, this)));

        table.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Eliminar").setCellEditor(new CustomButtonEditorTable(new JButton("Eliminar"),e -> ButtonActionsUsuario.eliminarUsuario(e, this)));
	}

	
	private void CrearUsuario() {
		// TODO Auto-generated method stub
        String correoElectronico = textField_CorreoElectronico.getText().trim();
        String contrasena = textField_Contraseña.getText().trim();
        String nombre = textField_Nombre.getText().trim();
        String rol = (String) comboBoxRol.getSelectedItem();

        if (correoElectronico.isEmpty() || contrasena.isEmpty() || nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Por favor, complete todos los campos.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "El correo electrónico no tiene un formato válido.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            Usuario nuevoUsuario = new Usuario(1,correoElectronico, contrasena, nombre, rol);
            UsuarioController usuarioController = new UsuarioController();

            boolean registrado = usuarioController.crearUsuario(nuevoUsuario);

            if (registrado) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Usuario registrado con éxito.",
                    "Éxito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                textField_CorreoElectronico.setText("");
                textField_Contraseña.setText("");
                textField_Nombre.setText("");
                comboBoxRol.setSelectedIndex(0);

                ReiniciarELementoEnTabla();
                limpiarFormulario();
            
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el usuario. Intente nuevamente.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Ocurrió un error al registrar el usuario: " + ex.getMessage(),
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
	}
	
	private void limpiarFormulario() {
	    textField_CorreoElectronico.setText("");
	    textField_Contraseña.setText("");
	    textField_Nombre.setText("");
	    comboBoxRol.setSelectedIndex(0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnRegistrar) {
            CrearUsuario();
        	}
	}

}
