package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


import controller.SalaController;

public class MesasView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelBase;
    SalaController controlador;

    JTabbedPane tabbedPane;
    RealizarPedidoView realizarPedidoView;  // Asegúrate de que tienes esta clase
    private int cantidadMesas;

    public MesasView(SalaController controlador,JTabbedPane tabbedPane, RealizarPedidoView realizarPedidoView) {
        this.controlador = controlador;
    	this.tabbedPane = tabbedPane;
        this.realizarPedidoView = realizarPedidoView;  // Inicializar la vista de platos
        initComponents();
    }

    private void initComponents() {
        setSize(1100, 600);
        setLayout(null);
        panelBase = new JPanel();
        
        panelBase.setBounds(22, 23, 1056, 552);
        add(panelBase);
        panelBase.setLayout(null); 
    }
    
    
   
    public void agregarMesasPanel(int cantidad) {
    	panelBase.removeAll();  // Limpiar cualquier mesa anterior
        this.cantidadMesas = cantidad;  // Actualizar la cantidad de mesas

        for (int i = 1; i <= cantidadMesas; i++) {
            JButton btnMesa = new JButton("Mesa " + i);  // Asignar nombre a cada mesa
            btnMesa.setBounds(10, i * 40, 200, 30);  // Ajusta la posición de cada botón

            btnMesa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabbedPane.setSelectedComponent(realizarPedidoView);  // Cambiar a la vista de platos
                }
            });

            panelBase.add(btnMesa);  // Añadir el botón al panel
        }

        panelBase.revalidate();  // Actualizar el panel de mesas
        panelBase.repaint();  // Redibujar el panel
    }


    // Método para actualizar las mesas (por ejemplo, cambiar nombre o características)
    public void actualizarMesaPanel(String nombreAntiguo, String nuevoNombre) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreAntiguo)) {  // Busca la mesa con el nombre antiguo
                    button.setText(nuevoNombre);  // Actualiza el nombre de la mesa
                    panelBase.revalidate();  // Revalidar para actualizar el diseño
                    panelBase.repaint();  // Vuelve a pintar el panel
                    break;
                }
            }
        }
    }

    // Método para eliminar una mesa
    public void eliminarMesaDePanel(String nombreMesa) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreMesa)) {  // Si encontramos la mesa por su nombre
                    panelBase.remove(button);  // Eliminar el botón de la mesa
                    panelBase.revalidate();  // Revalidar para actualizar el diseño
                    panelBase.repaint();  // Vuelve a pintar el panel
                    break;
                }
            }
        }
    }
}
