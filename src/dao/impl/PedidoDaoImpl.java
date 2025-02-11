package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPedidoDao;
import model.Pedido;
import utils.MySqlConexion;

public class PedidoDaoImpl implements IPedidoDao{

    @Override
    public List<Pedido> listPedidos() {
        List<Pedido> listPedidos = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "SELECT p.IdPedido, p.SalaId, p.NumeroMesa, p.Fecha, p.Total, p.Estado, p.Usuario, s.Nombre " +
                         "FROM Pedido p JOIN Sala s ON p.SalaId = s.IdSala";
            
            psm = cn.prepareStatement(sql);
            rs = psm.executeQuery();
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("IdPedido"));
                pedido.setIdSala(rs.getInt("SalaId"));
                pedido.setNumeroMesa(rs.getInt("NumeroMesa"));
                pedido.setFecha(rs.getTimestamp("Fecha").toLocalDateTime());
                pedido.setTotal(rs.getBigDecimal("Total"));
                pedido.setEstado(rs.getString("Estado"));
                pedido.setUsuario(rs.getString("Usuario"));
                pedido.setNombreSala(rs.getString("Nombre"));
                
                listPedidos.add(pedido);
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
        return listPedidos;
    }

    @Override
    public Pedido getPedido(int id) {
        Pedido pedido = null;
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "SELECT p.IdPedido, p.SalaId, p.NumeroMesa, p.Fecha, p.Total, p.Estado, p.Usuario, s.Nombre " +
                         "FROM Pedido p JOIN Sala s ON p.SalaId = s.IdSala WHERE p.IdPedido = ?";
            
            psm = cn.prepareStatement(sql);
            psm.setInt(1, id);
            rs = psm.executeQuery();
            
            if (rs.next()) {
                pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("IdPedido"));
                pedido.setIdSala(rs.getInt("SalaId"));
                pedido.setNumeroMesa(rs.getInt("NumeroMesa"));
                pedido.setFecha(rs.getTimestamp("Fecha").toLocalDateTime());
                pedido.setTotal(rs.getBigDecimal("Total"));
                pedido.setEstado(rs.getString("Estado"));
                pedido.setUsuario(rs.getString("Usuario"));
                pedido.setNombreSala(rs.getString("Nombre"));
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
        return pedido;
    }

    @Override
    public Pedido createPedido(Pedido pedido) {
        Connection cn = null;
        PreparedStatement psm = null;
        
        try {
            cn = MySqlConexion.getConexion();
            String sqlInsert = "INSERT INTO Pedido (SalaId, NumeroMesa, Total, Estado, Usuario) VALUES (?, ?, ?, ?, ?)";
            
            psm = cn.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
            psm.setInt(1, pedido.getIdSala());
            psm.setInt(2, pedido.getNumeroMesa());
            psm.setBigDecimal(3, pedido.getTotal());
            psm.setString(4, pedido.getEstado());
            psm.setString(5, pedido.getUsuario());
            
            int affectedRows = psm.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = psm.getGeneratedKeys();
                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1));
                }
                rs.close();
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
        return pedido;
    }

    @Override
    public boolean deletePedido(int id) {
        Connection cn = null;
        PreparedStatement psm = null;
        boolean eliminado = false;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "DELETE FROM Pedido WHERE IdPedido = ?";
            
            psm = cn.prepareStatement(sql);
            psm.setInt(1, id);
            
            int rowsAffected = psm.executeUpdate();
            eliminado = rowsAffected > 0;
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
        return eliminado;
    }

    @Override
    public boolean updatePedido(Pedido pedido) {
        Connection cn = null;
        PreparedStatement psm = null;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "UPDATE Pedido SET SalaId = ?, NumeroMesa = ?, Total = ?, Estado = ?, Usuario = ? WHERE IdPedido = ?";
            
            psm = cn.prepareStatement(sql);
            psm.setInt(1, pedido.getIdSala());
            psm.setInt(2, pedido.getNumeroMesa());
            psm.setBigDecimal(3, pedido.getTotal());
            psm.setString(4, pedido.getEstado());
            psm.setString(5, pedido.getUsuario());
            psm.setInt(6, pedido.getIdPedido());
            
            int resultado = psm.executeUpdate();
            return resultado > 0;
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
        return false;
    }
}
