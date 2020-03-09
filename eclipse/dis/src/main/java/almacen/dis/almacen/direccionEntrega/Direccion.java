package almacen.dis.almacen.direccionEntrega;

public class Direccion {
	
	private String calle;
	private int numero;
	private int codigoPostal;
	private String población;
	private String pais;

	public Direccion(String calle, int numero, int codigoPostal, String población, String pais) {
		this.calle = calle;
		this.numero = numero;
		this.codigoPostal = codigoPostal;
		this.población = población;
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

	public String getPoblación() {
		return población;
	}

	public void setPoblación(String población) {
		this.población = población;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
