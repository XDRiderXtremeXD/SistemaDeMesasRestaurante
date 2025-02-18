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
                return pedido;
            }
        }
        System.out.println("Pedido no encontrado con el ID: " + idPedido);
        return null;
    }

    public Pedido crearPedido(Pedido pedido) {
    	Pedido registrar = null;
        try {
            registrar = pedidoDao.createPedido(pedido);
            if (registrar != null && registrar.getIdPedido() > 0) {
            	listPedidos.add(pedido);
            } else {
            	System.out.println("Error al crear el pedido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al registrar el pedido.");
        }
        return registrar;
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
        } else {
            System.out.println("Error al actualizar pedido.");
        }
    }

    public void eliminarPedido(int idPedido) {
        boolean eliminado = pedidoDao.deletePedido(idPedido);
        if (eliminado) {
            listPedidos.removeIf(p -> p.getIdPedido() == idPedido);
        } else {
            System.out.println("No se pudo eliminar el pedido con ID: " + idPedido);
        }
    }
}
