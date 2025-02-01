package utils;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class ButtonRenderer extends JButton implements TableCellRenderer {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ImageIcon iconoBoton;
	
	public ButtonRenderer(ImageIcon icono) {
        setOpaque(true);  // Asegura que el fondo del botón sea visible
        iconoBoton=icono;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
           
        setIcon(iconoBoton); 
        return this;
    }
}
