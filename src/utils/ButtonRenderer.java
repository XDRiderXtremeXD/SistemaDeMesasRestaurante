package utils;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import components.CustomButton;

public class ButtonRenderer extends CustomButton implements TableCellRenderer {
    private static final long serialVersionUID = 1L;

    public ButtonRenderer(ImageIcon icon) {
        setIcon(icon);
        setFocusable(false);
        disableShadowAndBorder();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Puedes personalizar el aspecto según si la fila está seleccionada o no
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}
