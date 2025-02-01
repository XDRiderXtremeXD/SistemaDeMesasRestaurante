package utils;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import controller.UsuarioController;
import model.Usuario;
import view.EditUsuarioView;
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
        
        UsuarioController usuarioController= new UsuarioController ();
        Usuario user=usuarioController.obtenerUsuario(Integer.parseInt(value.toString()));
        
        EditUsuarioView frame = new EditUsuarioView(user,usuariosView);
        frame.setLocationRelativeTo(usuariosView); 
        frame.setVisible(true); 
    }
	
	public static void eliminarUsuario(ActionEvent e, UsuariosView usuariosView) {
	    JButton sourceButton = (JButton) e.getSource();
	    int row = (int) sourceButton.getClientProperty("row");
	    int column = (int) sourceButton.getClientProperty("column");
	    JTable tabla = (JTable) sourceButton.getClientProperty("table");
	    TableModel model = tabla.getModel(); 
	    Object value = model.getValueAt(row, 0);
	    Object nombre = model.getValueAt(row, 1);
	    
	    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar este usuario "+nombre.toString()+"?", "Confirmación", JOptionPane.YES_NO_OPTION);
	    
	    if (confirmacion == JOptionPane.YES_OPTION) {
	        UsuarioController usuarioController = new UsuarioController();
	        usuarioController.eliminarUsuario(Integer.parseInt(value.toString()));
	        
	        usuariosView.ReiniciarELementoEnTabla();
	        
	        System.out.println("Eliminando fila: " + row + ", columna: " + column + ", valor: " + value.toString());
	    } else {
	        System.out.println("Eliminación cancelada.");
	    }
	}
}
