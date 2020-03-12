package almacen.dis.almacen;

import java.util.ArrayList;
import almacen.dis.almacen.direccionEntrega.Direccion;

public class Clientes {
	
	private String nombre;
	private String apellidos;
	private String email;
	private String telfContacto;
	private ArrayList<Direccion> direccion;
	
	public Clientes(String nombre, String apellidos, String email, String telfContacto, ArrayList<Direccion> direccion) { 
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.telfContacto =telfContacto;
		this.setDireccion(new ArrayList<Direccion>());
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelfContacto() {
		return telfContacto;
	}

	public void setTelfContacto(String telfContacto) {
		this.telfContacto = telfContacto;
	}

	public ArrayList<Direccion> getDireccion() {
		return direccion;
	}

	public void setDireccion(ArrayList<Direccion> direccion) {
		this.direccion = direccion;
	}
	
}
