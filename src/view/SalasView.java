package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.SalaController;
import dao.impl.SalaDaoImpl;
import dao.interfaces.ISalaDao;
import model.Sala;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class SalasView extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JLabel lblTituloForm;
    private JButton btnAgregar;
    private JPanel panel_1;
    private JLabel lblNombre;
    private JLabel lblNumMesas;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnNewButton_3;
    private JTextField txtNombre;
    private JTextField txtMesas;
    private JScrollPane scrollPane;
    private JTable tablaSalas;
    
    ISalaDao salaDao = new SalaDaoImpl(); // Implementación concreta
    SalaController controlador = new SalaController();
    private PanelView panelView; // Instancia PanelView


    /**
     * Create the panel.
     */
    public SalasView(SalaController controlador) {
        initComponents();
        cargarDatosTabla();
        panelView = new PanelView();
        add(panelView); // Agregar el PanelView a la vista principal, ajustando su layout si es necesario
    }
    private void initComponents() {
        setSize(1100, 600);
        setLayout(null);

        panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));
        panel.setBounds(127, 81, 346, 454);
        add(panel);
        panel.setLayout(null);

        btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(this);
        btnAgregar.setBounds(43, 304, 85, 21);
        panel.add(btnAgregar);

        panel_1 = new JPanel();
        panel_1.setBackground(new Color(0, 0, 0));
        panel_1.setBounds(0, 0, 346, 51);
        panel.add(panel_1);
        panel_1.setLayout(null);

        lblTituloForm = new JLabel("Nueva Sala");
        lblTituloForm.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 30));
        lblTituloForm.setBounds(98, 0, 160, 54);
        panel_1.add(lblTituloForm);
        lblTituloForm.setForeground(new Color(255, 255, 255));
        lblTituloForm.setBackground(new Color(205, 128, 0));

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(43, 121, 59, 21);
        panel.add(lblNombre);

        lblNumMesas = new JLabel("Mesas:");
        lblNumMesas.setBounds(43, 215, 59, 21);
        panel.add(lblNumMesas);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this);
        btnEliminar.setBounds(43, 357, 85, 21);
        panel.add(btnEliminar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(this);
        btnActualizar.setBounds(203, 304, 85, 21);
        panel.add(btnActualizar);

        btnNewButton_3 = new JButton("New button");
        btnNewButton_3.setBounds(203, 357, 85, 21);
        panel.add(btnNewButton_3);

        // Campo de texto para Nombre
        txtNombre = new JTextField();
        txtNombre.setBounds(112, 122, 176, 19);
        txtNombre.setBackground(Color.WHITE); // Fondo blanco (opcional)
        txtNombre.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK)); // Línea negra abajo
        txtNombre.setOpaque(false); // Sin fondo opaco
        txtNombre.setColumns(10);
        panel.add(txtNombre);

        // Campo de texto para Mesas (con la línea abajo)
        txtMesas = new JTextField();
        txtMesas.setBounds(112, 215, 176, 19); // Cambié la posición para que no se solapen
        txtMesas.setBackground(Color.WHITE); // Fondo blanco (opcional)
        txtMesas.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK)); // Línea negra abajo
        txtMesas.setOpaque(false); // Sin fondo opaco
        txtMesas.setColumns(10);
        panel.add(txtMesas);
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(514, 81, 527, 448);
        add(scrollPane);
        
        tablaSalas = new JTable();
        tablaSalas.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"ID", "NOMBRE", "MESAS"
        	}
        ));
        tablaSalas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int filaSeleccionada = tablaSalas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txtNombre.setText(tablaSalas.getValueAt(filaSeleccionada, 1).toString());
                    txtMesas.setText(tablaSalas.getValueAt(filaSeleccionada, 2).toString());
                }
            }
        });

        scrollPane.setViewportView(tablaSalas);
    }
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEliminar) {
			actionPerformedBtnEliminar(e);
		}
		if (e.getSource() == btnActualizar) {
			actionPerformedBtnActualizar(e);
		}
		if (e.getSource() == btnAgregar) {
			actionPerformedBtnAgregar(e);
		}
	}
	
	protected void actionPerformedBtnAgregar(ActionEvent e) {
	    // Obtener los valores de los campos de texto
	    String nombre = txtNombre.getText();
	    int mesas = Integer.parseInt(txtMesas.getText());  // Convertir el número de mesas a entero

	    // Crear un objeto Sala
	    Sala sala = new Sala(0, nombre, mesas);

	    // Llamar al método registrarSala del controlador
	    SalaController controlador = new SalaController();
	    controlador.registrarSala(sala);

	    // Agregar directamente la nueva fila a la tabla
	    DefaultTableModel modelo = (DefaultTableModel) tablaSalas.getModel();
	    modelo.addRow(new Object[]{sala.getIdSala(), sala.getNombre(), sala.getMesas()});

	    // Limpiar los campos
	    txtNombre.setText("");
	    txtMesas.setText("");

	    // Obtener el PanelView existente (suponiendo que la jerarquía de contenedores es correcta)
	    PanelView panelView = (PanelView) getParent(); // Obtener el panel contenedor que tiene el PanelView
	    
	    if (panelView != null) {
	        // Agregar la nueva sala al PanelView
	        panelView.agregarSalaPanel(sala.getNombre());
	        
	        // Forzar la actualización del PanelView
	        SwingUtilities.invokeLater(() -> {
	            panelView.revalidate();
	            panelView.repaint();
	        });
	    } else {
	        System.out.println("Error: No se encontró el PanelView.");
	    }
	}



	
	protected void actionPerformedBtnActualizar(ActionEvent e) {
	    // Obtener la fila seleccionada de la tabla
	    int filaSeleccionada = tablaSalas.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        // Obtener los valores de la fila seleccionada
	        int idSala = Integer.parseInt(tablaSalas.getValueAt(filaSeleccionada, 0).toString());
	        String nombre = txtNombre.getText();
	        int mesas = Integer.parseInt(txtMesas.getText());

	        // Crear un objeto Sala con los valores actualizados
	        Sala sala = new Sala(idSala, nombre, mesas);

	        // Llamar al método actualizarSala del controlador
	        SalaController controlador = new SalaController();
	        controlador.actualizarSala(sala);

	        // Actualizar la tabla después de la actualización
	        cargarDatosTabla();
	        
	        // Limpiar los campos de texto
	        txtNombre.setText("");
	        txtMesas.setText("");
	    } else {
	        System.out.println("Debe seleccionar una sala de la tabla para actualizar.");
	    }
	}

	protected void actionPerformedBtnEliminar(ActionEvent e) {
	    // Obtener la fila seleccionada de la tabla
	    int filaSeleccionada = tablaSalas.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        // Obtener el ID de la sala seleccionada
	        int idSala = Integer.parseInt(tablaSalas.getValueAt(filaSeleccionada, 0).toString());

	        // Llamar al método eliminarSala del controlador
	        SalaController controlador = new SalaController();
	        controlador.eliminarSala(idSala);

	        // Actualizar la tabla después de la eliminación
	        cargarDatosTabla();
	    } else {
	        System.out.println("Debe seleccionar una sala de la tabla para eliminar.");
	    }
	}
	
	private void cargarDatosTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaSalas.getModel();
        modelo.setRowCount(0); 
        try {
            List<Sala> salas = controlador.listarSalas();
            for (Sala sala : salas) {
                modelo.addRow(new Object[]{sala.getIdSala(), sala.getNombre(), sala.getMesas()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos de la tabla: " + e.getMessage());
        }
    }

	
	
	
	// Método para mostrar una lista de salas
    public static void mostrarSalas(List<Sala> salas) {
        // Implementación para mostrar las salas en una tabla
        System.out.println("Salas disponibles:");
        for (Sala sala : salas) {
            System.out.println("ID: " + sala.getIdSala() + ", Nombre: " + sala.getNombre() + ", Mesas: " + sala.getMesas());
        }
    }

    // Método para mostrar una sala específica
    public static void mostrarSala(Sala sala) {
        System.out.println("Detalles de la Sala:");
        System.out.println("ID: " + sala.getIdSala());
        System.out.println("Nombre: " + sala.getNombre());
        System.out.println("Mesas: " + sala.getMesas());
    }

    // Método para mostrar un mensaje al usuario
    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
