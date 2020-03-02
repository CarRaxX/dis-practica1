package almacen.dis.almacen;

public class Pedidos {

	public enum Nivel {
		BASICO, MEDIO, FLUIDO, NATIVO
	};

	private String nombre;
	private Nivel nivel;

	public Pedidos(String nombre, Nivel nivel) {
		this.nombre = nombre;
		this.nivel = nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setnivel(Nivel nivel) {
		this.nivel = nivel;
	}

}
