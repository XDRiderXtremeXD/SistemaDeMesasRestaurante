package dao.impl;

import model.Sala;
import utils.MySqlConexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ISalaDao;

public class SalaDaoImpl implements ISalaDao {

    @Override
    public List<Sala> listarSalas() {
        List<Sala> listSalas = new ArrayList<>();
        String sql = "SELECT IdSala, Nombre, Mesas FROM Sala";
        
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setIdSala(rs.getInt("IdSala"));
                sala.setNombre(rs.getString("Nombre"));
                sala.setMesas(rs.getInt("Mesas"));
                listSalas.add(sala);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSalas;
    }

    @Override
    public Sala obtenerSalaPorId(int id) {
        Sala sala = null;
        String query = "SELECT Nombre, Mesas FROM Sala WHERE IdSala = ?";
        
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(query)) {
             
            psm.setInt(1, id);
            try (ResultSet rs = psm.executeQuery()) {
                if (rs.next()) {
                    sala = new Sala(
                            rs.getInt("IdSala"),
                            rs.getString("Nombre"),
                            rs.getInt("Mesas")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sala;
    }

    @Override
    public Sala registrarSala(Sala sala) {
        String sqlInsert = "INSERT INTO Sala (Nombre, Mesas) VALUES (?, ?)";
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
             
            psm.setString(1, sala.getNombre());
            psm.setInt(2, sala.getMesas());

            int affectedRows = psm.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = psm.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        sala.setIdSala(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sala;
    }

    @Override
    public Sala actualizarSala(Sala sala) {
    	Connection cn = null;
        PreparedStatement psm = null;
        
        try {
        	cn = MySqlConexion.getConexion();
            String sql = "UPDATE Sala SET Nombre = ?, Mesas = ? WHERE IdSala = ?";
            psm = cn.prepareStatement(sql);
            
            psm.setString(1, sala.getNombre());
            psm.setInt(2, sala.getMesas());
            psm.setInt(3, sala.getIdSala());

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
        return sala;
    }

    @Override
    public boolean eliminarSala(int idSala) {
        String sql = "DELETE FROM Sala WHERE IdSala = ?";
        
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {

            psm.setInt(1, idSala);
            int rowsAffected = psm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
