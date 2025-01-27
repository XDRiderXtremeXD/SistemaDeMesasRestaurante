package dao.interfaces;

import java.util.List;

import model.Plato;

public interface IPlatoDao {
	List<Plato> listPlatos();
	Plato getPlato(int id);
	Plato createPlato(Plato plato);
	Plato updatePlato(Plato plato);
	boolean deletePlato(int idPlato);
}
