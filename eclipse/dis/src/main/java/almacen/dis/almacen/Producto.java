package almacen.dis.almacen;

import java.util.GregorianCalendar;

public class Producto {
	
	
	private String codigo;
	private String nombre;
	private String empresa;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	private String detalles;
	
	public Producto( String codigo, String nombre, ) { 
		
		this.codigo = codigo;
		this.nombre = nombre;
		
	}
	
	

	public String getcodigo() {
		return codigo;
	}

	public void setcodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getnombre() {
		return nombre;
	}

	public void setnombre(String nombre) {
		this.nombre = nombre;
	}

	
	
}
