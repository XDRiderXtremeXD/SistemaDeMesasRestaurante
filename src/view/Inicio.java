package view;

import javax.swing.*;
import controller.SalaController;
import model.Sala;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Inicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel panelBase;  
    SalaController controlador;
    MesasView mesasView;
    JTabbedPane tabbedPane; 
    JScrollPane scrollPane;


    public Inicio(SalaController controlador, MesasView mesasView, JTabbedPane tabbedPane) {
        this.controlador = controlador;
        this.mesasView = mesasView;
        this.tabbedPane = tabbedPane;


        initComponents();
        cargarSalasEnPanel();
    }


    private void initComponents() {

        setSize(1100, 600);
        setLayout(null);
        panelBase = new JPanel();

        panelBase.setBounds(22, 23, 1056, 552);
        add(panelBase);
        panelBase.setLayout(null); 

    }

    // LISTAR las salas
    private void cargarSalasEnPanel() {
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            agregarSalaPanel(sala);
        }
    }

    // AGREGAR la sala como botón
    public void agregarSalaPanel(Sala sala) {
        JButton salaButton = new JButton(sala.getNombre());
        salaButton.setFont(new Font("Arial", Font.BOLD, 14));
        salaButton.setBounds(0, panelBase.getComponentCount() * 40, 200, 30); 

        salaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    tabbedPane.setSelectedComponent(mesasView);
            }

        });

        panelBase.add(salaButton);
        panelBase.revalidate();  
        panelBase.repaint(); 
    }



    // Eliminar la sala
    public void eliminarSalaDePanel(String nombreSala) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreSala)) {
                    panelBase.remove(button);
                    panelBase.revalidate();
                    panelBase.repaint();
                    break;
                }
            }
        }
    }

    // ACTUALIZAR la sala
    public void actualizarSalaPanel(String nombreAntiguo, String nuevoNombre) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(nombreAntiguo)) { 
                    button.setText(nuevoNombre);  
                    panelBase.revalidate();  
                    panelBase.repaint();  
                    break;
                }
            }
        }
    }
}