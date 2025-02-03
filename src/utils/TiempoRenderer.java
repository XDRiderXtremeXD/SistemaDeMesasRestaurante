package utils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TiempoRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Obtiene el tiempo en formato "hh:mm:ss"
        String tiempo = (String) value;
        String[] partes = tiempo.split(":");

        if (partes.length == 3) {
            int minutos = Integer.parseInt(partes[1])+ Integer.parseInt(partes[0])*60; // Extrae los minutos

            if (minutos >= 20) {
                cell.setBackground(Color.RED); // Más de 20 min → Rojo
                cell.setForeground(Color.WHITE);
            } else if (minutos >= 10) {
                cell.setBackground(Color.YELLOW); // Entre 10 y 19 min → Amarillo
                cell.setForeground(Color.BLACK);
            } else {
                cell.setBackground(Color.WHITE); // Menos de 10 min → Normal
                cell.setForeground(Color.BLACK);
            }
        }

        return cell;
    }
}
