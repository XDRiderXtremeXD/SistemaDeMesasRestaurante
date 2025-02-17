package controller;

import dao.factory.DAOFactory;
import dao.interfaces.ISalaDao;
import model.Sala;
import java.util.List;

public class SalaController {
    
    private ISalaDao salaDao;

    public SalaController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.salaDao = daoFactory.getSala();
    }
    
    public List<Sala> listarSalas() {
        try {
            return salaDao.listarSalas();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener la lista de salas.");
            return null;
        }
    }

    public void obtenerSalaPorId(int idSala) {
        try {
            salaDao.obtenerSalaPorId(idSala);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener la sala.");
        }
    }
    
    public Sala registrarSala(Sala sala) {
        Sala registrar = null;
        try {
            registrar = salaDao.registrarSala(sala);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al registrar la sala.");
        }
        return registrar;
    }

    public Sala actualizarSala(Sala sala) {
    	Sala actualizar = null;
        try {
            actualizar = salaDao.actualizarSala(sala);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la sala.");
        }
        return actualizar;
    }
    
    public void eliminarSala(int idSala) {
        try {
            salaDao.eliminarSala(idSala);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar la sala.");
        }
    }
}
