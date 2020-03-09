package almacen.dis.almacen;

import java.util.ArrayList;

import almacen.dis.almacen.direccionEntrega.DireccionEntrega;

public class Pedidos {

	private String productos;
	private int cantidad;
	private String destinatario;
	private String fechaDeEntregaEstimada;
	private ArrayList<DireccionEntrega> direccionEntrega;

	public Pedidos(String productos, int cantidad, String destinatario, String fechaDeEntregaEstimada, ArrayList<DireccionEntrega> direccionEntrega) {
		this.productos = productos;
		this.cantidad = cantidad;
		this.destinatario = destinatario;
		this.fechaDeEntregaEstimada = fechaDeEntregaEstimada;
		this.setDireccionEntrega(new ArrayList<DireccionEntrega>());
	}

	public String getproductos() {
		return productos;
	}

	public void setproductos(String productos) {
		this.productos = productos;
	}

	public int getcantidad() {
		return cantidad;
	}

	public void setcantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getdestinatario() {
		return destinatario;
	}

	public void setdestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public String getfechaDeEntregaEstimada() {
		return fechaDeEntregaEstimada;
	}

	public void setfechaDeEntregaEstimada(String fechaDeEntregaEstimada) {
		this.fechaDeEntregaEstimada = fechaDeEntregaEstimada;
	}

	public ArrayList<DireccionEntrega> getDireccionEntrega() {
		return direccionEntrega;
	}

	public void setDireccionEntrega(ArrayList<DireccionEntrega> direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}
}
