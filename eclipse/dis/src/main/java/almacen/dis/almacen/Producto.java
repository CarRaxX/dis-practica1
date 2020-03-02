package almacen.dis.almacen;


public class Producto<Pendiente> {
	
	
	private String Codigo;
	private String Nombre;
	private String Descripcion;
	private int Stock;
	
	
	public enum Localizacion {
		Pasillo, Estanteria, Estante
	};
	
	private Localizacion localizacion;
	private Boolean Pendiente;
	
	
	public Producto( String Codigo, String Nombre, String Descripcion, int Stock, Localizacion localizacion, Boolean Pendiente ) { 
		
		this.Codigo = Codigo;
		this.Nombre = Nombre;
		this.Descripcion = Descripcion;
		this.Stock = Stock;
		this.localizacion = localizacion;
		this.Pendiente = Pendiente;
	}
	
	
	public Boolean getPendiente() {
		return Pendiente;
	}

	public void setPendiente(Boolean pendiente) {
		this.Pendiente= Pendiente;
	}
	
	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion Localizacion) {
		this.localizacion= Localizacion;
	}

	
	public int getStock() {
		return Stock;
	}

	public void setStock(int Stock) {
		this.Stock = Stock;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.Descripcion = Descripcion;
	}
	
	public String getCodigo() {
		return Codigo;
	}

	public void setCodigo(String Codigo) {
		this.Codigo = Codigo;
	}
	
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String Nombre) {
		this.Nombre = Nombre;
	}

	
	
}
