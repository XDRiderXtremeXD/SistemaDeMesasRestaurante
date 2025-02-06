package dao.interfaces;

import java.util.List;

import model.Plato;

public interface IPlatoDao {
	List<Plato> list();
	Plato get(int id);
	Plato create(Plato plato);
	Plato update(Plato plato);
	boolean delete(int idPlato);
}
