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
            SalasView.mostrarMensaje("Error al obtener la lista de salas.");
            return null;
        }
    }

    public void obtenerSalaPorId(int idSala) {
        try {
            Sala sala = salaDao.obtenerSalaPorId(idSala);
            if (sala != null) {
                SalasView.mostrarSala(sala);
            } else {
                SalasView.mostrarMensaje("Sala no encontrada con ID: " + idSala);
            }
        } catch (Exception e) {
            e.printStackTrace();
            SalasView.mostrarMensaje("Error al obtener la sala.");
        }
    }
    
    public void registrarSala(Sala sala) {
        try {
            Sala registrar = salaDao.registrarSala(sala);
            if (registrar != null && registrar.getIdSala() > 0) {
                SalasView.mostrarMensaje("Sala registrada exitosamente: " + registrar.getNombre());
            } else {
                SalasView.mostrarMensaje("Error al registrar la sala");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SalasView.mostrarMensaje("Ocurrió un error al registrar la sala.");
        }
    }

    public void actualizarSala(Sala sala) {
        try {
            boolean actualizar = salaDao.actualizarSala(sala);
            if (actualizar) {
                SalasView.mostrarMensaje("Sala actualizada exitosamente: " + sala.getNombre());
            } else {
                SalasView.mostrarMensaje("Error al actualizar la sala con ID: " + sala.getIdSala());
            }
        } catch (Exception e) {
            e.printStackTrace();
            SalasView.mostrarMensaje("Error al actualizar la sala.");
        }
    }
    
    public void eliminarSala(int idSala) {
        try {
            boolean eliminar = salaDao.eliminarSala(idSala);
            if (eliminar) {
                SalasView.mostrarMensaje("Sala eliminada exitosamente");
            } else {
                SalasView.mostrarMensaje("Error al eliminar la sala");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SalasView.mostrarMensaje("Error al eliminar la sala.");
        }
    }
}
