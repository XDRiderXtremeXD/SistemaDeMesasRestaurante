package dao.interfaces;

import java.util.List;

import model.Sala;

public interface ISalaDao {
	List<Sala> listarSalas();
	Sala obtenerSalaPorId(int id);
	Sala registrarSala(Sala sala);
	Sala actualizarSala(Sala sala);
	boolean eliminarSala(int id);
}
