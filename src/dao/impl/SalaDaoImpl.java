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
        String sql = "SELECT Id, Nombre, Mesas FROM Sala";
        
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql);
             ResultSet rs = psm.executeQuery()) {

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setIdSala(rs.getInt("Id"));
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
        String query = "SELECT Nombre, Mesas FROM Sala WHERE Id = ?";
        
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(query)) {
             
            psm.setInt(1, id);
            try (ResultSet rs = psm.executeQuery()) {
                if (rs.next()) {
                    sala = new Sala(
                            rs.getInt("Id"),
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
    public boolean actualizarSala(Sala sala) {
        String sql = "UPDATE Sala SET Nombre = ?, Mesas = ? WHERE Id = ?";
        
        try (Connection cn = MySqlConexion.getConexion();
             PreparedStatement psm = cn.prepareStatement(sql)) {

            psm.setString(1, sala.getNombre());
            psm.setInt(2, sala.getMesas());
            psm.setInt(3, sala.getIdSala());

            int resultado = psm.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarSala(int idSala) {
        String sql = "DELETE FROM Sala WHERE Id = ?";
        
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

    // Método auxiliar para cerrar los recursos
    private void closeResources(Connection cn, PreparedStatement psm, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (psm != null) psm.close();
            if (cn != null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
