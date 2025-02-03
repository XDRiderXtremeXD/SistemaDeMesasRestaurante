package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dao.factory.DAOFactory;
import dao.interfaces.IPedidoDao;
import model.Pedido;

public class PedidoController {

    private IPedidoDao pedidoDao;
    static List<Pedido> listPedidos = new ArrayList<>();

    public PedidoController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.pedidoDao = daoFactory.getPedido();
        listPedidos = pedidoDao.listPedidos();
    }
    
    public List<Pedido> listarPedidos(boolean pendiente,boolean entregado,boolean finalizado) {
    	listPedidos = pedidoDao.listPedidos();
    	return listPedidos.stream()  
                          .filter(pedido -> (pendiente && pedido.getEstado().equals("Pendiente")) ||  (entregado && pedido.getEstado().equals("Entregado")) ||  (finalizado && pedido.getEstado().equals("Finalizado"))) 
                          .collect(Collectors.toList());
    }

    public Pedido obtenerPedido(int idPedido) {
        for (Pedido pedido : listPedidos) {
            if (pedido.getIdPedido() == idPedido) {
                System.out.println("Pedido encontrado: " + pedido.getIdPedido());
                return pedido;
            }
        }
        System.out.println("Pedido no encontrado con el ID: " + idPedido);
        return null;
    }

    public boolean crearPedido(Pedido pedido) {
        boolean creado = pedidoDao.createPedido(pedido);
        if (creado) {
            listPedidos.add(pedido);
            System.out.println("Pedido creado con ID: " + pedido.getIdPedido());
        } else {
            System.out.println("Error al crear el pedido.");
        }
        return creado;
    }

    public void actualizarPedido(Pedido pedido) {
        boolean actualizado = pedidoDao.updatePedido(pedido);
        if (actualizado) {
            for (int i = 0; i < listPedidos.size(); i++) {
                if (listPedidos.get(i).getIdPedido() == pedido.getIdPedido()) {
                    listPedidos.set(i, pedido);
                    break;
                }
            }
            System.out.println("Pedido actualizado con ID: " + pedido.getIdPedido());
        } else {
            System.out.println("Error al actualizar pedido.");
        }
    }

    public void eliminarPedido(int idPedido) {
        boolean eliminado = pedidoDao.deletePedido(idPedido);
        if (eliminado) {
            listPedidos.removeIf(p -> p.getIdPedido() == idPedido);
            System.out.println("Pedido con ID: " + idPedido + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el pedido con ID: " + idPedido);
        }
    }
}
