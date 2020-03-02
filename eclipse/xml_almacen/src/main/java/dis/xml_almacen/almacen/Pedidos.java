package disg4.xmlcv.cv;

import java.util.GregorianCalendar;

public class Pedidos {
	
	private String nombre;
	private String entidad;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	private String detalles;
	
	public Pedidos(String nombre, String entidad, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) { 
		this.nombre = nombre;
		this.entidad = entidad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.detalles = "";
	}
	
	public Pedidos(String nombre, String entidad, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, String detalles) { 
		this.nombre = nombre;
		this.entidad = entidad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.detalles = detalles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public GregorianCalendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(GregorianCalendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
	
	
}
