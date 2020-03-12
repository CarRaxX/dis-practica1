package almacen.dis.almacen;


public class Producto{

	private String codigo;
	private String nombre;
	private String descripcion;
	private int stock;
	private boolean pendiente;
	
	public enum Localizacion {
		PASILLO, ESTANTERIA, ESTANTE
	};
	
	private Localizacion localizacion;
	
	public Producto( String codigo, String nombre, String descripcion, int stock, Localizacion localizacion, boolean pendiente) { 
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.stock = stock;
		this.localizacion = localizacion;
		this.pendiente = pendiente;
	}
	
	public boolean getPendiente() {
		return pendiente;
	}

	public void setPendiente(boolean pendiente) {
		this.pendiente= pendiente;
	}
	
	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion Localizacion) {
		this.localizacion= Localizacion;
	}

	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
