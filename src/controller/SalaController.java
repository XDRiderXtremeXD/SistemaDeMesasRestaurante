package controller;

import dao.factory.DAOFactory;
import dao.interfaces.ISalaDao;
import model.Sala;
import view.SalasView;

import java.util.List;

import javax.swing.JOptionPane;

public class SalaController {
    
    	private ISalaDao salaDao;

    public SalaController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.salaDao = daoFactory.getSala();
    }
    
    public List<Sala> listarSalas() {
        return salaDao.listarSalas();
    }

    
    public void obtenerSala(int idSala) {
        // Obtener sala específica desde el DAO
        Sala sala = salaDao.obtenerSalaPorId(idSala);

        // Enviar los datos a la vista
        if (sala != null) {
            SalasView.mostrarSala(sala);
        } else {
            SalasView.mostrarMensaje("Sala no encontrada con ID: " + idSala);
        }
    }
    
    public void registrarSala(Sala sala) {
        // Llamar al DAO para guardar la nueva sala
        salaDao.registrarSala(sala);

        // Mostrar mensaje de confirmación
        SalasView.mostrarMensaje("Sala registrada exitosamente: " + sala.getNombre());
    }

    public void actualizarSala(Sala sala) {
        // Llamar al DAO para actualizar la sala
        boolean actualizado = salaDao.actualizarSala(sala);

        // Mostrar mensaje de confirmación
        if (actualizado) {
            SalasView.mostrarMensaje("Sala actualizada exitosamente: " + sala.getNombre());
        } else {
            SalasView.mostrarMensaje("Error al actualizar la sala con ID: " + sala.getIdSala());
        }
    }
    
    public void eliminarSala(int idSala) {
        try {
            salaDao.eliminarSala(idSala);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar la sala: " + e.getMessage());
        }
    }
}

