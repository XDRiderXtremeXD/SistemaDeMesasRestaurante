package model;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import components.CustomButtonEditorTable;
import controller.PedidoController;
import model.Pedido;
import utils.ButtonRenderer;
import view.DetallePedidoView;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HistorialPedidoView extends JPanel implements ActionListener,DocumentListener{

	private static final long serialVersionUID = 1L;
	private JTextField textFiltro;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private PedidoController pedidoController;
	private JComboBox<String> comboBox;
	private String tipoFiltro;
	private String textoFiltro;
	private JLabel lblBuscador ;

	/**
	 * Create the panel.
	 */
	public HistorialPedidoView() {
		setLayout(null);
		pedidoController=new PedidoController();
		tipoFiltro="Pedido";
		textoFiltro="";
		JLabel lblNewLabel = new JLabel("Tipo de buscador");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 121, 29);
		add(lblNewLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Pedido", "Sala", "Mozo"}));
		comboBox.setBounds(144, 11, 189, 29);
		add(comboBox);
		comboBox.addActionListener(this);
		
		lblBuscador = new JLabel("ID Pedido:");
		lblBuscador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBuscador.setBounds(10, 51, 121, 29);
		add(lblBuscador);
		
		textFiltro = new JTextField();
		textFiltro.setBounds(144, 51, 189, 29);
		add(textFiltro);
		textFiltro.setColumns(10);
		textFiltro.getDocument().addDocumentListener(this);
		
		scrollPane= new JScrollPane();
		scrollPane.setBounds(10, 118, 1410, 771);
		add(scrollPane);
		
		CreacionTabla();
		ReiniciarTablaConFiltros();
		
	}

	public void CreacionTabla() {
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "IdPedido", "Sala", "NumeroMesa", "Fecha", "Total", "Mozo", "Ver Detalle" }
            ) {
            	@Override
    			public boolean isCellEditable(int row, int column) {
    				return column == 6; 
    			}
            };
            table = new JTable(tableModel);
            table.setRowHeight(40); 
    		// Rutas de los iconos
    		String rutaIconoDetalle = "/resources/detalles.png";
    		ImageIcon iconoDetalle = new ImageIcon(getClass().getResource(rutaIconoDetalle));
    				
    		table.getColumn("Ver Detalle").setCellRenderer(new ButtonRenderer(iconoDetalle));
    		table.getColumn("Ver Detalle").setCellEditor(new CustomButtonEditorTable(new JButton(), iconoDetalle,
    				e -> verDetallePedidoEstado(e))); 
            
            scrollPane.setViewportView(table);
    }

	public void verDetallePedidoEstado(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        int row = (int) sourceButton.getClientProperty("row");
        int column = (int) sourceButton.getClientProperty("column");
        JTable tabla = (JTable) sourceButton.getClientProperty("table");
        TableModel model = tabla.getModel(); 
        Object value = model.getValueAt(row, 0); 
        System.out.println("Editando fila: " + row + ", columna: " + column+", valor: "+value.toString());
        int idPedido=Integer.parseInt(value.toString());
        Pedido pedido=pedidoController.obtenerPedido(idPedido);
        DetallePedidoView frame = new DetallePedidoView(pedido,null);
        frame.setLocationRelativeTo(this); 
        frame.setVisible(true); 
    }
	

		private void LogicaCambioTextoFiltro() {
			textoFiltro=textFiltro.getText();
			ReiniciarTablaConFiltros();
		}
		
		private void LogicaCambioTipoFiltro() {
			tipoFiltro=comboBox.getSelectedItem().toString();
			
			if(tipoFiltro.contentEquals("Pedido"))
				lblBuscador.setText("ID Pedido:");
			else if(tipoFiltro.contentEquals("Mozo"))
				lblBuscador.setText("Nombre Mozo:");
			else if(tipoFiltro.contentEquals("Sala"))
				lblBuscador.setText("Sala:");
			
			ReiniciarTablaConFiltros();
		}

		private void ReiniciarTablaConFiltros() {
			System.out.println("FILTROS "+tipoFiltro+" "+textoFiltro);
			List<Pedido> pedidos = new ArrayList<Pedido>();
			if(textoFiltro.isEmpty())
			pedidos=pedidoController.listarPedidos(false,false,true);
			else if(tipoFiltro.contentEquals("Pedido")) {
				Pedido pedidoID=pedidoController.obtenerPedido(Integer.parseInt(textoFiltro));				
				pedidos.add(pedidoID);
			}
			else if(tipoFiltro.contentEquals("Mozo")) {
				pedidos=pedidoController.listarPedidosPorMozo(textoFiltro);
			}
			else if(tipoFiltro.contentEquals("Sala")) {
				pedidos=pedidoController.listarPedidosPorSala(textoFiltro);
			}
			
	        tableModel.setRowCount(0);
	        for (int i = pedidos.size() - 1; i >= 0; i--) {
	            Pedido pedido = pedidos.get(i);
	            tableModel.addRow(new Object[] {
	                pedido.getIdPedido(),
	                pedido.getNombreSala(),
	                pedido.getNumeroMesa(),
	                pedido.getFecha(),
	                pedido.getTotal(),
	                pedido.getUsuario(),
	                "Ver Detalle"
	            });
	        }
			
			
		}

		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		 if (arg0.getSource() == comboBox) {
			 LogicaCambioTipoFiltro();
		    }
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == textFiltro.getDocument())
			LogicaCambioTextoFiltro();
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == textFiltro.getDocument())
			LogicaCambioTextoFiltro();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == textFiltro.getDocument())
			LogicaCambioTextoFiltro();
	}

	
}