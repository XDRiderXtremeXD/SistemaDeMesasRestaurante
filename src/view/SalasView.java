package view;

import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.SalaController;
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
    
    SalaController controlador;
    private Inicio inicioView;
    private MesasView mesasView;
    /**
     * Create the panel.
     */
    public SalasView(SalaController controlador, Inicio inicioView, MesasView mesasView) {
        this.controlador = controlador;
        this.inicioView = inicioView; 
        this.mesasView = mesasView;
        initComponents();
        cargarDatosTabla();
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

        txtNombre = new JTextField();
        txtNombre.setBounds(112, 122, 176, 19);
        txtNombre.setBackground(Color.WHITE); 
        txtNombre.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK)); // Línea negra abajo
        txtNombre.setOpaque(false); // Sin fondo opaco
        txtNombre.setColumns(10);
        panel.add(txtNombre);

        txtMesas = new JTextField();
        txtMesas.setBounds(112, 215, 176, 19); 
        txtMesas.setBackground(Color.WHITE); 
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
        //evento de poner los valores de la tabla en los txt
        tablaSalas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int filaSeleccionada = tablaSalas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txtNombre.setText(tablaSalas.getValueAt(filaSeleccionada, 1).toString());
                    txtMesas.setText(tablaSalas.getValueAt(filaSeleccionada, 2).toString());
                }
            }
        });
        //permite desplazamiento si hay muchas filas
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
	    String nombre = txtNombre.getText();
	    int mesas = Integer.parseInt(txtMesas.getText()); 

	    Sala sala = new Sala(0, nombre, mesas);
	    controlador.registrarSala(sala);

	    DefaultTableModel modelo = (DefaultTableModel) tablaSalas.getModel();
	    modelo.addRow(new Object[]{sala.getIdSala(), sala.getNombre(), sala.getMesas()});
	    limpiarDatos();
	    
	    //AGREGAR AL PANEL
	    if (inicioView != null) {
	    	inicioView.agregarSalaPanel(sala);
	        inicioView.revalidate();  // Revalidar para que el panel de la sala se actualice
	        inicioView.repaint();  // Vuelve a pintar la vista
	    } else {
	        System.out.println("Error: No se encontró la vista Inicio");
	    }
	    
	 // Aquí es donde se pasa la cantidad de mesas a la vista de mesas
	    if (mesasView != null) {
	        mesasView.agregarMesasPanel(mesas);
	        mesasView.revalidate();  // Asegurarse de que la vista de mesas se actualice
	        mesasView.repaint();  // Vuelve a pintar la vista
	    } else {
	        System.out.println("Error: No se encontró la vista Mesas");
	    }
	}


	protected void actionPerformedBtnActualizar(ActionEvent e) {
	    int filaSeleccionada = tablaSalas.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        // Obtener los valores de la fila seleccionada
	        int idSala = Integer.parseInt(tablaSalas.getValueAt(filaSeleccionada, 0).toString());
	        String nombreAntiguo = tablaSalas.getValueAt(filaSeleccionada, 1).toString();  // Nombre antiguo de la sala
	        String nombreNuevo = txtNombre.getText();
	        int mesas = Integer.parseInt(txtMesas.getText());

	        Sala sala = new Sala(idSala, nombreNuevo, mesas);
	        controlador.actualizarSala(sala);  

	        cargarDatosTabla();
	        limpiarDatos();

	        // ACTUALIZAR EN PANEL
	        if (inicioView != null) {
	            inicioView.actualizarSalaPanel(nombreAntiguo, nombreNuevo);  // Pasamos nombre antiguo y nombre nuevo
	        }
	    } else {
	        System.out.println("Debe seleccionar una sala de la tabla para actualizar.");
	    }
	}


	protected void actionPerformedBtnEliminar(ActionEvent e) {
	    int filaSeleccionada = tablaSalas.getSelectedRow();
	    if (filaSeleccionada >= 0) {
	        int idSala = Integer.parseInt(tablaSalas.getValueAt(filaSeleccionada, 0).toString());
	        String nombreSala = tablaSalas.getValueAt(filaSeleccionada, 1).toString(); // Obtener el nombre de la sala
	        controlador.eliminarSala(idSala);

	        cargarDatosTabla();
	        limpiarDatos();

	        // ELIMINAR DEL PANEL
	        if (inicioView != null) {
	            inicioView.eliminarSalaDePanel(nombreSala);  
	        }
	    } else {
	        System.out.println("Debe seleccionar una sala de la tabla para eliminar.");
	    }
	}


	
	
	//DEPURACIÓN
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
    
    
    
    //OTROS MÉTODOS
    //Obtener los datos de la bd gracias al metodo list<>
  	private void cargarDatosTabla() {
          DefaultTableModel modelo = (DefaultTableModel) tablaSalas.getModel();
          modelo.setRowCount(0); //limpia la tabla para evitar duplicados
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
  
    public static void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    public void limpiarDatos() {
    	txtNombre.setText("");
        txtMesas.setText("");
    }
}
