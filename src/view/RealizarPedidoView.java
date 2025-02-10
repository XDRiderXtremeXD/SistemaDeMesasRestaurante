package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import components.CustomAlert;
import components.CustomButton;
import components.CustomTable;
import components.CustomTextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Plato;
import java.awt.*;
import java.text.DecimalFormat;

public class RealizarPedidoView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tablaPedidos, tablaPlatos;
    private DefaultTableModel tableModelPedidos, tableModelPlatos;
    private CustomTextField txtComentario, txtBuscar;
    private CustomButton btnAgregarComen, btnEliminarPedido, btnRealizarPedido, btnAgregarPlato;
    private JScrollPane scrollPanePedidos, scrollPanePlatos;
    private JLabel lblMontoPagar;
    private Map<Integer, Integer> mapIdPedidoPlato = new HashMap<>();
    public int salaId, numeroMesa;

    public RealizarPedidoView(List<Plato> platos) {
        setPreferredSize(new Dimension(1427, 675));
        setLayout(new GridBagLayout());

        // Panel izquierdo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(SystemColor.textHighlightText);
        panelIzquierdo.setPreferredSize(new Dimension(713, 675));
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

        // Título "Comentarios"
        JLabel lblPedido = new JLabel("Realizar Pedido", SwingConstants.CENTER);
        lblPedido.setFont(new Font("Arial", Font.BOLD, 18));
        lblPedido.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(lblPedido);
        panelIzquierdo.add(Box.createVerticalStrut(15));

        // Comentario (campo de texto)
        txtComentario = new CustomTextField();
        txtComentario.setPlaceholder("Agrega un comentario...");

        // Botón Agregar
        btnAgregarComen = new CustomButton();
        btnAgregarComen.setText("Agregar");
        btnAgregarComen.setFocusPainted(false);
        btnAgregarComen.setBackground(new java.awt.Color(30, 180, 114));
        btnAgregarComen.setForeground(new java.awt.Color(245, 245, 245));
        btnAgregarComen.setRippleColor(new java.awt.Color(255, 255, 255));
        btnAgregarComen.setShadowColor(new java.awt.Color(30, 180, 114));
        btnAgregarComen.setEnabled(false);
        btnAgregarComen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAgregarComen.setPreferredSize(new Dimension(90, 38));
        
        btnAgregarComen.addActionListener(e -> agregarComentarioAlPedido());

        btnEliminarPedido = new CustomButton();
        btnEliminarPedido.setText("Eliminar");
        btnEliminarPedido.setFocusPainted(false);
        btnEliminarPedido.setBackground(new java.awt.Color(253, 83, 83));
        btnEliminarPedido.setForeground(new java.awt.Color(245, 245, 245));
        btnEliminarPedido.setRippleColor(new java.awt.Color(255, 255, 255));
        btnEliminarPedido.setShadowColor(new java.awt.Color(253, 83, 83));
        btnEliminarPedido.setEnabled(false);
        btnEliminarPedido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminarPedido.setPreferredSize(new Dimension(90, 38));
        
        btnEliminarPedido.addActionListener(e -> eliminarPlatoDelPedido());

        // Panel para el comentario y los dos botones
        JPanel panelComentario = new JPanel();
        panelComentario.setBackground(SystemColor.textHighlightText);
        panelComentario.setLayout(new BoxLayout(panelComentario, BoxLayout.X_AXIS));

        txtComentario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnAgregarComen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnEliminarPedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        panelComentario.add(Box.createRigidArea(new Dimension(90, 0)));
        panelComentario.add(txtComentario);
        panelComentario.add(Box.createRigidArea(new Dimension(10, 0)));
        panelComentario.add(btnAgregarComen);
        panelComentario.add(Box.createRigidArea(new Dimension(10, 0)));
        panelComentario.add(btnEliminarPedido);
        panelComentario.add(Box.createRigidArea(new Dimension(90, 0)));

        panelIzquierdo.add(panelComentario);

        // Tabla de Pedidos
        String[] columnNames = {"ID", "Plato", "Precio", "Cant", "SubTotal", "Comentario"};
        tableModelPedidos = new DefaultTableModel(columnNames, 0);
        tablaPedidos = new JTable(tableModelPedidos);
        scrollPanePedidos = new JScrollPane(tablaPedidos);
        CustomTable.TableCustom.apply(scrollPanePedidos, CustomTable.TableCustom.TableType.DEFAULT);
        
        tablaPedidos.setDefaultEditor(Object.class, null);
        tablaPedidos.getTableHeader().setResizingAllowed(false);
        tablaPedidos.getTableHeader().setReorderingAllowed(false);
        tablaPedidos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaPedidos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaPedidos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaPedidos.getColumnModel().getColumn(3).setPreferredWidth(60);
        tablaPedidos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaPedidos.getColumnModel().getColumn(5).setPreferredWidth(200);

        // Panel contenedor para la tabla
        JPanel panelTablaPedidos = new JPanel();
        panelIzquierdo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(211, 211, 211)));
        panelTablaPedidos.setBackground(SystemColor.textHighlightText);
        panelTablaPedidos.setLayout(new BoxLayout(panelTablaPedidos, BoxLayout.X_AXIS));
        
        panelTablaPedidos.add(Box.createRigidArea(new Dimension(30, 0)));
        panelTablaPedidos.add(scrollPanePedidos);
        panelTablaPedidos.add(Box.createRigidArea(new Dimension(30, 0)));

        panelIzquierdo.add(Box.createVerticalStrut(15));
        panelIzquierdo.add(panelTablaPedidos);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	veriConBtnAgregarCom();
                	actuTextoBtnComen();
                    btnEliminarPedido.setEnabled(tablaPedidos.getSelectedRow() != -1);
                }
            }
        });
        
        // Listener para cambios en el campo de texto txtComentario
        txtComentario.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            	veriConBtnAgregarCom();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	veriConBtnAgregarCom();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	veriConBtnAgregarCom();
            }
        });
        
        // Panel para el total y botón realizar pedido
        JPanel panelTotal = new JPanel();
        panelTotal.setBackground(SystemColor.textHighlightText);
        panelTotal.setLayout(new BoxLayout(panelTotal, BoxLayout.Y_AXIS));
        panelTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Label "Total a Pagar"
        JLabel lblTotal = new JLabel("Total a Pagar");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Label monto "00.00"
        lblMontoPagar = new JLabel("S/. 00,00");
        lblMontoPagar.setFont(new Font("Arial", Font.BOLD, 24));
        lblMontoPagar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Botón Realizar Pedido
        btnRealizarPedido = new CustomButton();
        btnRealizarPedido.setText("Realizar Pedido");
        btnRealizarPedido.setFocusPainted(false);
        btnRealizarPedido.setBackground(new Color(0, 123, 255));
        btnRealizarPedido.setForeground(new Color(245, 245, 245));
        btnRealizarPedido.setRippleColor(new Color(255, 255, 255));
        btnRealizarPedido.setShadowColor(new Color(0, 123, 255));
        btnRealizarPedido.setEnabled(false);
        btnRealizarPedido.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRealizarPedido.setPreferredSize(new Dimension(170, 40));
        btnRealizarPedido.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        btnRealizarPedido.addActionListener(e -> realizarPedido());

        // Panel contenedor para alinear a la derecha
        JPanel panelWrapper = new JPanel();
        panelWrapper.setBackground(SystemColor.textHighlightText);
        panelWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelWrapper.add(panelTotal);

        panelTotal.add(Box.createVerticalStrut(10));
        panelTotal.add(lblTotal);
        panelTotal.add(Box.createVerticalStrut(5));
        panelTotal.add(lblMontoPagar);
        panelTotal.add(Box.createVerticalStrut(10));
        panelTotal.add(btnRealizarPedido);
        panelTotal.add(Box.createVerticalStrut(10));

        JPanel panelWrapperContainer = new JPanel();
        panelWrapperContainer.setBackground(SystemColor.textHighlightText);
        panelWrapperContainer.setLayout(new BoxLayout(panelWrapperContainer, BoxLayout.X_AXIS));

        // Espaciado a los lados
        panelWrapperContainer.add(Box.createRigidArea(new Dimension(30, 0)));
        panelWrapperContainer.add(panelWrapper);
        panelWrapperContainer.add(Box.createRigidArea(new Dimension(30, 0)));

        // Añadir el panelWrapperContainer al panelIzquierdo en lugar de panelWrapper
        panelIzquierdo.add(panelWrapperContainer);

        panelIzquierdo.add(Box.createVerticalStrut(20));

        // Panel derecho
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(SystemColor.textHighlightText);
        panelDerecho.setPreferredSize(new Dimension(713, 675));
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));

        // Título "Carta del Día"
        JLabel lblPlato = new JLabel("Carta del Día", SwingConstants.CENTER);
        lblPlato.setFont(new Font("Arial", Font.BOLD, 18));
        lblPlato.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelDerecho.add(Box.createVerticalStrut(20));
        panelDerecho.add(lblPlato);
        panelDerecho.add(Box.createVerticalStrut(15));

        // Campo de búsqueda
        txtBuscar = new CustomTextField();
        txtBuscar.setPlaceholder("Buscar por nombre...");
        
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarPlatos(txtBuscar.getText(), platos);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarPlatos(txtBuscar.getText(), platos);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarPlatos(txtBuscar.getText(), platos);
            }
        });

        // Botón Agregar
        btnAgregarPlato = new CustomButton();
        btnAgregarPlato.setText("Agregar");
        btnAgregarPlato.setFocusPainted(false);
        btnAgregarPlato.setBackground(new java.awt.Color(30, 180, 114));
        btnAgregarPlato.setForeground(new java.awt.Color(245, 245, 245));
        btnAgregarPlato.setRippleColor(new java.awt.Color(255, 255, 255));
        btnAgregarPlato.setShadowColor(new java.awt.Color(30, 180, 114));
        btnAgregarPlato.setEnabled(false);
        btnAgregarPlato.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAgregarPlato.setPreferredSize(new Dimension(90, 38)); 

        btnAgregarPlato.addActionListener(e -> agregarPlatoAlPedido());
        
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBackground(SystemColor.textHighlightText);
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.X_AXIS));

        txtBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnAgregarPlato.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        panelBusqueda.add(Box.createRigidArea(new Dimension(150, 0)));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(Box.createRigidArea(new Dimension(10, 0)));
        panelBusqueda.add(btnAgregarPlato);
        panelBusqueda.add(Box.createRigidArea(new Dimension(150, 0)));

        panelDerecho.add(panelBusqueda);

        // Tabla de Carta del Día
        String[] columnNamesDerecho = {"ID", "Plato", "Precio"};
        tableModelPlatos = new DefaultTableModel(columnNamesDerecho, 0);
        tablaPlatos = new JTable(tableModelPlatos);
        scrollPanePlatos = new JScrollPane(tablaPlatos);
        CustomTable.TableCustom.apply(scrollPanePlatos, CustomTable.TableCustom.TableType.DEFAULT);
        
        tablaPlatos.setDefaultEditor(Object.class, null);
        tablaPlatos.getTableHeader().setResizingAllowed(false);
        tablaPlatos.getTableHeader().setReorderingAllowed(false);
        tablaPlatos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaPlatos.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaPlatos.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        listar(platos);

        // Panel contenedor para la tabla derecha
        JPanel panelTablaPlatos = new JPanel();
        panelTablaPlatos.setBackground(SystemColor.textHighlightText);
        panelTablaPlatos.setLayout(new BoxLayout(panelTablaPlatos, BoxLayout.X_AXIS));
        
        panelTablaPlatos.add(Box.createRigidArea(new Dimension(30, 0)));
        panelTablaPlatos.add(scrollPanePlatos);
        panelTablaPlatos.add(Box.createRigidArea(new Dimension(30, 0)));

        panelDerecho.add(Box.createVerticalStrut(15));
        panelDerecho.add(panelTablaPlatos);
        panelDerecho.add(Box.createVerticalStrut(20));

        tablaPlatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaPlatos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tablaPlatos.getSelectedRow() != -1) {
                        btnAgregarPlato.setEnabled(true);
                    } else {
                        btnAgregarPlato.setEnabled(false);
                    }
                }
            }
        });

        // Configuración de GridBagLayout para los paneles
        GridBagConstraints gbcIzquierdo = new GridBagConstraints();
        gbcIzquierdo.gridx = 0; 
        gbcIzquierdo.gridy = 0;
        gbcIzquierdo.weightx = 0.5;
        gbcIzquierdo.fill = GridBagConstraints.BOTH;
        add(panelIzquierdo, gbcIzquierdo);

        GridBagConstraints gbcDerecho = new GridBagConstraints();
        gbcDerecho.gridx = 1;
        gbcDerecho.gridy = 0;
        gbcDerecho.weightx = 0.5;
        gbcDerecho.fill = GridBagConstraints.BOTH;
        add(panelDerecho, gbcDerecho);

        // Redimensionar los paneles cuando cambie el tamaño de la ventana
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                panelIzquierdo.setPreferredSize(new Dimension(width / 2, height));
                panelDerecho.setPreferredSize(new Dimension(width / 2, height));
                revalidate();
                repaint();
            }
        });
    }
    
    private double convertirStringADouble(String precioStr) {
        return Double.parseDouble(precioStr.replace("S/. ", "").replace(",", "."));
    }

    private String convertirDoubleAString(double precio) {
        return "S/. " + String.format("%.2f", precio).replace('.', ',');
    }

    
    public void listar(List<Plato> platos) {
        DefaultTableModel tableModelDerecho = (DefaultTableModel) tablaPlatos.getModel();
        tableModelDerecho.setRowCount(0);
        
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        for (Plato plato : platos) {
            Object[] rowDerecha = new Object[4];
            rowDerecha[0] = plato.getIdPlato();
            rowDerecha[1] = plato.getNombre();
            rowDerecha[2] = "S/. " + decimalFormat.format(plato.getPrecio());
            tableModelDerecho.addRow(rowDerecha);
        }
    }

    private void buscarPlatos(String query, List<Plato> platos) {
        DefaultTableModel tableModelDerecho = (DefaultTableModel) tablaPlatos.getModel();
        tableModelDerecho.setRowCount(0);

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        if (query.isEmpty()) {
            listar(platos);
        } else {
            for (Plato plato : platos) {
                if (plato.getNombre().toLowerCase().contains(query.toLowerCase())) {
                    Object[] rowDerecha = new Object[4];
                    rowDerecha[0] = plato.getIdPlato();
                    rowDerecha[1] = plato.getNombre();
                    rowDerecha[2] = "S/. " + decimalFormat.format(plato.getPrecio());
                    tableModelDerecho.addRow(rowDerecha);
                }
            }
        }
    }

    private void agregarPlatoAlPedido() {
        int row = tablaPlatos.getSelectedRow();
        if (row != -1) {
            int idPlato = (int) tablaPlatos.getValueAt(row, 0);
            String nombrePlato = (String) tablaPlatos.getValueAt(row, 1);
            
            String precioStr = (String) tablaPlatos.getValueAt(row, 2);
            double precioPlato = convertirStringADouble(precioStr);

            boolean encontrado = false;
            for (int i = 0; i < tablaPedidos.getRowCount(); i++) {
                if (mapIdPedidoPlato.get(tablaPedidos.getValueAt(i, 0)) == idPlato) {
                    int cantidadActual = (int) tablaPedidos.getValueAt(i, 3);
                    cantidadActual++;
                    tablaPedidos.setValueAt(cantidadActual, i, 3);
                    
                    double nuevoSubtotal = precioPlato * cantidadActual;
                    tablaPedidos.setValueAt(convertirDoubleAString(nuevoSubtotal), i, 4);

                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                int idPedido = tablaPedidos.getRowCount() + 1;
                Object[] nuevoPedido = new Object[6];
                nuevoPedido[0] = idPedido;
                nuevoPedido[1] = nombrePlato;
                nuevoPedido[2] = convertirDoubleAString(precioPlato);
                nuevoPedido[3] = 1;
                nuevoPedido[4] = convertirDoubleAString(precioPlato);
                nuevoPedido[5] = "";
                tableModelPedidos.addRow(nuevoPedido);

                mapIdPedidoPlato.put(idPedido, idPlato);
            }

            actualizarTotal();
            actualizarBtnRealizarPedido();
        }
    }

    private void actualizarTotal() {
        double total = 0;

        for (int i = 0; i < tablaPedidos.getRowCount(); i++) {
            String subtotalStr = (String) tablaPedidos.getValueAt(i, 4);
            total += convertirStringADouble(subtotalStr);
        }

        lblMontoPagar.setText(convertirDoubleAString(total));
    }
    
    private void agregarComentarioAlPedido() {
        int selectedRow = tablaPedidos.getSelectedRow();
        if (selectedRow != -1) {
            String comentario = txtComentario.getText().trim();
            tableModelPedidos.setValueAt(comentario, selectedRow, 5);
            txtComentario.setText("");
            btnAgregarComen.setEnabled(false);
            btnAgregarComen.setText("Agregar");
        }
    }
    
    private void eliminarPlatoDelPedido() {
        int selectedRow = tablaPedidos.getSelectedRow();
        if (selectedRow != -1) {
        	tableModelPedidos.removeRow(selectedRow);
            reordenarIdsPedidos();
            actualizarTotal();
            actualizarBtnRealizarPedido();
        }
    }
    
    private void reordenarIdsPedidos() {
        for (int i = 0; i < tableModelPedidos.getRowCount(); i++) {
        	tableModelPedidos.setValueAt(i + 1, i, 0);
            mapIdPedidoPlato.put(i + 1, (int) tableModelPedidos.getValueAt(i, 0));
        }
    }
    
    private void actualizarBtnRealizarPedido() {
        if (tablaPedidos.getRowCount() > 0) {
            btnRealizarPedido.setEnabled(true);
        } else {
            btnRealizarPedido.setEnabled(false);
        }
    }
    
    private void veriConBtnAgregarCom() {
        boolean filaSeleccionada = tablaPedidos.getSelectedRow() != -1;
        boolean comentarioNoVacio = !txtComentario.getText().trim().isEmpty();

        btnAgregarComen.setEnabled(filaSeleccionada && comentarioNoVacio);
    }
    
    private void actuTextoBtnComen() {
        int selectedRow = tablaPedidos.getSelectedRow();
        if (selectedRow != -1) {
            String comentarioExistente = (String) tableModelPedidos.getValueAt(selectedRow, 5);
            if (comentarioExistente != null && !comentarioExistente.trim().isEmpty()) {
                btnAgregarComen.setText("Actualizar");
            } else {
                btnAgregarComen.setText("Agregar");
            }
        }
    }
    
    public void setDatosPedido(int salaId, int numeroMesa) {
        System.out.println("Sala ID: " + salaId + " - Mesa: " + numeroMesa);
        this.salaId = salaId;
        this.numeroMesa = numeroMesa;
    }
    
    private void realizarPedido() {
    	CustomAlert.showAlert("Éxito", "El Pedido se ha realizado con éxito", "success");

    	tableModelPedidos.setRowCount(0);
        tablaPlatos.getSelectionModel().clearSelection();

        txtComentario.setText("");

        btnRealizarPedido.setEnabled(false);
        btnAgregarComen.setEnabled(false);

        btnAgregarComen.setText("Agregar");

        mapIdPedidoPlato.clear();

        lblMontoPagar.setText("S/. 00,00");
    }
}