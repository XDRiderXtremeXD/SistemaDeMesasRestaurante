package dao.interfaces;

import java.util.List;

import model.DetallePedido;

public interface IDetallePedidoDao {
	List<DetallePedido> listDetallePedido();
	DetallePedido getDetallePedido(int id);
	boolean createDetallePedido(DetallePedido detallePedido);
	boolean updateDetallePedido(DetallePedido detallePedido);
	boolean deleteDetallePedido(int id);
}
