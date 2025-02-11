package dao.interfaces;

import java.util.List;

import model.Pedido;

public interface IPedidoDao {
    List<Pedido> listPedidos();
    Pedido getPedido(int id);
    Pedido createPedido(Pedido pedido);
    boolean updatePedido(Pedido pedido);
    boolean deletePedido(int id);
}