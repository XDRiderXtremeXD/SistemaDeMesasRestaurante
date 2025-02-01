package controller;

import dao.factory.DAOFactory;
import dao.interfaces.IUsuarioDao;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    private IUsuarioDao usuarioDao;
    static List<Usuario> listUsuarios = new ArrayList<>();

    public UsuarioController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.usuarioDao = daoFactory.getUsuario();
        listUsuarios = usuarioDao.listUsuarios();
    }
 
    public List<Usuario> listarUsuarios() {
    	return listarUsuarios("");
    }
    
    public List<Usuario> listarUsuarios(String coincidencia) {
        
    	if(coincidencia.contentEquals("")) {
        	for (Usuario usuario : listUsuarios) {
                System.out.println("ID: " + usuario.getIdUsuario() + 
                                   ", Nombre: " + usuario.getNombreUsuario() +
                                   ", Correo: " + usuario.getCorreo() +
                                   ", Rol: " + usuario.getRol());
            }
        	return listUsuarios;}
        
        List<Usuario> usuariosFiltrados = new ArrayList<>();
        
        String coincidenciaLower = coincidencia.toLowerCase();
        for (Usuario usuario : listUsuarios) {
            if (usuario.getNombreUsuario().toLowerCase().contains(coincidenciaLower) ||
                usuario.getCorreo().toLowerCase().contains(coincidenciaLower)) {
                usuariosFiltrados.add(usuario);
            }
        }

        if (!usuariosFiltrados.isEmpty()) {
            System.out.println("Usuarios encontrados que coinciden con: " + coincidencia);
            for (Usuario usuario : usuariosFiltrados) {
                System.out.println("ID: " + usuario.getIdUsuario() +
                                   ", Nombre: " + usuario.getNombreUsuario() +
                                   ", Correo: " + usuario.getCorreo() +
                                   ", Rol: " + usuario.getRol());
            }
        } else {
            System.out.println("No se encontraron usuarios que coincidan con: " + coincidencia);
        }

        return usuariosFiltrados;
    }
    
    

    public Usuario obtenerUsuario(int idUsuario) {
        // Buscar en la lista local en lugar de ir a la BD cada vez
        for (Usuario usuario : listUsuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                System.out.println("Usuario encontrado: " + usuario.getNombreUsuario());
                return usuario;
            }
        }
        System.out.println("Usuario no encontrado con el ID: " + idUsuario);
        return null;
    }

    public boolean crearUsuario(Usuario usuario) {
        boolean creado = usuarioDao.createUsuario(usuario);
        if (creado) {
            listUsuarios.add(usuario); // Agregar a la lista local
            System.out.println("Usuario creado con ID: " + usuario.getIdUsuario());
        } else {
            System.out.println("Error al crear usuario.");
        }
        return creado;
    }

    public void actualizarUsuario(Usuario usuario) {
        boolean actualizado = usuarioDao.updateUsuario(usuario);
        if (actualizado) {
            // Actualizar la lista local
            for (int i = 0; i < listUsuarios.size(); i++) {
                if (listUsuarios.get(i).getIdUsuario() == usuario.getIdUsuario()) {
                    listUsuarios.set(i, usuario);
                    break;
                }
            }
            System.out.println("Usuario actualizado con ID: " + usuario.getIdUsuario());
        } else {
            System.out.println("Error al actualizar usuario.");
        }
    }

    public void eliminarUsuario(int idUsuario) {
        boolean eliminado = usuarioDao.deleteUsuario(idUsuario);
        if (eliminado) {
            listUsuarios.removeIf(user -> user.getIdUsuario() == idUsuario); // Eliminar de la lista local
            System.out.println("Usuario con ID: " + idUsuario + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el usuario con ID: " + idUsuario);
        }
    }
}
