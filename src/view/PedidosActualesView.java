	
	package view;
	import javax.swing.*;
	import javax.swing.table.DefaultTableModel;
	
	import controller.PedidoController;
	import model.Pedido;
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

    @SuppressWarnings("serial")
	public PedidosActualesView(boolean pendiente,boolean entregado,boolean finalizado) {
        setLayout(null);
        pedidoController = new PedidoController();
        this.pendiente=pendiente;
        this.entregado=entregado;
        this.finalizado=finalizado;
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 1430, 900);
        add(scrollPane);
        
        tableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] { "IdPedido", "Sala", "NumeroMesa", "Fecha", "Total", "Estado", "Usuario", "Tiempo", "Ver/Estado" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que la tabla sea de solo lectura
            }
        };
        
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);
        
        InicializarTablaDatos();
        iniciarTimer();
    }

    private void InicializarTablaDatos() {
    	
        List<Pedido> pedidos=pedidoController.listarPedidos(true,true,true);
        
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
