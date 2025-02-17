package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import components.CustomButtonEditorTable;
import components.CustomComboBox;
import components.CustomTable;
import components.CustomTextField;
import controller.PedidoController;
import model.Pedido;
import utils.ButtonRenderer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HistorialPedidoView extends JPanel implements ActionListener, DocumentListener{
	private static final long serialVersionUID = 1L;
	
	private CustomTextField txtFiltro;
	private DefaultTableModel tableModel;
	private PedidoController pedidoController;
	private CustomComboBox<String> comboBox;
	private String tipoFiltro;
	private String textoFiltro;
	private JLabel lblBuscador;
	private JLabel lblTBuscador;
	private JTable table_1;
	private JScrollPane scrollPane;

	public HistorialPedidoView() {
		pedidoController = new PedidoController();
		tipoFiltro = "Pedido";
		textoFiltro = "";
		setPreferredSize(new Dimension(1427, 675));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlightText);
		add(panel, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		lblTBuscador = new JLabel("Tipo de buscador:");
		lblTBuscador.setFont(new Font("Arial", Font.BOLD, 14));
		lblTBuscador.setBounds(10, 11, 158, 29);
		panel.add(lblTBuscador);
		
		comboBox = new CustomComboBox<>();
		comboBox.addItem("Pedido");
		comboBox.addItem("Sala");
		comboBox.addItem("Mozo");
		comboBox.setBounds(178, 12, 189, 29);
		panel.add(comboBox);
		comboBox.addActionListener(this);
		
		lblBuscador = new JLabel("ID Pedido:");
		lblBuscador.setFont(new Font("Arial", Font.BOLD, 14));
		lblBuscador.setBounds(10, 51, 121, 29);
		panel.add(lblBuscador);
		
		txtFiltro = new CustomTextField();
		txtFiltro.setBounds(178, 49, 189, 40);
        txtFiltro.setPlaceholder("Buscador");
        panel.add(txtFiltro);
		txtFiltro.setColumns(10);
		txtFiltro.getDocument().addDocumentListener(this);
		
		CreacionTabla();
		ReiniciarTablaConFiltros();
	}

	public void CreacionTabla() {
	    String[] columns = {"IdPedido", "Sala", "NumeroMesa", "Fecha", "Total", "Mozo", "Ver Detalle"};

	    tableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return column == 6;
	        }
	    };

	    table_1 = new JTable(tableModel);
	    table_1.setRowHeight(40);
	    table_1.getTableHeader().setReorderingAllowed(false);
	    
	    scrollPane.setViewportView(table_1);

	    String rutaIconoDetalle = "/imgs/detalles.png";
	    ImageIcon iconoDetalle = new ImageIcon(getClass().getResource(rutaIconoDetalle));

	    table_1.getColumn("Ver Detalle").setCellRenderer(new ButtonRenderer(iconoDetalle));
	    table_1.getColumn("Ver Detalle").setCellEditor(new CustomButtonEditorTable(iconoDetalle,
	            e -> verDetallePedidoEstado(e)));
	    
        scrollPane.setViewportView(table_1);
        CustomTable.TableCustom.apply(scrollPane, CustomTable.TableCustom.TableType.DEFAULT);
	}

	public void verDetallePedidoEstado(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        int row = (int) sourceButton.getClientProperty("row");
        JTable tabla = (JTable) sourceButton.getClientProperty("table");
        TableModel model = tabla.getModel(); 
        Object value = model.getValueAt(row, 0); 
        int idPedido = Integer.parseInt(value.toString());
        Pedido pedido = pedidoController.obtenerPedido(idPedido);
        DetallePedidoView frame = new DetallePedidoView(pedido, null);
        frame.setLocationRelativeTo(this); 
        frame.setVisible(true); 
    }
	
	private void LogicaCambioTextoFiltro() {
		textoFiltro=txtFiltro.getText();
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

	public void ReiniciarTablaConFiltros() {
	    List<Pedido> pedidos = new ArrayList<Pedido>();
	    
	    if (textoFiltro.isEmpty()) {
	        pedidos = pedidoController.listarPedidos(false, false, true);
	    } else if (tipoFiltro.contentEquals("Pedido")) {
	        Pedido pedidoID = pedidoController.obtenerPedido(Integer.parseInt(textoFiltro));
	        if(pedidoID!=null)
	        	pedidos.add(pedidoID);
	    } else if (tipoFiltro.contentEquals("Mozo")) {
	        pedidos = pedidoController.listarPedidosPorMozo(textoFiltro);
	    } else if (tipoFiltro.contentEquals("Sala")) {
	        pedidos = pedidoController.listarPedidosPorSala(textoFiltro);
	    }
	    
	    pedidos.sort(Comparator.comparing(Pedido::getIdPedido));

	    tableModel.setRowCount(0);
	    
	    for (Pedido pedido : pedidos) {
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
		
    public void actualizarTabla() {
		ReiniciarTablaConFiltros();
		tableModel.fireTableDataChanged();
		revalidate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		 System.out.println(arg0.getSource());
		if (arg0.getSource() == comboBox) 
		 LogicaCambioTipoFiltro();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == txtFiltro.getDocument())
			LogicaCambioTextoFiltro();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == txtFiltro.getDocument())
			LogicaCambioTextoFiltro();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == txtFiltro.getDocument())
			LogicaCambioTextoFiltro();
	}
}
	