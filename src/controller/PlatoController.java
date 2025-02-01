package controller;

import java.util.ArrayList;
import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.IPlatoDao;
import model.Plato;
import view.CartaDelDiaView;

public class PlatoController {

    private IPlatoDao platoDao;
    private CartaDelDiaView cartaDelDiaView;
    private List<Plato> platosEnMemoria;
    private boolean datosCargados = false;

    public PlatoController(CartaDelDiaView cartaDelDiaView) {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.platoDao = daoFactory.getPlato();
        this.cartaDelDiaView = cartaDelDiaView;
        this.platosEnMemoria = new ArrayList<>();
    }

    public void listarPlatos() {
        if (!datosCargados) {
            platosEnMemoria = platoDao.listPlatos();
            datosCargados = true;
        }
        cartaDelDiaView.listarTablaPlatos(platosEnMemoria);
    }

    public void obtenerPlato(int idPlato) {
        for (Plato plato : platosEnMemoria) {
            if (plato.getIdPlato() == idPlato) {
                System.out.println("ID: " + plato.getIdPlato() + ", Nombre: " + plato.getNombre() + 
                                   ", Precio: " + plato.getPrecio() + ", Fecha: " + plato.getFecha());
                return;
            }
        }
        System.out.println("Plato no encontrado con el ID: " + idPlato);
    }

    public void crearPlato(Plato plato) {
        Plato nuevoPlato = platoDao.createPlato(plato);
        platosEnMemoria.add(nuevoPlato);
        cartaDelDiaView.listarTablaPlatos(platosEnMemoria);
        System.out.println("Plato creado con ID: " + nuevoPlato.getIdPlato());
    }

    public void actualizarPlato(Plato plato) {
        Plato platoActualizado = platoDao.updatePlato(plato);
        for (int i = 0; i < platosEnMemoria.size(); i++) {
            if (platosEnMemoria.get(i).getIdPlato() == plato.getIdPlato()) {
                platosEnMemoria.set(i, platoActualizado);
                break;
            }
        }
        cartaDelDiaView.listarTablaPlatos(platosEnMemoria);
        System.out.println("Plato actualizado con ID: " + platoActualizado.getIdPlato());
    }

    public void eliminarPlato(int idPlato) {
        boolean eliminado = platoDao.deletePlato(idPlato);
        if (eliminado) {
            platosEnMemoria.removeIf(plato -> plato.getIdPlato() == idPlato);
            cartaDelDiaView.listarTablaPlatos(platosEnMemoria);
            System.out.println("Plato con ID: " + idPlato + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el plato con ID: " + idPlato);
        }
    }
}
