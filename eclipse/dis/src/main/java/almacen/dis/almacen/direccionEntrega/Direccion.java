package almacen.dis.almacen.direccionEntrega;

public class Direccion {
	
	private String calle;
	private int numero;
	private int codigoPostal;
	private String poblaci�n;
	private String pais;

	public Direccion(String calle, int numero, int codigoPostal, String poblaci�n, String pais) {
		this.calle = calle;
		this.numero = numero;
		this.codigoPostal = codigoPostal;
		this.poblaci�n = poblaci�n;
		this.pais = pais;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPoblaci�n() {
		return poblaci�n;
	}

	public void setPoblaci�n(String poblaci�n) {
		this.poblaci�n = poblaci�n;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
