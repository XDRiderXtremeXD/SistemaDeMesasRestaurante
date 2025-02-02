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
                System.out.println("Detalle de Pedido encontrado: " + detalle.getIdDetallePedido());
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
            System.out.println("Detalle de Pedido creado con ID: " + detalle.getIdDetallePedido());
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
            System.out.println("Detalle de Pedido actualizado con ID: " + detalle.getIdDetallePedido());
        } else {
            System.out.println("Error al actualizar detalle de pedido.");
        }
    }

    public void eliminarDetallePedido(int idDetallePedido) {
        boolean eliminado = detallePedidoDao.deleteDetallePedido(idDetallePedido);
        if (eliminado) {
            listDetallePedidos.removeIf(detalle -> detalle.getIdDetallePedido() == idDetallePedido);
            System.out.println("Detalle de Pedido con ID: " + idDetallePedido + " eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el detalle de pedido con ID: " + idDetallePedido);
        }
    }
}