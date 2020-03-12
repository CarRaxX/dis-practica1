package almacen.dis.almacen;

import java.util.ArrayList;

import almacen.dis.almacen.direccionEntrega.DireccionEntrega;

public class Pedidos {

	private ArrayList<Producto> productos;
	private int cantidad;
	private ArrayList<DireccionEntrega> direccionEntrega;
	private String destinatario;
	private String fechaDeEntregaEstimada;

	public Pedidos(ArrayList<Producto> productos, int cantidad, ArrayList<DireccionEntrega> direccionEntrega, String destinatario, String fechaDeEntregaEstimada){
		this.setProductos(new ArrayList<Producto>());
		this.cantidad = cantidad;
		this.destinatario = destinatario;
		this.fechaDeEntregaEstimada = fechaDeEntregaEstimada;
		this.setDireccionEntrega(new ArrayList<DireccionEntrega>());
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public ArrayList<DireccionEntrega> getDireccionEntrega() {
		return direccionEntrega;
	}

	public void setDireccionEntrega(ArrayList<DireccionEntrega> direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getFechaDeEntregaEstimada() {
		return fechaDeEntregaEstimada;
	}

	public void setFechaDeEntregaEstimada(String fechaDeEntregaEstimada) {
		this.fechaDeEntregaEstimada = fechaDeEntregaEstimada;
	}
}
