package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPlatoDao;
import model.Plato;
import utils.MySqlConexion;

public class PlatoDaoImpl implements IPlatoDao {

	@Override
	public List<Plato> list() {
		List<Plato> listPlatos = new ArrayList<Plato>();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;

		try {
		    cn = MySqlConexion.getConexion();    
		    String sql = "SELECT IdPlato, Nombre, Precio, Fecha FROM Plato";
		    
		    psm = cn.prepareStatement(sql);
		    rs = psm.executeQuery();

		    while (rs.next()) {
		    	Plato plato = new Plato();
		    	plato.setIdPlato(rs.getInt("IdPlato"));
		    	plato.setNombre(rs.getString("Nombre"));
		    	plato.setPrecio(rs.getDouble("Precio"));
		    	plato.setFecha(rs.getDate("Fecha"));

		    	listPlatos.add(plato);
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
		return listPlatos;
	}

	@Override
	public Plato get(int idPlato) {
		Plato plato = null;
	    Connection cn = null;
	    PreparedStatement psm = null;
	    ResultSet rs = null;

	    try {
	        cn = MySqlConexion.getConexion();
	        String sql = "SELECT IdPlato, Nombre, Precio, Fecha FROM Plato WHERE IdPlato = ?";
	        
	        psm = cn.prepareStatement(sql);
	        psm.setInt(1, idPlato);
	        rs = psm.executeQuery();

	        if (rs.next()) {
	        	plato = new Plato();
	        	plato.setIdPlato(rs.getInt("IdPlato"));
		    	plato.setNombre(rs.getString("Nombre"));
		    	plato.setPrecio(rs.getDouble("Precio"));
		    	plato.setFecha(rs.getDate("Fecha"));
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
	    return plato;
	}

	@Override
	public Plato create(Plato plato) {
	    Connection cn = null;
	    PreparedStatement psm = null;
	    ResultSet rs = null;

	    try {
	        cn = MySqlConexion.getConexion();

	        String sqlInsert = "INSERT INTO Plato (Nombre, Precio, Fecha) "
	                         + "VALUES (?, ?, ?)";
	        
	        psm = cn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);

	        psm.setString(1, plato.getNombre());
	        psm.setDouble(2, plato.getPrecio());
	        psm.setDate(3, (Date) plato.getFecha());

	        int affectedRows = psm.executeUpdate();

	        if (affectedRows > 0) {
	            rs = psm.getGeneratedKeys();
	            if (rs.next()) {
	            	int generatedId = rs.getInt(1);
	                plato.setIdPlato(generatedId);
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
	    return plato;
	}

	@Override
	public Plato update(Plato plato) {
	    Connection cn = null;
	    PreparedStatement psm = null;

	    try {
	        cn = MySqlConexion.getConexion();

	        String sql = "UPDATE Plato SET Nombre = ?, Precio = ?, Fecha = ? "
	                   + "WHERE IdPlato = ?";

	        psm = cn.prepareStatement(sql);

	        psm.setString(1, plato.getNombre());
	        psm.setDouble(2, plato.getPrecio());
	        psm.setDate(3, (Date) plato.getFecha());
	        psm.setInt(4, plato.getIdPlato());

	        int resultado = psm.executeUpdate();

	        if (resultado == 0) {
	            System.out.println("No se encontró el plato con el ID proporcionado.");
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
	    return plato;
	}
	
	@Override
	public boolean delete(int idPlato) {
	    Connection cn = null;
	    PreparedStatement psm = null;
	    boolean isDeleted = false;

	    try {
	        cn = MySqlConexion.getConexion();

	        String sql = "DELETE FROM Plato WHERE idPlato = ?";

	        psm = cn.prepareStatement(sql);
	        psm.setInt(1, idPlato);

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
