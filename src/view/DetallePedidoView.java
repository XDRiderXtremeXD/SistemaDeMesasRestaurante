package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.List;

import javax.swing.SwingConstants;

import model.DetallePedido;
import model.Pedido;
import utils.GenerarPdf;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import components.CustomButton;
import components.CustomTable;
import controller.DetallePedidoController;
import controller.PedidoController;

import java.awt.Color;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetallePedidoView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private List<DetallePedido> detallesPedido;
    private PedidosActualesView pedidoView;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JLabel lblEstado;
    private JLabel lblEstado_1;
    private Pedido pedido;
    private CustomButton btnPdf;

    public DetallePedidoView(Pedido pedido, PedidosActualesView pedidoView) {
    	setResizable(false);
    	setTitle("Detalle del Pedido");
        setIconImage(new ImageIcon(getClass().getResource("/imgs/LogoIcon.png")).getImage());
    	
        // Obtenemos los detalles del pedido
        DetallePedidoController detallePedidoController = new DetallePedidoController();
        this.detallesPedido = detallePedidoController.listarDetallePedidosPorPedido(pedido.getIdPedido());
        this.pedidoView = pedidoView;
        this.pedido = pedido;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 940, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(SystemColor.textHighlightText);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Crear y configurar el único scrollPane
        scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 67, 920, 425);
        contentPane.add(scrollPane);
        
        CrearTabla();
        InicializarDatos();
       
        JLabel lblNewLabel = new JLabel("Detalle del Pedido");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, 924, 68);
        contentPane.add(lblNewLabel);
        
        ViewEstado();
        ViewCambiarEstado();
        
        CustomTable.TableCustom.apply(scrollPane, CustomTable.TableCustom.TableType.DEFAULT);
        
        btnPdf = new CustomButton();
        btnPdf.addActionListener(this);
        btnPdf.setText("PDF");
        btnPdf.setShadowColor(new Color(0, 123, 255));
        btnPdf.setRippleColor(Color.WHITE);
        btnPdf.setForeground(new Color(245, 245, 245));
        btnPdf.setBackground(new Color(0, 123, 255));
        btnPdf.setBounds(309, 540, 143, 40);
        contentPane.add(btnPdf);
    }
    
    private void ViewEstado() {
        String textoEstado = "Estado: " + pedido.getEstado();
        lblEstado = new JLabel(textoEstado);
        lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEstado.setBounds(20, 500, 300, 40);
        contentPane.add(lblEstado);
    }
    
    private void ViewCambiarEstado() {
        // Solo se ejecuta si se ingresó una vista de pedidos
        if (pedidoView == null)
            return;        
        
        String nuevoEstado = "";
        if (pedido.getEstado().contentEquals("Pendiente"))
            nuevoEstado = "Entregado";
        if (pedido.getEstado().contentEquals("Entregado"))
            nuevoEstado = "Finalizado";
        if (nuevoEstado.equals(""))
            return;
        
        // Mostrar la opción de cambiar el estado (si aplica)
        lblEstado_1 = new JLabel("Cambiar a --> ");
        lblEstado_1.setForeground(new Color(0, 128, 255));
        lblEstado_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblEstado_1.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblEstado_1.setBounds(26, 540, 113, 30);
        contentPane.add(lblEstado_1);
        
        CustomButton btnCambiarEstado = new CustomButton();
        btnCambiarEstado.setBackground(new Color(0, 123, 255));
        btnCambiarEstado.setForeground(new Color(245, 245, 245));
        btnCambiarEstado.setRippleColor(new Color(255, 255, 255));
        btnCambiarEstado.setShadowColor(new Color(0, 123, 255));
        btnCambiarEstado.setText(nuevoEstado);
        btnCambiarEstado.setBounds(143, 540, 143, 40);
        contentPane.add(btnCambiarEstado);
        
        // Acción para actualizar el estado del pedido
        btnCambiarEstado.addActionListener(e -> {
            PedidoController pedidoController = new PedidoController();
            String nuevoEstadoPedido = btnCambiarEstado.getText();
            pedido.setEstado(nuevoEstadoPedido);
            pedidoController.actualizarPedido(pedido);
            pedidoView.inicializarTablaDatos();
            this.dispose();
        });
    }
    
    private void InicializarDatos() {
        double totalPedido = 0.0;
        model.setRowCount(0);
        
        for (DetallePedido detalle : detallesPedido) {
            model.addRow(new Object[]{
                detalle.getIdPedido(),
                detalle.getNombreProducto(),
                detalle.getPrecio(),
                detalle.getCantidad(),
                detalle.getComentario(),
                detalle.getSubTotal()
            });
            totalPedido += detalle.getSubTotal();
        }
    
        JLabel lblTotal = new JLabel("Total: S/. " + String.format("%.2f", totalPedido));
        lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBounds(600, 510, 300, 30);
        contentPane.add(lblTotal);
    }
    
    private void CrearTabla() {
        table = new JTable();
        model = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "ID Pedido", "Nombre Producto", "Precio", "Cantidad", "Comentario", "SubTotal"
            }
        );
        table.setModel(model);
        scrollPane.setViewportView(table);
    }
    
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPdf) {
			actionPerformedBtnPdf(e);
		}
	}
	
	protected void actionPerformedBtnPdf(ActionEvent e) {
		int idPedido = pedido.getIdPedido();
		
		PedidoController pedidoController = new PedidoController();
		DetallePedidoController detallePController = new DetallePedidoController();
	    GenerarPdf pdfPrueba = new GenerarPdf(pedidoController, detallePController );
	    pdfPrueba.generarPDF(idPedido);
	}
}
