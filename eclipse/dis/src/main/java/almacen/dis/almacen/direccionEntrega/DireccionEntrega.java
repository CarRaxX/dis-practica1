package almacen.dis.almacen.direccionEntrega;

public class DireccionEntrega {
	
	private String calle;
	private int numero;
	private int codigoPostal;
	private String poblacion;
	private String pais;
	
	public DireccionEntrega(String calle, int numero, int codigoPostal, String poblacion, String pais) {
		this.calle = calle;
		this.numero = numero;
		this.codigoPostal = codigoPostal;
		this.poblacion = poblacion;
		this.pais = pais;
	}

	public String getcalle() {
		return calle;
	}

	public void setcalle(String calle) {
		this.calle = calle;
	}

	public int getnumero() {
		return numero;
	}

	public void setnumero(int numero) {
		this.numero = numero;
	}

	public int getcodigoPostal() {
		return codigoPostal;
	}

	public void setcodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	public String getpoblacion() {
		return poblacion;
	}

	public void setpoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	
	public String getpais() {
		return pais;
	}

	public void setpais(String pais) {
		this.pais = pais;
	}
	
}
