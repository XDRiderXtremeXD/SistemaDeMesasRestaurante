package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.SwingConstants;

import model.DetallePedido;
import model.Pedido;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.DetallePedidoController;
import controller.PedidoController;

import java.awt.Color;
import javax.swing.JButton;

public class DetallePedidoView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private List<DetallePedido> detallesPedido;  // Cambié el nombre de la variable a 'detallePedido'
    private PedidosActualesView pedidoView;
    private JTable table;
    private JScrollPane scrollPane_1 ;
    private DefaultTableModel model ;
    private JLabel lblEstado;
    private JLabel lblEstado_1;
    private Pedido pedido;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Pedido pedido=new Pedido();
                    DetallePedidoView frame = new DetallePedidoView(pedido, null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public DetallePedidoView(Pedido pedido,PedidosActualesView pedidoView) {
        DetallePedidoController detallePedidoController=new DetallePedidoController();
    	this.detallesPedido = detallePedidoController.listarDetallePedidosPorPedido(pedido.getIdPedido());
        this.pedidoView = pedidoView;
        this.pedido=pedido;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 940, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 67, 924, 425);
        contentPane.add(scrollPane);
        
        scrollPane_1 = new JScrollPane();
        scrollPane.setViewportView(scrollPane_1);
        
        CrearTabla();
        InicializarDatos();
       
        
        JLabel lblNewLabel = new JLabel("Detalle Pedido");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, 924, 68);
        contentPane.add(lblNewLabel);
        
        
        ViewEstado();
        ViewCambiarEstado();
    }
    
    

    private void ViewEstado() {
    	 String textoEstado="Estado: "+pedido.getEstado();
    	lblEstado = new JLabel(textoEstado);
        lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEstado.setBounds(0, 500, 300, 40);
        contentPane.add(lblEstado);
	}

	private void ViewCambiarEstado() {
		//ESTO ES PARA QUE FUNCIONE SOLO SI SE INGRESO UNA FRAMEVIDE DE PEDIDO 
		if(pedidoView==null)
			return;        
        
		String nuevoEstado="";
        if(pedido.getEstado().contentEquals("Pendiente") )
        	nuevoEstado="Entregado";
        if(pedido.getEstado().contentEquals("Entregado") )
        	nuevoEstado="Finalizado";
        if(nuevoEstado=="")
        	return;
        
        
        //SOLO SE MOSTRARA SI SE PUEDE REALIZAR ALGUN CAMBIO, SI ESTA EN FINALIZADO NO
        lblEstado_1 = new JLabel("Cambiar a --> ");
        lblEstado_1.setForeground(new Color(0, 128, 255));
        lblEstado_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblEstado_1.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblEstado_1.setBounds(0, 540, 113, 30);
        contentPane.add(lblEstado_1);
        
        JButton btnCambiarEstado = new JButton(nuevoEstado);
        btnCambiarEstado.setForeground(new Color(0, 128, 255));
        btnCambiarEstado.setFont(new Font("Verdana", Font.PLAIN, 14));
        btnCambiarEstado.setBounds(123, 540, 143, 30);
        contentPane.add(btnCambiarEstado);
        
        // Agregar acción al botón
        btnCambiarEstado.addActionListener(e -> {
            // Actualizar el estado del pedido
            PedidoController pedidoController=new PedidoController();
            String nuevoEstadoPedido=btnCambiarEstado.getText();
            pedido.setEstado(nuevoEstadoPedido);
            pedidoController.actualizarPedido(pedido);
            pedidoView.InicializarTablaDatos();
            this.dispose();
        });
        
	}

	private void InicializarDatos() {
        double totalPedido = 0.0;
        model.setRowCount(0);
        // Recorremos la lista de detalles del pedido y agregamos cada uno a la tabla
        for (DetallePedido detalle : detallesPedido) {
            // Agregar los datos del detalle a la tabla
            model.addRow(new Object[]{
                detalle.getIdPedido(),            // ID Pedido
                detalle.getNombreProducto(),      // Nombre Producto
                detalle.getPrecio(),              // Precio
                detalle.getCantidad(),            // Cantidad
                detalle.getComentario(),          // Comentario
                detalle.getSubTotal()             // SubTotal
            });
            
            // Sumar el subtotal de cada detalle al total
            totalPedido += detalle.getSubTotal();
        }

        // Mostrar el total en un JLabel
        JLabel lblTotal = new JLabel("Total: S/. " + String.format("%.2f", totalPedido));
        lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBounds(600, 510, 300, 30); // Ajusta la posición según sea necesario
        contentPane.add(lblTotal);
    }



	private void CrearTabla() {
		 table = new JTable();
		 model = new DefaultTableModel(
		        	new Object[][] {
		        	},
		        	new String[] {
		        		"ID Pedido", "Nombre Producto", "Precio", "Cantidad", "Comentario", "SubTotal"
		        	}
		        );
	        table.setModel(model);
	        scrollPane_1.setViewportView(table);
	}
}
