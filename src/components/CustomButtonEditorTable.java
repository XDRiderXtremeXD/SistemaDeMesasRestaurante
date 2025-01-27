package components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class CustomButtonEditorTable extends AbstractCellEditor implements TableCellEditor, ActionListener {
	private JButton button;
    private ActionListener actionListener;

    public CustomButtonEditorTable(JButton button, ActionListener actionListener) {
        this.button = button;
        this.actionListener = actionListener;

        // Agrega un ActionListener al botón
        button.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.actionPerformed(e);
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Actualiza el botón con la fila/columna si es necesario
        button.putClientProperty("row", row);
        button.putClientProperty("column", column);
        button.putClientProperty("table", table);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}