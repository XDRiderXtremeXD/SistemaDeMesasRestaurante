package dao.factory;

import dao.interfaces.IPlatoDao;
import dao.interfaces.ISalaDao;
import dao.interfaces.IUsuarioDao;

public abstract class DAOFactory {
	public static final int MYSQL = 1;
	public static final int SQLSERVER=2;
	public static final int ORACLE=3;
	
	public abstract ISalaDao getSala();
	public abstract IPlatoDao getPlato();
	public abstract IUsuarioDao getUsuario();
	
	public static DAOFactory getDaoFactory(int tipo) {
		switch (tipo) {
		case MYSQL:
			return new MySqlDAOFactory();
		case SQLSERVER:
			return null;
		case ORACLE:
			return null;
		default:
			return null;
		}
	}
}
