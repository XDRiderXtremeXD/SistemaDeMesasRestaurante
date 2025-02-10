package components;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;

public class CustomButtonEditorTable extends AbstractCellEditor implements TableCellEditor {
    private static final long serialVersionUID = 1L;
    private CustomButton button;
    
    public CustomButtonEditorTable(ImageIcon icon, ActionListener actionListener) {
        // Se crea el CustomButton directamente
        this.button = new CustomButton();
        button.setIcon(icon);
        button.disableShadowAndBorder();
        
        // Agregamos el ActionListener que ejecuta la acción y detiene la edición
        button.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.actionPerformed(e);
            }
            fireEditingStopped();
        });
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Opcional: guardar la fila, columna o la tabla en propiedades, si lo necesitas
        button.putClientProperty("row", row);
        button.putClientProperty("column", column);
        button.putClientProperty("table", table);
        return button;
    }
    
    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
