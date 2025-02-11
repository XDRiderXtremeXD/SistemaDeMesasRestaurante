package controller;

import dao.factory.DAOFactory;
import dao.interfaces.ISalaDao;
import model.Sala;
import view.SalasView;
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
            Sala sala = salaDao.obtenerSalaPorId(idSala);
            if (sala != null) {
                SalasView.mostrarSala(sala);
            } else {
            	System.out.println("Sala no encontrada con ID: " + idSala);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al obtener la sala.");
        }
    }
    
    public Sala registrarSala(Sala sala) {
        Sala registrar = null;
        try {
            registrar = salaDao.registrarSala(sala);
            if (registrar != null && registrar.getIdSala() > 0) {
            	System.out.println("Sala registrada exitosamente: " + registrar.getNombre());
            } else {
            	System.out.println("Error al registrar la sala");
            }
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
            if (actualizar != null && actualizar.getIdSala() > 0) {
            	System.out.println("Sala actualizada exitosamente: " + sala.getNombre());
            } else {
            	System.out.println("Error al actualizar la sala con ID: " + sala.getIdSala());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la sala.");
        }
        return actualizar;
    }
    
    public void eliminarSala(int idSala) {
        try {
            boolean eliminar = salaDao.eliminarSala(idSala);
            if (eliminar) {
            	System.out.println("Sala eliminada exitosamente");
            } else {
            	System.out.println("Error al eliminar la sala");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar la sala.");
        }
    }
}
