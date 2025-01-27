package utils;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import controller.UsuarioController;
import view.UsuariosView;

public class ButtonActionsUsuario {
    
	public static void editarUsuario(ActionEvent e, UsuariosView usuariosView) {
        JButton sourceButton = (JButton) e.getSource();
        int row = (int) sourceButton.getClientProperty("row");
        int column = (int) sourceButton.getClientProperty("column");
        JTable tabla = (JTable) sourceButton.getClientProperty("table");
        TableModel model = tabla.getModel(); 
        Object value = model.getValueAt(row, 0); 
        
        System.out.println("Editando fila: " + row + ", columna: " + column+", valor: "+value.toString());
        
    }
	
	public static void eliminarUsuario(ActionEvent e, UsuariosView usuariosView) {
		JButton sourceButton = (JButton) e.getSource();
        int row = (int) sourceButton.getClientProperty("row");
        int column = (int) sourceButton.getClientProperty("column");
        JTable tabla = (JTable) sourceButton.getClientProperty("table");
        TableModel model = tabla.getModel(); 
        Object value = model.getValueAt(row, 0);
        
        UsuarioController usuarioController= new UsuarioController ();
        usuarioController.eliminarUsuario(Integer.parseInt(value.toString()));
        
        usuariosView.ReiniciarELementoEnTabla();
        
        System.out.println("Eliminando fila: " + row + ", columna: " + column+", valor: "+value.toString());
    }
}
