package controller;

import java.util.ArrayList;
import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.IDetallePedidoDao;
import model.DetallePedido;

public class DetallePedidoController {

    private IDetallePedidoDao detallePedidoDao;
    static List<DetallePedido> listDetallePedidos = new ArrayList<>();

    public DetallePedidoController() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory(DAOFactory.MYSQL);
        this.detallePedidoDao = daoFactory.getDetallePedido();
        listDetallePedidos = detallePedidoDao.listDetallePedido();
    }

    public List<DetallePedido> listarDetallePedidos() {
        return listDetallePedidos;
    }

    public DetallePedido obtenerDetallePedido(int idDetallePedido) {
        for (DetallePedido detalle : listDetallePedidos) {
            if (detalle.getIdDetallePedido() == idDetallePedido) {
                return detalle;
            }
        }
        System.out.println("Detalle de Pedido no encontrado con el ID: " + idDetallePedido);
        return null;
    }

    public boolean crearDetallePedido(DetallePedido detalle) {
        boolean creado = detallePedidoDao.createDetallePedido(detalle);
        if (creado) {
            listDetallePedidos.add(detalle);
        } else {
            System.out.println("Error al crear el detalle de pedido.");
        }
        return creado;
    }

    public void actualizarDetallePedido(DetallePedido detalle) {
        boolean actualizado = detallePedidoDao.updateDetallePedido(detalle);
        if (actualizado) {
            for (int i = 0; i < listDetallePedidos.size(); i++) {
                if (listDetallePedidos.get(i).getIdDetallePedido() == detalle.getIdDetallePedido()) {
                    listDetallePedidos.set(i, detalle);
                    break;
                }
            }
        } else {
            System.out.println("Error al actualizar detalle de pedido.");
        }
    }

    public void eliminarDetallePedido(int idDetallePedido) {
        boolean eliminado = detallePedidoDao.deleteDetallePedido(idDetallePedido);
        if (eliminado) {
            listDetallePedidos.removeIf(detalle -> detalle.getIdDetallePedido() == idDetallePedido);
        } else {
            System.out.println("No se pudo eliminar el detalle de pedido con ID: " + idDetallePedido);
        }
    }
    
    public List<DetallePedido> listarDetallePedidosPorPedido(int idPedido) {
        List<DetallePedido> detallePedidos = detallePedidoDao.listDetallePedidoById(idPedido);
        if (detallePedidos.isEmpty()) {
            System.out.println("No se encontraron detalles de pedido para el ID: " + idPedido);
        }
        return detallePedidos;
    }

    public List<DetallePedido> obtenerDetallesPorPedido(int idPedido) {
        return detallePedidoDao.listDetallePedidoById(idPedido);
    }
}
