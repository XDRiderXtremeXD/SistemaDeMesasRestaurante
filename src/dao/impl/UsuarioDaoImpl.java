package dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IUsuarioDao;
import model.Usuario;
import utils.MySqlConexion;

public class UsuarioDaoImpl implements IUsuarioDao {

    @Override
    public List<Usuario> list() {
        List<Usuario> listUsuarios = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            cn = MySqlConexion.getConexion();
            String sql = "SELECT IdUsuario, NombreUsuario, Correo, Contrasena, Rol FROM Usuario";

            psm = cn.prepareStatement(sql);
            rs = psm.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("IdUsuario"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuario.setCorreo(rs.getString("Correo"));
                usuario.setContrasena(rs.getString("Contrasena"));
                usuario.setRol(rs.getString("Rol"));

                listUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        
        return listUsuarios;
    }

    @Override
    public Usuario get(int id) {
        Usuario usuario = null;
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        try {
            cn = MySqlConexion.getConexion();
            String sql = "SELECT IdUsuario, NombreUsuario, Correo, Contrasena, Rol FROM Usuario WHERE IdUsuario = ?";

            psm = cn.prepareStatement(sql);
            psm.setInt(1, id);
            rs = psm.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("IdUsuario"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuario.setCorreo(rs.getString("Correo"));
                usuario.setContrasena(rs.getString("Contrasena"));
                usuario.setRol(rs.getString("Rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return usuario;
    }

    @Override
    public Usuario create(Usuario usuario) throws SQLException {
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        try {
            cn = MySqlConexion.getConexion();
            String sqlInsert = "INSERT INTO Usuario (NombreUsuario, Correo, Contrasena, Rol) VALUES (?, ?, ?, ?)";
            psm = cn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);

            psm.setString(1, usuario.getNombreUsuario());
            psm.setString(2, usuario.getCorreo());
            psm.setString(3, usuario.getContrasena());
            psm.setString(4, usuario.getRol());

            int affectedRows = psm.executeUpdate();

            if (affectedRows > 0) {
                rs = psm.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    usuario.setIdUsuario(generatedId);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("El nombre de usuario ya existe. Intente con otro.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }
    
    @Override
	public Usuario update(Usuario usuario) throws SQLException {
		Connection cn = null;
		PreparedStatement psm = null;

        try {
            cn = MySqlConexion.getConexion();

            String sql = "UPDATE Usuario SET NombreUsuario = ?, Correo = ?, Contrasena = ?, Rol = ? "
                       + "WHERE IdUsuario = ?";

            psm = cn.prepareStatement(sql);

            psm.setString(1, usuario.getNombreUsuario());
            psm.setString(2, usuario.getCorreo());
            psm.setString(3, usuario.getContrasena());
            psm.setString(4, usuario.getRol());
            psm.setInt(5, usuario.getIdUsuario());

            int resultado = psm.executeUpdate();
            
            if (resultado == 0) {
	            System.out.println("No se encontró el usuario con el ID proporcionado.");
	        }
            
        } catch (SQLIntegrityConstraintViolationException e) {
        	throw new SQLException("El nombre de usuario ya existe. Intente con otro.");
        } finally {
            try {
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
	}

    @Override
    public boolean delete(int id) {
        Connection cn = null;
        PreparedStatement psm = null;
        boolean isDeleted = false;

        try {
            cn = MySqlConexion.getConexion();

            String sql = "DELETE FROM Usuario WHERE IdUsuario = ?";

            psm = cn.prepareStatement(sql);
            psm.setInt(1, id);

            int rowsAffected = psm.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isDeleted;
    }
	
	@Override
	public Usuario login(String nombreUsuario, String contrasena) {
	    Usuario usuario = null;
	    Connection cn = null;
	    PreparedStatement psm = null;
	    ResultSet rs = null;

	    try {
	        cn = MySqlConexion.getConexion();
	        String sql = "SELECT IdUsuario, NombreUsuario, Correo, Contrasena, Rol FROM Usuario WHERE NombreUsuario = ? AND Contrasena = ?";

	        psm = cn.prepareStatement(sql);
	        psm.setString(1, nombreUsuario);
	        psm.setString(2, contrasena);
	        rs = psm.executeQuery();

	        if (rs.next()) {
	            usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("IdUsuario"));
	            usuario.setNombreUsuario(rs.getString("NombreUsuario"));
	            usuario.setCorreo(rs.getString("Correo"));
	            usuario.setContrasena(rs.getString("Contrasena"));
	            usuario.setRol(rs.getString("Rol"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (psm != null) psm.close();
	            if (cn != null) cn.close();
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return usuario;
	}
}
