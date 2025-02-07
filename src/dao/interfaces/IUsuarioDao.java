package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.Usuario;

public interface IUsuarioDao {
	List<Usuario> list();
	Usuario get(int id);
	Usuario create(Usuario usuario) throws SQLException;
	Usuario update(Usuario usuario)  throws SQLException;
	boolean delete(int id);
	public Usuario login(String nombreUsuario, String contrasena);
}
