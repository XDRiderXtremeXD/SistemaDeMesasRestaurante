package controller;

import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.IPlatoDao;
import model.Plato;
import view.CartaDelDiaView;

public class PlatoController {

	private IPlatoDao platoDao;
	private CartaDelDiaView cartaDelDiaView;

    public PlatoController(CartaDelDiaView cartaDelDiaView) {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.platoDao = daoFactory.getPlato();
        this.cartaDelDiaView = cartaDelDiaView;
    }
    
    public void listarPlatos() {
        List<Plato> platos = platoDao.listPlatos();
        cartaDelDiaView.listarTablaPlatos(platos);
    }
    
    public void obtenerPlato(int idPlato) {
        Plato plato = platoDao.getPlato(idPlato);
        if (plato != null) {
            System.out.println("ID: " + plato.getIdPlato() + ", Nombre: " + plato.getNombre() + ", Precio: " + plato.getPrecio() + ", Fecha: " + plato.getFecha());
        } else {
            System.out.println("Plato no encontrado con el ID: " + idPlato);
        }
    }
    
    public void crearPlato(Plato plato) {
        Plato nuevoPlato = platoDao.createPlato(plato);
        System.out.println("Plato creado con ID: " + nuevoPlato.getIdPlato());
    }
    
    public void actualizarPlato(Plato plato) {
        Plato platoActualizado = platoDao.updatePlato(plato);
        System.out.println("Plato actualizado con ID: " + platoActualizado.getIdPlato());
    }
    
    public void eliminarPlato(int idPlato) {
        boolean eliminado = platoDao.deletePlato(idPlato);
        if (eliminado) {
            System.out.println("Plato con ID: " + idPlato + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el plato con ID: " + idPlato);
        }
    }
    
}
