	
package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import components.CustomButtonEditorTable;
import controller.PedidoController;
import model.Pedido;
import utils.ButtonRenderer;
import utils.TiempoRenderer;

import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.time.Duration;
	import java.time.LocalDateTime;
	import java.util.List;
	
	

public class PedidosActualesView extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private PedidoController pedidoController;
    private Timer timer; // Timer para actualizar el tiempo
    private boolean pendiente;
    private boolean entregado;
    private boolean finalizado;
    private JScrollPane scrollPane;

	public PedidosActualesView(boolean pendiente,boolean entregado,boolean finalizado) {
        setLayout(null);
        pedidoController = new PedidoController();
        this.pendiente=pendiente;
        this.entregado=entregado;
        this.finalizado=finalizado;
        
        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 1430, 900);
        add(scrollPane);
        

        CreacionTabla();
        InicializarTablaDatos();
        iniciarTimer();
    }

    public void CreacionTabla() {
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "IdPedido", "Sala", "NumeroMesa", "Fecha", "Total", "Estado", "Usuario", "Tiempo", "Ver/Estado" }
            ) {
            	@Override
    			public boolean isCellEditable(int row, int column) {
    				return column == 8; 
    			}
            };
            table = new JTable(tableModel);
            table.setRowHeight(40); 
    		// Rutas de los iconos
    		String rutaIconoDetalle = "/resources/detalles.png";
    		ImageIcon iconoDetalle = new ImageIcon(getClass().getResource(rutaIconoDetalle));
    				
    		table.getColumn("Ver/Estado").setCellRenderer(new ButtonRenderer(iconoDetalle));
    		table.getColumn("Ver/Estado").setCellEditor(new CustomButtonEditorTable(new JButton(), iconoDetalle,
    				e -> verDetallePedidoEstado(e, this))); 
            
            scrollPane.setViewportView(table);
    }
    
    public void verDetallePedidoEstado(ActionEvent e, PedidosActualesView pedidoView) {
        JButton sourceButton = (JButton) e.getSource();
        int row = (int) sourceButton.getClientProperty("row");
        int column = (int) sourceButton.getClientProperty("column");
        JTable tabla = (JTable) sourceButton.getClientProperty("table");
        TableModel model = tabla.getModel(); 
        Object value = model.getValueAt(row, 0); 
        System.out.println("Editando fila: " + row + ", columna: " + column+", valor: "+value.toString());
        int idPedido=Integer.parseInt(value.toString());
        Pedido pedido=pedidoController.obtenerPedido(idPedido);
        
        DetallePedidoView frame = new DetallePedidoView(pedido,pedidoView);
        frame.setLocationRelativeTo(pedidoView); 
        frame.setVisible(true); 
    }
    
    public void InicializarTablaDatos() {
    	
        List<Pedido> pedidos=pedidoController.listarPedidos(pendiente,entregado,finalizado);
        tableModel.setRowCount(0);
        for (Pedido pedido : pedidos) {
            tableModel.addRow(new Object[] {
                pedido.getIdPedido(),
                pedido.getNombreSala(),
                pedido.getNumeroMesa(),
                pedido.getFecha(),
                pedido.getTotal(),
                pedido.getEstado(),
                pedido.getUsuario(),
                calcularTiempoTranscurrido(pedido.getFecha()), 
                "Ver/Estado"
            });
        }

        table.getColumnModel().getColumn(7).setCellRenderer(new TiempoRenderer());
    }


    private void iniciarTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempos();
            }
        });
        timer.start(); // Iniciar el temporizador
    }

    private void actualizarTiempos() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
        	LocalDateTime fechaPedido = (LocalDateTime) tableModel.getValueAt(i, 3);
            tableModel.setValueAt(calcularTiempoTranscurrido(fechaPedido), i, 7); 
        }
    }

    private String calcularTiempoTranscurrido(LocalDateTime localDateTime) {
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between( localDateTime, ahora);
        
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}