package dao.impl;

import model.Sala;
import utils.MySqlConexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ISalaDao;

public class SalaDaoImpl implements ISalaDao{
	
	@Override
	public List<Sala> listarSalas() {
		List<Sala> listSalas = new ArrayList<Sala>();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;

		try {
		    cn = MySqlConexion.getConexion();    
		    String sql = "SELECT IdSala, Nombre, Mesas FROM Sala";
		    
		    psm = cn.prepareStatement(sql);
		    rs = psm.executeQuery();

		    while (rs.next()) {
		    	Sala sala = new Sala();
		    	sala.setIdSala(rs.getInt("IdSala"));
		    	sala.setNombre(rs.getString("Nombre"));
		    	sala.setMesas(rs.getInt("Mesas"));

		        listSalas.add(sala);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (rs != null) rs.close();
		        if (psm != null) psm.close();
		        if (cn != null) cn.close();
		    } catch (Exception e2) {
		        e2.printStackTrace();
		    }
		}
		return listSalas;
	}

	@Override
	public Sala obtenerSalaPorId(int id) {
	    Sala sala = null;
	    Connection cn = null;
	    PreparedStatement psm = null;
	    ResultSet rs = null;
	    try {
	        // Obtener conexión de la fuente
	        cn = MySqlConexion.getConexion();// connection debe estar configurada previamente
	        String query = "SELECT * FROM salas WHERE id_sala = ?";
	        psm = cn.prepareStatement(query); // Aquí usamos prepareStatement
	        psm.setInt(1, id);
	        rs = psm.executeQuery();
	        if (rs.next()) {
	            sala = new Sala(
	                    rs.getInt("id_sala"),
	                    rs.getString("nombre"),
	                    rs.getInt("mesas")
	            );
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Cerrar recursos para evitar fugas
	        try {
	            if (rs != null) rs.close();
	            if (psm != null) psm.close();
	            if (cn != null) cn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return sala;
	}

	@Override
	public Sala registrarSala(Sala sala) {
	    Connection cn = null;
	    PreparedStatement psm = null;
	    ResultSet rs = null;

	    try {
	        cn = MySqlConexion.getConexion();

	        String sqlInsert = "INSERT INTO Sala (Nombre, Mesas) "
	                         + "VALUES (?, ?)";
	        
	        psm = cn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);

	        psm.setString(1, sala.getNombre());
	        psm.setInt(2, sala.getMesas());

	        int affectedRows = psm.executeUpdate();

	        if (affectedRows > 0) {
	            rs = psm.getGeneratedKeys();
	            if (rs.next()) {
	                int generatedId = rs.getInt(1);
	                sala.setIdSala(generatedId);
	            }
	        }

	    } catch (SQLException e) {
	    	e.printStackTrace();;
	    } finally {
	        try {
	        	if (rs != null) rs.close();
	            if (psm != null) psm.close();
	            if (cn != null) cn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return sala;
	}

	@Override
	public boolean actualizarSala(Sala sala) {
	    Connection cn = null;
	    PreparedStatement psm = null;
	    boolean isActu = false;


	    try {
	        cn = MySqlConexion.getConexion();

	        String sql = "UPDATE Sala SET Nombre = ?, Mesas = ? "
	                   + "WHERE IdSala = ?";

	        psm = cn.prepareStatement(sql);

	        psm.setString(1, sala.getNombre());
	        psm.setInt(2, sala.getMesas());
	        psm.setInt(3, sala.getIdSala());

	        int resultado = psm.executeUpdate();

	        if (resultado == 0) {
	            System.out.println("No se encontró la sala con el ID proporcionado.");
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
	    return isActu;
	}
	
	@Override
	public boolean eliminarSala(int idSala) {
	    Connection cn = null;
	    PreparedStatement psm = null;
	    boolean isDeleted = false;

	    try {
	        cn = MySqlConexion.getConexion();

	        String sql = "DELETE FROM Sala WHERE IdSala = ?";

	        psm = cn.prepareStatement(sql);
	        psm.setInt(1, idSala);

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
}
