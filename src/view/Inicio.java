package view;

import javax.swing.*;

import components.CustomButton;
import controller.SalaController;
import model.Sala;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Inicio extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel panelBase;  
    private SalaController controlador;
    private MesasView mesasView;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane;

    public Inicio(SalaController controlador, MesasView mesasView, JTabbedPane tabbedPane) {
        this.controlador = controlador;
        this.mesasView = mesasView;
        this.tabbedPane = tabbedPane;

        initComponents();
        cargarSalasEnPanel();
    }

    private void initComponents() {
        setSize(1100, 600);
        setLayout(new BorderLayout()); 

        panelBase = new JPanel();
        panelBase.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        scrollPane = new JScrollPane(panelBase);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1050, 100));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarSalasEnPanel() {
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            agregarSalaPanel(sala);
        }
    }

    public void agregarSalaPanel(final Sala sala) {
        CustomButton salaButton = new CustomButton();
        
        ImageIcon imagenSala = new ImageIcon(getClass().getResource("/imgs/sala.png"));
        Image img = imagenSala.getImage();
        Image newImg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(newImg);
        
        salaButton.setIcon(iconoEscalado);
        salaButton.setText(sala.getNombre());
        salaButton.setFont(new Font("Arial", Font.BOLD, 15));
        salaButton.setPreferredSize(new Dimension(200, 500)); 
        salaButton.setBackground(Color.BLACK);
        salaButton.setForeground(Color.WHITE);
        salaButton.setShadowColor(new Color(169, 169, 169, 255));
        salaButton.setShadowBlurRadius(10);
        salaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salaButton.setRound(30);
        salaButton.setLayout(new BoxLayout(salaButton, BoxLayout.Y_AXIS));
        salaButton.setHorizontalAlignment(SwingConstants.CENTER);
        salaButton.setIconTextGap(10);
        
        salaButton.putClientProperty("salaId", sala.getIdSala());
        salaButton.putClientProperty("mesas", sala.getMesas());
        
        salaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                int id = (Integer) btn.getClientProperty("salaId");
                int mesas = (Integer) btn.getClientProperty("mesas");
                mesasView.agregarMesasPanel(id, mesas);
                tabbedPane.setSelectedComponent(mesasView);
            }
        });
        
        panelBase.add(salaButton);
        panelBase.setBackground(SystemColor.textHighlightText);
        panelBase.revalidate();
        panelBase.repaint();
    }

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

    public void actualizarSalaPanel(int salaId, String nuevoNombre, int nuevaCantidad) {
        for (Component component : panelBase.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                Object idObj = button.getClientProperty("salaId");
                if (idObj instanceof Integer) {
                    int id = (Integer) idObj;
                    if (id == salaId) {
                        button.setText(nuevoNombre);
                        button.putClientProperty("mesas", nuevaCantidad);
                        panelBase.revalidate();
                        panelBase.repaint();
                        break;
                    }
                }
            }
        }
    }
}