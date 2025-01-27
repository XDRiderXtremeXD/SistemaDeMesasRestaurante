package dao.interfaces;

import java.util.List;

import model.Usuario;

public interface IUsuarioDao {
	List<Usuario> listUsuarios();
	Usuario getUsuario(int id);
	boolean createUsuario(Usuario usuario);
	Usuario updateUsuario(Usuario usuario);
	boolean deleteUsuario(int id);
}
