package controller;

import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.IPlatoDao;
import model.Plato;

public class PlatoController {

    private IPlatoDao dao;

    public PlatoController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.dao = daoFactory.getPlato();
    }

    public List<Plato> listar() {
    	System.out.print(dao.list());
        return dao.list();
    }

    public Plato obtener(int idPlato) {
        return dao.get(idPlato);
    }

    public Plato crear(Plato plato) {
        return dao.create(plato);
    }

    public Plato actualizar(Plato plato) {
        return dao.update(plato);
    }

    public boolean eliminar(int idPlato) {
        boolean eliminado = dao.delete(idPlato);
        if (eliminado) {
            return true;
        } else {
            return false;
        }
    }
}
