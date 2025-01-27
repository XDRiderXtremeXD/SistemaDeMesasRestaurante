package model;

public class Sala {
	private int idSala;
	private String nombre;
	private int mesas;

	public Sala() {
    }
	
	public Sala(int idSala, String nombre, int mesas) {
		super();
		this.idSala = idSala;
		this.nombre = nombre;
		this.mesas = mesas;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getMesas() {
		return mesas;
	}

	public void setMesas(int mesas) {
		this.mesas = mesas;
	}
}
