package view;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.PedidoController;
import model.Pedido;

import java.awt.*;
import java.util.List;

public class Estadistica extends JPanel {
/*
    private static final long serialVersionUID = 1L;
    private PedidoController pedidoController;

    /**
     * Create the panel.
     */
	/* public Estadistica() {
        setLayout(new BorderLayout(0, 0));
        
        pedidoController = new PedidoController();

        // Panel para la cabecera
        JPanel head = new JPanel();
        add(head, BorderLayout.NORTH);

        // JComboBox en la cabecera
        JComboBox comboBox = new JComboBox();
        head.add(comboBox);

        // Crear un dataset (conjunto de datos) para el gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        List<Pedido> pedidos = pedidoController.listarPedidos(false, false, true);

        // Agregar datos (categoría, subcategoría, valor)
        dataset.addValue(10, "Ventas", "Enero");
        dataset.addValue(15, "Ventas", "Febrero");
        dataset.addValue(30, "Ventas", "Marzo");
        dataset.addValue(25, "Ventas", "Abril");

        // Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Ventas",   // Título del gráfico
                "Mes",                 // Etiqueta del eje X
                "Ventas",              // Etiqueta del eje Y
                dataset                // Datos
        );

        // Panel para el gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600)); // Tamaño del gráfico

        // Agregar el gráfico al panel con desplazamiento
        JScrollPane scrollPane = new JScrollPane(chartPanel);
        add(scrollPane, BorderLayout.CENTER); // Agregar el panel con gráfico a la posición central
    }*/
/*
    // Método principal para probar la visualización
    public static void main(String[] args) {
        // Crear la ventana (JFrame)
        JFrame frame = new JFrame("Gráfico 2D en JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear el panel de estadísticas
        Estadistica panel = new Estadistica();
        
        // Agregar el panel al JFrame
        frame.getContentPane().add(panel);
        
        // Ajustar el tamaño del JFrame
        frame.setSize(800, 600);
        frame.setVisible(true);
    }*/
}
