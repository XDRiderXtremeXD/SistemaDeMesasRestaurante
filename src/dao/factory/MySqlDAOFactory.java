package dao.factory;

import dao.impl.DetallePedidoDaoImpl;
import dao.impl.PedidoDaoImpl;
import dao.impl.PlatoDaoImpl;
import dao.impl.SalaDaoImpl;
import dao.impl.UsuarioDaoImpl;
import dao.interfaces.IDetallePedidoDao;
import dao.interfaces.IPedidoDao;
import dao.interfaces.IPlatoDao;
import dao.interfaces.ISalaDao;
import dao.interfaces.IUsuarioDao;

public class MySqlDAOFactory extends DAOFactory {
	
	@Override
	public ISalaDao getSala() {
		return new SalaDaoImpl();
	}
	
	@Override
	public IPlatoDao getPlato() {
		return new PlatoDaoImpl();
	}

	@Override
	public IUsuarioDao getUsuario() {
		return new UsuarioDaoImpl();
	}

	@Override
	public IDetallePedidoDao getDetallePedido() {
		return new DetallePedidoDaoImpl();
	}

	@Override
	public IPedidoDao getPedido() {
		return new PedidoDaoImpl();
	}
}
