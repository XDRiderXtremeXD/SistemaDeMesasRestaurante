package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import components.*;
import controller.PlatoController;

import java.util.List;
import model.Plato;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

public class CartaDelDiaView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tablaPlatos;
    private DefaultTableModel tableModel;
    private CustomButton btnAgregar;
    private CustomButton btnEliminar;
    private CustomTextField txtNombre;
    private CustomTextField txtPrecio, txtBuscar;
    private JPanel formularioPanel;

    public CartaDelDiaView(List<Plato> platos) {	
        setPreferredSize(new Dimension(1427, 675));
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nombre", "Precio", "Fecha"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPlatos = new JTable(tableModel);
        tablaPlatos.getTableHeader().setReorderingAllowed(false);
        tablaPlatos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaPlatos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaPlatos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaPlatos.getColumnModel().getColumn(3).setPreferredWidth(150);

        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablaPanel.setBackground(SystemColor.textHighlightText);
        
        txtBuscar = new CustomTextField();
        txtBuscar.setPlaceholder("Buscar por nombre...");
        txtBuscar.setPreferredSize(new Dimension(300, 40));
        
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
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(SystemColor.textHighlightText);
        searchPanel.add(txtBuscar);
        tablaPanel.add(searchPanel, BorderLayout.NORTH);
        
        // ScrollPane con la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPlatos);
        CustomTable.TableCustom.apply(scrollPane, CustomTable.TableCustom.TableType.DEFAULT);
        tablaPanel.add(scrollPane, BorderLayout.CENTER);

        listar(platos);
        
        // Panel de formulario (sin cambios)
        formularioPanel = new JPanel();
        formularioPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(211, 211, 211)));
        formularioPanel.setBackground(SystemColor.textHighlightText);
        formularioPanel.setLayout(null);
        formularioPanel.setPreferredSize(new Dimension(400, 0));

        JLabel lblTitulo = new JLabel("Agregar Carta del Día");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(50, 30, 300, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        formularioPanel.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre del Plato");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNombre.setBounds(55, 80, 295, 20);
        formularioPanel.add(lblNombre);

        txtNombre = new CustomTextField();
        txtNombre.setBounds(50, 110, 300, 40);
        txtNombre.setPlaceholder("Ingresa el nombre del plato");
        formularioPanel.add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio S/.");
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPrecio.setBounds(55, 160, 295, 20);
        formularioPanel.add(lblPrecio);

        txtPrecio = new CustomTextField();
        txtPrecio.setBounds(50, 190, 300, 40);
        txtPrecio.setPlaceholder("Ej: 12,99");
        formularioPanel.add(txtPrecio);

        txtPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (txtPrecio.getText().isEmpty() && c == ',') {
                    e.consume();
                }

                if (!Character.isDigit(c) && c != ',' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }

                if (c == ',' && txtPrecio.getText().contains(",")) {
                    e.consume();
                }
            }
        });

        btnAgregar = new CustomButton();
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBackground(new java.awt.Color(30, 180, 114));
        btnAgregar.setForeground(new java.awt.Color(245, 245, 245));
        btnAgregar.setRippleColor(new java.awt.Color(255, 255, 255));
        btnAgregar.setShadowColor(new java.awt.Color(30, 180, 114));
        btnAgregar.setText("Agregar Plato");
        btnAgregar.setBounds(125, 244, 150, 46);
        btnAgregar.setEnabled(false);
        btnAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formularioPanel.add(btnAgregar);

        Runnable validateButton = () -> {
            String nombre = txtNombre.getText();
            String precioText = txtPrecio.getText();

            precioText = precioText.replace(',', '.');

            boolean isValidPrecio = false;
            try {
                Double.parseDouble(precioText);
                isValidPrecio = true;
            } catch (NumberFormatException ex) {
                isValidPrecio = false;
            }

            boolean isValid = nombre.length() >= 3 && !precioText.isEmpty() && isValidPrecio;
            btnAgregar.setEnabled(isValid);
        };

        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                validateButton.run();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                validateButton.run();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                validateButton.run();
            }
        };

        txtNombre.getDocument().addDocumentListener(listener);
        txtPrecio.getDocument().addDocumentListener(listener);

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String precioText = txtPrecio.getText();

            precioText = precioText.replace(',', '.');

            boolean isValidPrecio = false;
            try {
                Double.parseDouble(precioText);
                isValidPrecio = true;
            } catch (NumberFormatException ex) {
                isValidPrecio = false;
            }

            boolean isValid = nombre.length() >= 3 && !precioText.isEmpty() && isValidPrecio;
            btnAgregar.setEnabled(isValid);

            if (isValid) {
                double precio = Double.parseDouble(precioText);
                java.sql.Date fecha = java.sql.Date.valueOf(java.time.LocalDate.now());

                Plato nuevoPlato = new Plato();
                nuevoPlato.setNombre(nombre);
                nuevoPlato.setPrecio(precio);
                nuevoPlato.setFecha(fecha);

                PlatoController platoController = new PlatoController();
                
                Plato platoCreado = platoController.crear(nuevoPlato);

                if (platoCreado != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    Object[] row = new Object[4];
                    row[0] = platoCreado.getIdPlato();
                    row[1] = platoCreado.getNombre();
                    row[2] = "S/. " + decimalFormat.format(platoCreado.getPrecio());
                    row[3] = platoCreado.getFecha();
                    tableModel.addRow(row);
                    
                    CustomAlert.showAlert("Éxito", "El plato ha sido agregado correctamente", "success");
                } else {
                    CustomAlert.showAlert("Error", "No se pudo agregar el plato", "error");
                }

                txtNombre.setText("");
                txtPrecio.setText("");
                txtNombre.restorePlaceholder();
                txtPrecio.restorePlaceholder();

                formularioPanel.requestFocus();
            }
        });

        btnEliminar = new CustomButton();
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBackground(new java.awt.Color(253, 83, 83));
        btnEliminar.setForeground(new java.awt.Color(245, 245, 245));
        btnEliminar.setRippleColor(new java.awt.Color(255, 255, 255));
        btnEliminar.setShadowColor(new java.awt.Color(253, 83, 83));
        btnEliminar.setText("Eliminar Plato");
        btnEliminar.setBounds(125, 304, 150, 46);
        btnEliminar.setEnabled(false);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formularioPanel.add(btnEliminar);

        tablaPlatos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        tablaPlatos.getSelectionModel().addListSelectionListener(e -> {
            boolean seleccionValida = tablaPlatos.getSelectedRowCount() > 0;
            btnEliminar.setEnabled(seleccionValida);
        });

        btnEliminar.addActionListener(e -> {
            int[] filasSeleccionadas = tablaPlatos.getSelectedRows();
            if (filasSeleccionadas.length > 0) {
                PlatoController platoController = new PlatoController();
                boolean algunEliminado = false;
                int platosEliminados = 0;

                for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {
                    int fila = filasSeleccionadas[i];
                    int idPlato = (int) tableModel.getValueAt(fila, 0);

                    boolean eliminado = platoController.eliminar(idPlato);
                    if (eliminado) {
                        tableModel.removeRow(fila);
                        algunEliminado = true;
                        platosEliminados++;
                    }
                }

                if (algunEliminado) {
                    String mensaje = (platosEliminados == 1) ? "El plato ha sido eliminado" : "Los platos han sido eliminados";
                    CustomAlert.showAlert("Éxito", mensaje, "success");
                } else {
                    String mensajeError = (filasSeleccionadas.length == 1) ? "No se pudo eliminar el plato" : "No se pudieron eliminar los platos";
                    CustomAlert.showAlert("Error", mensajeError, "error");
                }
            }
        });

        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                tablaPlatos.clearSelection();
            }
        });

        txtPrecio.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                tablaPlatos.clearSelection();
            }
        });
        
        add(tablaPanel, BorderLayout.CENTER);
        add(formularioPanel, BorderLayout.EAST);
    }

    public void listar(List<Plato> platos) {
        tableModel.setRowCount(0);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        for (Plato plato : platos) {
            Object[] row = new Object[4];
            row[0] = plato.getIdPlato();
            row[1] = plato.getNombre();
            row[2] = "S/. " + decimalFormat.format(plato.getPrecio());
            row[3] = plato.getFecha();
            tableModel.addRow(row);
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
                    rowDerecha[3] = plato.getFecha();
                    tableModelDerecho.addRow(rowDerecha);
                }
            }
        }
    }
    
    public void valoresIniciales() {
        txtBuscar.setText("");

        txtNombre.setText("");
        txtNombre.restorePlaceholder();
        
        txtPrecio.setText("");
        txtPrecio.restorePlaceholder();

        btnAgregar.setEnabled(false);
        
        tablaPlatos.clearSelection();
        
        formularioPanel.requestFocus();
    }

}
