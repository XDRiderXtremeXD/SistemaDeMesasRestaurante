package controller;

import dao.factory.DAOFactory;
import dao.interfaces.IUsuarioDao;
import model.Usuario;

import java.util.List;

public class UsuarioController {

    private IUsuarioDao usuarioDao;

    public UsuarioController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.usuarioDao = daoFactory.getUsuario();
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioDao.listUsuarios();
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getIdUsuario() + 
                               ", Nombre: " + usuario.getNombreUsuario() +
                               ", Correo: " + usuario.getCorreo() +
                               ", Rol: " + usuario.getRol());
        }
        return usuarios;
    }

    public void obtenerUsuario(int idUsuario) {
        Usuario usuario = usuarioDao.getUsuario(idUsuario);
        if (usuario != null) {
            System.out.println("ID: " + usuario.getIdUsuario() +
                               ", Nombre: " + usuario.getNombreUsuario() +
                               ", Correo: " + usuario.getCorreo() +
                               ", Rol: " + usuario.getRol());
        } else {
            System.out.println("Usuario no encontrado con el ID: " + idUsuario);
        }
    }

    public boolean crearUsuario(Usuario usuario) {
        boolean seCreoNuevoUsuario = usuarioDao.createUsuario(usuario);
        System.out.println("Usuario creado con ID: " + usuario.getIdUsuario());
        
        return seCreoNuevoUsuario;
    }

    public void actualizarUsuario(Usuario usuario) {
        Usuario usuarioActualizado = usuarioDao.updateUsuario(usuario);
        System.out.println("Usuario actualizado con ID: " + usuarioActualizado.getIdUsuario());
    }

    public void eliminarUsuario(int idUsuario) {
        boolean eliminado = usuarioDao.deleteUsuario(idUsuario);
        if (eliminado) {
            System.out.println("Usuario con ID: " + idUsuario + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el usuario con ID: " + idUsuario);
        }
    }
    
    public Usuario login(String nombreUsuario, String contrasena) {
        return usuarioDao.login(nombreUsuario, contrasena);
    }
}
