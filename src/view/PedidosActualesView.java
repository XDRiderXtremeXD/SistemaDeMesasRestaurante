package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import components.CustomButton;
import components.CustomButtonEditorTable;
import components.CustomTable;
import controller.PedidoController;
import model.Pedido;
import utils.ButtonRenderer;
import utils.TiempoRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.List;

public class PedidosActualesView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private PedidoController pedidoController;
    private Timer timer;
    private boolean pendiente;
    private boolean entregado;
    private boolean finalizado;

    public PedidosActualesView(boolean pendiente, boolean entregado, boolean finalizado) {
        this.pendiente = pendiente;
        this.entregado = entregado;
        this.finalizado = finalizado;
        pedidoController = new PedidoController();

        setPreferredSize(new Dimension(1427, 675));
        setLayout(new BorderLayout());

        creacionTabla();
        inicializarTablaDatos();
        startTimer();
    }

    private void creacionTabla() {
        String[] columns = {"IdPedido", "Sala", "NumeroMesa", "Fecha", "Total", "Estado", "Usuario", "Tiempo", "Ver/Estado"};
        tableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        CustomTable.TableCustom.apply(scrollPane, CustomTable.TableCustom.TableType.DEFAULT);
        add(scrollPane, BorderLayout.CENTER);
        
            String rutaIconoDetalle = "/imgs/detalles.png";
    		ImageIcon iconoDetalle = new ImageIcon(getClass().getResource(rutaIconoDetalle));
    				
    		table.getColumn("Ver/Estado").setCellRenderer(new ButtonRenderer(iconoDetalle));
    		table.getColumn("Ver/Estado").setCellEditor(new CustomButtonEditorTable(iconoDetalle,
    				e -> verDetallePedidoEstado(e, this))); 
            
        
        scrollPane.setViewportView(table);

    }
    
    public void verDetallePedidoEstado(ActionEvent e, PedidosActualesView pedidoView) {
    	try {
    	    System.out.println("Método verDetallePedidoEstado ejecutado.");
    	    
    	    JButton sourceButton = (JButton) e.getSource();
    	    int row = (int) sourceButton.getClientProperty("row");
    	    int column = (int) sourceButton.getClientProperty("column");
    	    JTable tabla = (JTable) sourceButton.getClientProperty("table");
    	    TableModel model = tabla.getModel(); 
    	    Object value = model.getValueAt(row, 0); 

    	    int idPedido = Integer.parseInt(value.toString());
    	    Pedido pedido = pedidoController.obtenerPedido(idPedido);

    	    DetallePedidoView frame = new DetallePedidoView(pedido, pedidoView, null);
    	    frame.setLocationRelativeTo(pedidoView);
    	    frame.setVisible(true);

    	    
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.out.println("Ocurrió un error: " + ex.getMessage());
    	}


    }

    public void inicializarTablaDatos() {
        List<Pedido> pedidos = pedidoController.listarPedidos(pendiente, entregado, finalizado);
        
        // Ordenar la lista por ID en orden ascendente
        pedidos.sort(Comparator.comparing(Pedido::getIdPedido));

        tableModel.setRowCount(0);
        for (Pedido pedido : pedidos) {
            tableModel.addRow(new Object[]{
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

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimes();
            }
        });
        timer.start();
    }

    private void updateTimes() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            LocalDateTime fechaPedido = (LocalDateTime) tableModel.getValueAt(i, 3);
            tableModel.setValueAt(calcularTiempoTranscurrido(fechaPedido), i, 7);
        }
    }
    public void actualizarTabla() {
        inicializarTablaDatos(); // Vuelve a cargar los datos
        tableModel.fireTableDataChanged(); // Notifica que los datos han cambiado
        revalidate();
        repaint();
    }


    private String calcularTiempoTranscurrido(LocalDateTime fecha) {
        Duration duracion = Duration.between(fecha, LocalDateTime.now());
        long horas = duracion.toHours() - 5;
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}