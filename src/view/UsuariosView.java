package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import components.*;
import controller.UsuarioController;
import model.Usuario;
import raven.glasspanepopup.GlassPanePopup;

public class UsuariosView extends JPanel {
	private static final long serialVersionUID = 1L;
	
    private JTable tablaUsuarios;
    private DefaultTableModel tableModel;
    private CustomTextField txtBuscar, txtUsername, txtEmail;
    private CustomPasswordField txtContrasena;
    private CustomComboBox<String> cmbRol;
    private CustomButton btnAgregar, btnEliminar, btnEditar;
    private JPanel formularioPanel;
    private boolean isEditing = false;
    private int editingRowIndex = -1;

    public UsuariosView(List<Usuario> usuarios) {
        setPreferredSize(new Dimension(1427, 675));
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nombre de Usuario", "Correo", "Contraseña", "Rol"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(tableModel);
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);
        tablaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(150);

        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablaPanel.setBackground(SystemColor.textHighlightText);
        
        txtBuscar = new CustomTextField();
        txtBuscar.setPlaceholder("Buscar por nombre de usuario...");
        txtBuscar.setPreferredSize(new Dimension(300, 40));
        
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarUsuarios(txtBuscar.getText(), usuarios);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	buscarUsuarios(txtBuscar.getText(), usuarios);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	buscarUsuarios(txtBuscar.getText(), usuarios);
            }
        });
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(SystemColor.textHighlightText);
        searchPanel.add(txtBuscar);
        tablaPanel.add(searchPanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        CustomTable.TableCustom.apply(scrollPane, CustomTable.TableCustom.TableType.DEFAULT);
        tablaPanel.add(scrollPane, BorderLayout.CENTER);

        listar(usuarios);
        
        formularioPanel = new JPanel();
        formularioPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(211, 211, 211)));
        formularioPanel.setBackground(SystemColor.textHighlightText);
        formularioPanel.setLayout(null);
        formularioPanel.setPreferredSize(new Dimension(400, 0));

        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(50, 30, 300, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        formularioPanel.add(lblTitulo);

        JLabel lblUsername = new JLabel("Nombre de Usuario");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setBounds(55, 80, 295, 20);
        formularioPanel.add(lblUsername);

        txtUsername = new CustomTextField();
        txtUsername.setBounds(50, 110, 300, 42);
        txtUsername.setPlaceholder("Ingresa el nombre de usuario");
        formularioPanel.add(txtUsername);

        JLabel lblEmail = new JLabel("Correo Electrónico");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBounds(55, 160, 295, 20);
        formularioPanel.add(lblEmail);

        txtEmail = new CustomTextField();
        txtEmail.setBounds(50, 190, 300, 42);
        txtEmail.setPlaceholder("ejemplo@correo.com");
        formularioPanel.add(txtEmail);
        
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        lblContrasena.setBounds(55, 240, 295, 20);
        formularioPanel.add(lblContrasena);

        txtContrasena = new CustomPasswordField();
        txtContrasena.setBounds(50, 270, 300, 42);
        txtContrasena.setPlaceholder("Ingresa la contraseña");
        formularioPanel.add(txtContrasena);
        
        JLabel lblRol = new JLabel("Rol");
        lblRol.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRol.setBounds(55, 320, 295, 20);
        formularioPanel.add(lblRol);

        cmbRol = new CustomComboBox<>();
        cmbRol.setModel(new DefaultComboBoxModel<>(new String[]{"Seleccione un Rol", "Administrador", "Empleado", "Cliente"}));
        cmbRol.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbRol.setBounds(55, 350, 290, 33);
        cmbRol.setSelectedIndex(0);
        formularioPanel.add(cmbRol);
        
        btnAgregar = new CustomButton();
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBackground(new java.awt.Color(30, 180, 114));
        btnAgregar.setForeground(new java.awt.Color(245, 245, 245));
        btnAgregar.setRippleColor(new java.awt.Color(255, 255, 255));
        btnAgregar.setShadowColor(new java.awt.Color(30, 180, 114));
        btnAgregar.setText("Agregar Usuario");
        btnAgregar.setBounds(50, 410, 140, 46);
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formularioPanel.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            Usuario nuevoUsuario = validarYObtenerUsuarioDesdeCampos();
            if (nuevoUsuario == null) {
                return;
            }

            UsuarioController usuarioController = new UsuarioController();

            try {
                Usuario usuarioCreado = usuarioController.crear(nuevoUsuario);
                if (usuarioCreado != null) {
                    Object[] row = {
                        usuarioCreado.getIdUsuario(),
                        usuarioCreado.getNombreUsuario(),
                        usuarioCreado.getCorreo(),
                        usuarioCreado.getContrasena(),
                        usuarioCreado.getRol()
                    };
                    tableModel.addRow(row);
                    CustomAlert.showAlert("Éxito", "El usuario ha sido agregado correctamente.", "success");
                    limpiarCampos();
                }
            } catch (SQLException ex) {
                CustomAlert.showAlert("Error", ex.getMessage(), "error");
            }
        });
        
        btnEditar = new CustomButton();
        btnEditar.setFocusPainted(false);
        btnEditar.setBackground(new Color(0, 123, 255));
        btnEditar.setForeground(new Color(245, 245, 245));
        btnEditar.setRippleColor(new Color(255, 255, 255));
        btnEditar.setShadowColor(new Color(0, 123, 255));
        btnEditar.setText("Editar Usuario");
        btnEditar.setBounds(50, 470, 300, 46);
        btnEditar.setEnabled(false);
        btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formularioPanel.add(btnEditar);
        
        btnEditar.addActionListener(e -> {
            if (!isEditing) {
                int filaSeleccionada = tablaUsuarios.getSelectedRow();
                if (filaSeleccionada != -1) {
                    txtUsername.setText((String) tableModel.getValueAt(filaSeleccionada, 1));
                    txtEmail.setText((String) tableModel.getValueAt(filaSeleccionada, 2));
                    txtContrasena.setText((String) tableModel.getValueAt(filaSeleccionada, 3));

                    String rol = (String) tableModel.getValueAt(filaSeleccionada, 4);
                    int indexRol = 0;
                    switch (rol) {
                        case "Administrador":
                            indexRol = 1;
                            break;
                        case "Empleado":
                            indexRol = 2;
                            break;
                        case "Cliente":
                            indexRol = 3;
                            break;
                        default:
                            indexRol = 0;
                    }
                    cmbRol.setSelectedIndex(indexRol);

                    btnAgregar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    tablaUsuarios.setEnabled(false);
                    txtBuscar.setEnabled(false);

                    btnEditar.setText("Guardar Cambios");

                    isEditing = true;
                    editingRowIndex = filaSeleccionada;
                }
            } else {
                Usuario usuarioAEditar = validarYObtenerUsuarioDesdeCampos();
                if (usuarioAEditar == null) {
                    return;
                }

                int idUsuario = (int) tableModel.getValueAt(editingRowIndex, 0);
                usuarioAEditar.setIdUsuario(idUsuario);

                UsuarioController usuarioController = new UsuarioController();
                try {
                    Usuario usuarioActualizado = usuarioController.actualizar(usuarioAEditar);
                    if (usuarioActualizado != null) {
                        tableModel.setValueAt(usuarioActualizado.getNombreUsuario(), editingRowIndex, 1);
                        tableModel.setValueAt(usuarioActualizado.getCorreo(), editingRowIndex, 2);
                        tableModel.setValueAt(usuarioActualizado.getContrasena(), editingRowIndex, 3);
                        tableModel.setValueAt(usuarioActualizado.getRol(), editingRowIndex, 4);
                        CustomAlert.showAlert("Éxito", "El usuario ha sido actualizado correctamente.", "success");

                        btnEditar.setText("Editar Usuario");
                        btnAgregar.setEnabled(true);
                        btnEliminar.setEnabled(true);
                        tablaUsuarios.setEnabled(true);
                        txtBuscar.setEnabled(true);

                        isEditing = false;
                        editingRowIndex = -1;
                        limpiarCampos();
                    } else {
                        CustomAlert.showAlert("Error", "No se pudo actualizar el usuario.", "error");
                    }
                } catch (SQLException ex) {
                    CustomAlert.showAlert("Error", ex.getMessage(), "error");
                }
            }
        });

        btnEliminar = new CustomButton();
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBackground(new java.awt.Color(253, 83, 83));
        btnEliminar.setForeground(new java.awt.Color(245, 245, 245));
        btnEliminar.setRippleColor(new java.awt.Color(255, 255, 255));
        btnEliminar.setShadowColor(new java.awt.Color(253, 83, 83));
        btnEliminar.setText("Eliminar Usuario");
        btnEliminar.setBounds(210, 410, 140, 46);
        btnEliminar.setEnabled(false);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formularioPanel.add(btnEliminar);

        tablaUsuarios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionValida = tablaUsuarios.getSelectedRowCount() > 0;
            btnEliminar.setEnabled(seleccionValida);
        });

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnEditar.setEnabled(tablaUsuarios.getSelectedRowCount() == 1);
            }
        });
      
        btnEliminar.addActionListener(e -> {
            int[] filasSeleccionadas = tablaUsuarios.getSelectedRows();
            if (filasSeleccionadas.length > 0) {
                CustomAlert.showConfirmationAlert(
                    "Confirmación", 
                    "¿Estás seguro de eliminar el(los) usuario(s)?",
                    evt -> {
                        // Acción al presionar "Aceptar"
                        UsuarioController usuarioController = new UsuarioController();
                        boolean algunEliminado = false;
                        int usuariosEliminados = 0;

                        for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {
                            int fila = filasSeleccionadas[i];
                            int idUsuario = (int) tableModel.getValueAt(fila, 0);
                            boolean eliminado = usuarioController.eliminar(idUsuario);
                            if (eliminado) {
                                tableModel.removeRow(fila);
                                algunEliminado = true;
                                usuariosEliminados++;
                            }
                        }
                        GlassPanePopup.closePopupLast();

                        if (algunEliminado) {
                            String mensaje = (usuariosEliminados == 1) 
                                ? "El usuario ha sido eliminado" 
                                : "Los usuarios han sido eliminados";
                            CustomAlert.showAlert("Éxito", mensaje, "success");
                        } else {
                            String mensajeError = (filasSeleccionadas.length == 1) 
                                ? "No se pudo eliminar el usuario" 
                                : "No se pudieron eliminar los usuarios";
                            CustomAlert.showAlert("Error", mensajeError, "error");
                        }
                    },
                    evt -> {
                        // Acción al presionar "Cancelar"
                        GlassPanePopup.closePopupLast();
                    }
                );
            }
        });
        
        add(tablaPanel, BorderLayout.CENTER);
        add(formularioPanel, BorderLayout.EAST);
    }

    public void listar(List<Usuario> usuarios) {
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarios) {
            Object[] row = new Object[5];
            row[0] = usuario.getIdUsuario();
            row[1] = usuario.getNombreUsuario();
            row[2] = usuario.getCorreo();
            row[3] = usuario.getContrasena();
            row[4] = usuario.getRol();
            tableModel.addRow(row);
        }
    }
    
    private void buscarUsuarios(String query, List<Usuario> usuarios) {
        DefaultTableModel tableModelDerecho = (DefaultTableModel) tablaUsuarios.getModel();
        tableModelDerecho.setRowCount(0);

        if (query.isEmpty()) {
            listar(usuarios);
        } else {
            for (Usuario usuario : usuarios) {
                if (usuario.getNombreUsuario().toLowerCase().contains(query.toLowerCase())) {
                    Object[] row = new Object[5];
                    row[0] = usuario.getIdUsuario();
                    row[1] = usuario.getNombreUsuario();
                    row[2] = usuario.getCorreo();
                    row[3] = usuario.getContrasena();
                    row[4] = usuario.getRol();
                    tableModelDerecho.addRow(row);
                }
            }
        }
    }
    
    private void limpiarCampos() {
    	txtBuscar.setText("");
        txtUsername.setText("");
        txtUsername.restorePlaceholder();
        txtEmail.setText("");
        txtEmail.restorePlaceholder();
        txtContrasena.setText("");
        txtContrasena.restorePlaceholder();
        cmbRol.setSelectedIndex(0);

        formularioPanel.requestFocus();
    }
    
    private Usuario validarYObtenerUsuarioDesdeCampos() {
        String nombreUsuario = txtUsername.getText().trim();
        String correo = txtEmail.getText().trim();
        String contra = new String(txtContrasena.getPassword()).trim();
        String rol = (String) cmbRol.getSelectedItem();

        if (nombreUsuario.length() < 3) {
            CustomAlert.showAlert("Error", "El nombre de usuario debe tener al menos 3 caracteres.", "error");
            return null;
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!correo.matches(emailRegex)) {
            CustomAlert.showAlert("Error", "El correo electrónico no es válido.", "error");
            return null;
        }

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!contra.matches(passwordRegex)) {
            CustomAlert.showAlert("Error", "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial.", "error");
            return null;
        }

        if ("Seleccione un Rol".equals(rol)) {
            CustomAlert.showAlert("Error", "Debe seleccionar un rol.", "error");
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreo(correo);
        usuario.setContrasena(contra);
        usuario.setRol(rol);

        return usuario;
    }

    public void valoresIniciales() {
        txtBuscar.setText("");
        txtUsername.setText("");
        txtUsername.restorePlaceholder();
        txtEmail.setText("");
        txtEmail.restorePlaceholder();
        txtContrasena.setText("");
        txtContrasena.restorePlaceholder();
        cmbRol.setSelectedIndex(0);
        
        btnAgregar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnEditar.setText("Editar Usuario");
        btnEliminar.setEnabled(false);
        
        tablaUsuarios.setEnabled(true);
        txtBuscar.setEnabled(true);
        
        isEditing = false;
        editingRowIndex = -1;
    }
}
