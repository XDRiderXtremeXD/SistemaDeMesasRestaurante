package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import model.DetallePedido;
import javax.swing.JScrollPane;

public class PedidoDetalleView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private List<DetallePedido> detallesPedido;  // Cambié el nombre de la variable a 'detallePedido'
    private PedidosActualesView pedidoView;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	List<DetallePedido> listaDetallePedido = new ArrayList<>();
                    PedidoDetalleView frame = new PedidoDetalleView(listaDetallePedido, null);
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
    public PedidoDetalleView(List<DetallePedido> listaDetallePedido, PedidosActualesView pedidoView) {
        this.detallesPedido = listaDetallePedido;
        this.pedidoView = pedidoView;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 940, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 67, 924, 425);
        contentPane.add(scrollPane);
        
        JLabel lblNewLabel = new JLabel("Detalle Pedido");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 11, 924, 28);
        contentPane.add(lblNewLabel);


    }
}
