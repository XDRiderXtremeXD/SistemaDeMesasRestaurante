package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IDetallePedidoDao;
import model.DetallePedido;
import utils.MySqlConexion;

public class DetallePedidoDaoImpl implements IDetallePedidoDao{

    @Override
    public List<DetallePedido> listDetallePedido() {
        List<DetallePedido> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "SELECT IdDetallePedido, NombreProducto, Precio, Cantidad, Comentario, PedidoId FROM DetallePedido";
            psm = cn.prepareStatement(sql);
            rs = psm.executeQuery();
            
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdDetallePedido(rs.getInt("IdDetallePedido"));
                detalle.setNombreProducto(rs.getString("NombreProducto"));
                detalle.setPrecio(rs.getBigDecimal("Precio"));
                detalle.setCantidad(rs.getInt("Cantidad"));
                detalle.setComentario(rs.getString("Comentario"));
                detalle.setIdDetallePedido(rs.getInt("PedidoId"));
                
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public DetallePedido getDetallePedido(int id) {
        DetallePedido detalle = null;
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "SELECT IdDetallePedido, NombreProducto, Precio, Cantidad, Comentario, PedidoId FROM DetallePedido WHERE IdDetallePedido = ?";
            psm = cn.prepareStatement(sql);
            psm.setInt(1, id);
            rs = psm.executeQuery();
            
            if (rs.next()) {
                detalle = new DetallePedido();
                detalle.setIdDetallePedido(rs.getInt("IdDetallePedido"));
                detalle.setNombreProducto(rs.getString("NombreProducto"));
                detalle.setPrecio(rs.getBigDecimal("Precio"));
                detalle.setCantidad(rs.getInt("Cantidad"));
                detalle.setComentario(rs.getString("Comentario"));
                detalle.setIdDetallePedido(rs.getInt("PedidoId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return detalle;
    }

    @Override
    public boolean createDetallePedido(DetallePedido detalle) {
        Connection cn = null;
        PreparedStatement psm = null;
        boolean success = false;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "INSERT INTO DetallePedido (NombreProducto, Precio, Cantidad, Comentario, PedidoId) VALUES (?, ?, ?, ?, ?)";
            psm = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            psm.setString(1, detalle.getNombreProducto());
            psm.setBigDecimal(2, detalle.getPrecio());
            psm.setInt(3, detalle.getCantidad());
            psm.setString(4, detalle.getComentario());
            psm.setInt(5, detalle.getIdDetallePedido());
            
            int rowsAffected = psm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = psm.getGeneratedKeys();
                if (rs.next()) {
                    detalle.setIdDetallePedido(rs.getInt(1));
                }
                success = true;
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
        return success;
    }

    @Override
    public boolean updateDetallePedido(DetallePedido detalle) {
        Connection cn = null;
        PreparedStatement psm = null;
        boolean success = false;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "UPDATE DetallePedido SET NombreProducto = ?, Precio = ?, Cantidad = ?, Comentario = ?, PedidoId = ? WHERE IdDetallePedido = ?";
            psm = cn.prepareStatement(sql);
            
            psm.setString(1, detalle.getNombreProducto());
            psm.setBigDecimal(2, detalle.getPrecio());
            psm.setInt(3, detalle.getCantidad());
            psm.setString(4, detalle.getComentario());
            psm.setInt(5, detalle.getIdPedido());
            psm.setInt(6, detalle.getIdDetallePedido());
            
            int rowsAffected = psm.executeUpdate();
            success = rowsAffected > 0;
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
        return success;
    }

    @Override
    public boolean deleteDetallePedido(int id) {
        Connection cn = null;
        PreparedStatement psm = null;
        boolean success = false;
        
        try {
            cn = MySqlConexion.getConexion();
            String sql = "DELETE FROM DetallePedido WHERE IdDetallePedido = ?";
            psm = cn.prepareStatement(sql);
            psm.setInt(1, id);
            
            int rowsAffected = psm.executeUpdate();
            success = rowsAffected > 0;
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
        return success;
    }
    
    @Override
    public List<DetallePedido> listDetallePedidoById(int idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        
        try {
            cn = MySqlConexion.getConexion();
            // SQL para obtener los detalles de un pedido por IdPedido
            String sql = "SELECT IdDetallePedido, NombreProducto, Precio, Cantidad, Comentario, PedidoId FROM DetallePedido WHERE PedidoId = ?";
            psm = cn.prepareStatement(sql);
            psm.setInt(1, idPedido);  // Establece el valor del IdPedido
            
            rs = psm.executeQuery();
            
            while (rs.next()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setIdDetallePedido(rs.getInt("IdDetallePedido"));
                detalle.setNombreProducto(rs.getString("NombreProducto"));
                detalle.setPrecio(rs.getBigDecimal("Precio"));
                detalle.setCantidad(rs.getInt("Cantidad"));
                detalle.setComentario(rs.getString("Comentario"));
                detalle.setIdPedido(rs.getInt("PedidoId"));
                
                lista.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psm != null) psm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

}

