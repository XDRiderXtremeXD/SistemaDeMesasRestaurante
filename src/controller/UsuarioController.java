package controller;

import dao.factory.DAOFactory;
import dao.interfaces.IUsuarioDao;
import model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
	
	private IUsuarioDao dao;

    public UsuarioController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.dao = daoFactory.getUsuario();
    }

    public List<Usuario> listar() {
        return dao.list();
    }

    public Usuario obtener(int idUsuario) {
        return dao.get(idUsuario);
    }

    public Usuario crear(Usuario usuario) throws SQLException {
        return dao.create(usuario);
    }

    public Usuario actualizar(Usuario usuario) throws SQLException {
        return dao.update(usuario);
    }

    public boolean eliminar(int idUsuario) {
        boolean eliminado = dao.delete(idUsuario);
        if (eliminado) {
            return true;
        } else {
            return false;
        }
    }
    
    public Usuario login(String nombreUsuario, String contrasena) {
        return dao.login(nombreUsuario, contrasena);
    }
}
