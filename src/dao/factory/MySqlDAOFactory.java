package dao.factory;

import dao.impl.PlatoDaoImpl;
import dao.impl.SalaDaoImpl;
import dao.impl.UsuarioDaoImpl;
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
		// TODO Auto-generated method stub
		return new UsuarioDaoImpl();
	}
}
