package almacen.dis.almacen;

import java.util.GregorianCalendar;

public class Producto {
	
	private String puesto;
	private String empresa;
	private GregorianCalendar fechaInicio;
	private GregorianCalendar fechaFin;
	private String detalles;
	
	public Producto(String puesto, String empresa, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) { 
		this.puesto = puesto;
		this.empresa = empresa;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.detalles = "";
	}
	
	public Producto(String puesto, String empresa, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, String detalles) { 
		this.puesto = puesto;
		this.empresa = empresa;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.detalles = detalles;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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
	
	
}
