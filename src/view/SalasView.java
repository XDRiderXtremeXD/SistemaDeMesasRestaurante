package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import components.*;
import controller.SalaController;
import model.Sala;
import raven.glasspanepopup.GlassPanePopup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SalasView extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Inicio inicioView;
    private MesasView mesasView;
    private SalaController controlador;

    private JTable tablaSalas;
    private DefaultTableModel modelo;
    private CustomButton btnAgregar, btnEliminar, btnActualizar;
    private CustomTextField txtNombre, txtMesas;

    public SalasView(SalaController controlador, Inicio inicioView, MesasView mesasView) {
        this.controlador = controlador;
        this.inicioView = inicioView;
        this.mesasView = mesasView;

        setPreferredSize(new Dimension(1427, 675));
        setLayout(new BorderLayout());

        initComponents();
        cargarDatosTabla();
    }

    private void initComponents() {
        String[] columnas = {"ID", "Nombre", "Mesas"};
        modelo = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaSalas = new JTable(modelo);
        tablaSalas.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaSalas);
        CustomTable.TableCustom.apply(scrollPane, CustomTable.TableCustom.TableType.DEFAULT);

        tablaSalas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaSalas.getSelectedRow();
                if (fila != -1) {
                    txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                    txtMesas.setText(modelo.getValueAt(fila, 2).toString());
                    btnActualizar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                }
            }
        });

        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel formularioPanel = new JPanel();
        formularioPanel.setBackground(Color.WHITE);
        formularioPanel.setPreferredSize(new Dimension(400, 0));
        formularioPanel.setLayout(null);

        JLabel lblTitulo = new JLabel("Gestión de Salas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(50, 30, 300, 30);
        formularioPanel.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre de la Sala");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNombre.setBounds(55, 80, 295, 20);
        formularioPanel.add(lblNombre);

        txtNombre = new CustomTextField();
        txtNombre.setBounds(50, 110, 300, 40);
        txtNombre.setPlaceholder("Ingrese el nombre de la sala");
        formularioPanel.add(txtNombre);

        JLabel lblNumMesas = new JLabel("Cantidad de Mesas");
        lblNumMesas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNumMesas.setBounds(55, 160, 295, 20);
        formularioPanel.add(lblNumMesas);

        txtMesas = new CustomTextField();
        txtMesas.setBounds(50, 190, 300, 40);
        txtMesas.setPlaceholder("Ej: 5");
        formularioPanel.add(txtMesas);

        btnAgregar = new CustomButton();
        btnAgregar.setBackground(new java.awt.Color(30, 180, 114));
        btnAgregar.setForeground(new java.awt.Color(245, 245, 245));
        btnAgregar.setRippleColor(new java.awt.Color(255, 255, 255));
        btnAgregar.setShadowColor(new java.awt.Color(30, 180, 114));
        btnAgregar.setText("Agregar Sala");
        btnAgregar.setBounds(125, 244, 150, 46);
        btnAgregar.addActionListener(this);
        formularioPanel.add(btnAgregar);

        btnEliminar = new CustomButton();
        btnEliminar.setBackground(new java.awt.Color(253, 83, 83));
        btnEliminar.setForeground(new java.awt.Color(245, 245, 245));
        btnEliminar.setRippleColor(new java.awt.Color(255, 255, 255));
        btnEliminar.setShadowColor(new java.awt.Color(253, 83, 83));
        btnEliminar.setText("Eliminar Sala");
        btnEliminar.setBounds(125, 304, 150, 46);
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(this);
        formularioPanel.add(btnEliminar);

        btnActualizar = new CustomButton();
        btnActualizar.setBackground(new Color(0, 123, 255));
        btnActualizar.setForeground(new Color(245, 245, 245));
        btnActualizar.setRippleColor(new Color(255, 255, 255));
        btnActualizar.setShadowColor(new Color(0, 123, 255));
        btnActualizar.setText("Actualizar Sala");
        btnActualizar.setBounds(125, 364, 150, 46);
        btnActualizar.setEnabled(false);
        btnActualizar.addActionListener(this);
        formularioPanel.add(btnActualizar);

        add(tablaPanel, BorderLayout.CENTER);
        add(formularioPanel, BorderLayout.EAST);
    }

    private void cargarDatosTabla() {
        modelo.setRowCount(0);
        List<Sala> salas = controlador.listarSalas();
        for (Sala sala : salas) {
            modelo.addRow(new Object[]{sala.getIdSala(), sala.getNombre(), sala.getMesas()});
        }
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            String nombre = txtNombre.getText();
            String numMesasText = txtMesas.getText();

            if (nombre.isEmpty() || numMesasText.isEmpty()) {
            	CustomAlert.showAlert("Error", "Todos los campos son obligatorios", "error");
                return;
            }

            try {
                int numMesas = Integer.parseInt(numMesasText);
                Sala nuevaSala = new Sala(0, nombre, numMesas);
                Sala salaRegistrada = controlador.registrarSala(nuevaSala);
                cargarDatosTabla();

                inicioView.agregarSalaPanel(nuevaSala);
                mesasView.agregarMesasPanel(salaRegistrada.getIdSala(), numMesas);

                limpiarDatos();
                
                CustomAlert.showAlert("Éxito", "Sala agregada correctamente", "success");
            } catch (NumberFormatException ex) {
            	CustomAlert.showAlert("Error", "Ingrese un número válido en Mesas", "error");
            }

        } else if (e.getSource() == btnActualizar) {
            int filaSeleccionada = tablaSalas.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idSala = (int) modelo.getValueAt(filaSeleccionada, 0);
    	        String nombreNuevo = txtNombre.getText();
                String nuevoNumMesasText = txtMesas.getText();

                if (nombreNuevo.isEmpty() || nuevoNumMesasText.isEmpty()) {
                	CustomAlert.showAlert("Error", "Todos los campos son obligatorios", "error");
                    return;
                }

                try {
                    int nuevoNumMesas = Integer.parseInt(nuevoNumMesasText);
                    Sala salaActualizada = controlador.actualizarSala(new Sala(idSala, nombreNuevo, nuevoNumMesas));
                    cargarDatosTabla();        
                    
                    inicioView.actualizarSalaPanel(salaActualizada.getIdSala(), nombreNuevo, salaActualizada.getMesas());
                    mesasView.actualizarNumeroMesas(salaActualizada.getIdSala(), salaActualizada.getMesas());
                    
                    limpiarDatos();
                    
                    CustomAlert.showAlert("Éxito", "Sala actualizada correctamente", "success");
                } catch (NumberFormatException ex) {
                	CustomAlert.showAlert("Error", "Ingrese un número válido en Mesas", "error");
                }
            }

        } else if (e.getSource() == btnEliminar) {
            int filaSeleccionada = tablaSalas.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idSala = (int) modelo.getValueAt(filaSeleccionada, 0);
                String nombreSala = tablaSalas.getValueAt(filaSeleccionada, 1).toString();
                
                CustomAlert.showConfirmationAlert("Confirmación", "¿Estás seguro de eliminar esta sala",
        				evt -> {
        			        // Acción al presionar "Aceptar"
        					controlador.eliminarSala(idSala);
            	            cargarDatosTabla();
            	            inicioView.eliminarSalaDePanel(nombreSala);
            	            limpiarDatos();
        					GlassPanePopup.closePopupLast();
        					CustomAlert.showAlert("Éxito", "Sala eliminada correctamente", "success");
        			    },
        			    evt -> {
        			        // Acción al presionar "Cancelar"
        			        GlassPanePopup.closePopupLast();
        			    }
                );
            }
        }
    }

    //OTROS MÉTODOS
    // Método para mostrar una sala específica
       public static void mostrarSala(Sala sala) {
           System.out.println("Detalles de la Sala:");
           System.out.println("ID: " + sala.getIdSala());
           System.out.println("Nombre: " + sala.getNombre());
           System.out.println("Mesas: " + sala.getMesas());
       }
       
       public void limpiarDatos() {
       	txtNombre.setText("");
           txtMesas.setText("");
       }
}
