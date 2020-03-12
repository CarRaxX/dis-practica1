package almacen.dis.almacen;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import almacen.dis.almacen.Producto.Localizacion;
import almacen.dis.almacen.direccionEntrega.Direccion;
import almacen.dis.almacen.direccionEntrega.DireccionEntrega;

public class Almacen {
	
	private String almacenID;
	
	private ArrayList<Clientes> clientes;
	private ArrayList<Producto> productos;
	private ArrayList<Pedidos> pedidos;
	private ArrayList<Direccion> direcciones;
	private ArrayList<DireccionEntrega> direccionesEntrega;

	public Almacen() {
		this.clientes = new ArrayList<Clientes>();
		this.productos = new ArrayList<Producto>();
		this.pedidos = new ArrayList<Pedidos>();
	}

	public Almacen(String almacenID, String filePath) {
		this.almacenID = almacenID;
		this.clientes = new ArrayList<Clientes>();
		this.productos = new ArrayList<Producto>();
		this.pedidos = new ArrayList<Pedidos>();
		loadFile(filePath);
	}

	public Almacen(String almacenID) {
		this.almacenID = almacenID;
		this.clientes = new ArrayList<Clientes>();
		this.productos = new ArrayList<Producto>();
		this.pedidos = new ArrayList<Pedidos>();
	}

	public boolean saveFile(String path) {
		try {

			Element rootNode = new Element("Almacen_" + almacenID);
			Document doc = new Document(rootNode);

			Element node1 = new Element("DatosAlmacen");
			node1.addContent(new Element("AlmacenID").setText(almacenID));

			doc.getRootElement().addContent(node1);

			Element node2 = new Element("Clientes");
			for (Clientes cliente : clientes) {
				Element node21 = new Element("Cliente");
				node21.addContent(new Element("Nombre").setText(cliente.getNombre()));
				node21.addContent(new Element("Apellidos").setText(cliente.getApellidos()));
				node21.addContent(new Element("Email").setText(cliente.getEmail()));
				node21.addContent(new Element("Telf. Contacto").setText(cliente.getTelfContacto()));

				for (Direccion direccion : cliente.getDireccion()) {
					Element node211 = new Element("Direccion");
					node211.addContent(new Element("Calle").setText(direccion.getCalle()));
					node211.addContent(new Element("Número").setText(Integer.toString(direccion.getNumero())));
					node211.addContent(new Element("Código Postal").setText(Integer.toString(direccion.getCodigoPostal())));
					node211.addContent(new Element("Población").setText(direccion.getPoblacion()));
					node211.addContent(new Element("País").setText(direccion.getPais()));
					
					node21.addContent(node211);
				}

				node2.addContent(node21);
			}
			doc.getRootElement().addContent(node2);

			Element node3 = new Element("Productos");
			for (Producto producto : productos) {
				Element node31 = new Element("Producto");
				node31.addContent(new Element("Código").setText(producto.getCodigo()));
				node31.addContent(new Element("Nombre").setText(producto.getNombre()));
				node31.addContent(new Element("Descripción").setText(producto.getDescripcion()));
				node31.addContent(new Element("Stock").setText(Integer.toString(producto.getStock())));
				node31.addContent(new Element("Localización").setText(producto.getLocalizacion().toString()));
				node31.addContent(new Element("Pendientes").setText(Boolean.toString(producto.getPendiente())));
				
				node3.addContent(node31);
			}
			doc.getRootElement().addContent(node3);

			Element node4 = new Element("Pedidos");
			for (Pedidos pedido : pedidos) {
				Element node41 = new Element("Pedido");
				
				for (Producto producto : productos) {
					Element node411 = new Element("Producto");
					node411.addContent(new Element("Código").setText(producto.getCodigo()));
					node411.addContent(new Element("Nombre").setText(producto.getNombre()));
					node411.addContent(new Element("Descripción").setText(producto.getDescripcion()));
					node411.addContent(new Element("Stock").setText(Integer.toString(producto.getStock())));
					node411.addContent(new Element("Pendientes").setText(Boolean.toString(producto.getPendiente())));
					
					node41.addContent(node411);
				}
				
				node41.addContent(new Element("Cantidad").setText(Integer.toString(pedido.getCantidad())));
				
				for (DireccionEntrega direccionEntrega : pedido.getDireccionEntrega()) {
					Element node421 = new Element("Direccion de entrega");
					node421.addContent(new Element("Calle").setText(direccionEntrega.getCalle()));
					node421.addContent(new Element("Número").setText(Integer.toString(direccionEntrega.getNumero())));
					node421.addContent(new Element("Código Postal").setText(Integer.toString(direccionEntrega.getCodigoPostal())));
					node421.addContent(new Element("Población").setText(direccionEntrega.getPoblacion()));
					node421.addContent(new Element("País").setText(direccionEntrega.getPais()));
					
					node41.addContent(node421);
				}
				
				node41.addContent(new Element("Destinatario").setText(pedido.getDestinatario()));
				node41.addContent(new Element("Fecha de entrega estimada").setText(pedido.getFechaDeEntregaEstimada()));
				
				
				node4.addContent(node41);
			}
			doc.getRootElement().addContent(node4);

			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmlOutput.output(doc, new FileWriter(path));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	public boolean loadFile(String path) {
		SAXBuilder builder = new SAXBuilder();
		// Damos por valida la ruta absoluta al archivo a cargar
		File xmlFile = new File(path);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();

			Element node1 = rootNode.getChild("DatosAlmacen");

			this.almacenID = node1.getChildText("AlmacenID");

			Element node2 = rootNode.getChild("Clientes");
			for (Element node21 : node2.getChildren("Cliente")) {
				for (Element node211 : node21.getChildren("Direccion")) {
					direcciones.add(new Direccion(node211.getChildText("Calle"), Integer.parseInt(node211.getChildText("Numero")), Integer.parseInt(node211.getChildText("Código Postal")), node211.getChildText("Población"), node211.getChildText("País")));
					clientes.add(new Clientes(node21.getChildText("Nombre"), node21.getChildText("Apellidos"), node21.getChildText("Email"), node21.getChildText("Telf. Contacto"), direcciones));	
				}
			}

			Element node3 = rootNode.getChild("Productos");
			for (Element node31 : node3.getChildren("Producto")) {
				productos.add(new Producto(node31.getChildText("Código"), node31.getChildText("Nombre"), node31.getChildText("Descripción"), Integer.parseInt(node31.getChildText("Stock")), Localizacion.valueOf(node31.getChildText("Localización")), Boolean.parseBoolean(node31.getChildText("Pendientes"))));
			}

			Element node4 = rootNode.getChild("Pedidos");
			for (Element node41 : node4.getChildren("Pedido")) {
				for (Element node411 : node4.getChildren("Producto")) {
					for (Element node421 : node4.getChildren("Direccion de entrega")) {
						productos.add(new Producto(node411.getChildText("Código"), node411.getChildText("Nombre"), node411.getChildText("Descripción"), Integer.parseInt(node411.getChildText("Stock")), Localizacion.valueOf(node411.getChildText("Localización")), Boolean.parseBoolean(node411.getChildText("Pendientes"))));
						direccionesEntrega.add(new DireccionEntrega(node421.getChildText("Calle"), Integer.parseInt(node421.getChildText("Numero")), Integer.parseInt(node421.getChildText("Código Postal")), node421.getChildText("Población"), node421.getChildText("País")));
						pedidos.add(new Pedidos(productos, Integer.parseInt(node41.getChildText("Cantidad")), direccionesEntrega, node41.getChildText("Destinatario"), node41.getChildText("Fecha de entrega estimada")));
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void printAlmacen(String encabezado, boolean mostrarDatosAlmacen, boolean mostrarClientes,
			boolean mostrarProductos, boolean mostrarPedidos) {
		String output = "=================[ " + encabezado + " ]=================\n";

		if (mostrarDatosAlmacen) {
			output += "- Datos del Almacén\n";
			output += "\tAlmacénID: " + almacenID + "\n";
		}

		if (mostrarClientes) {
			output += "- Clientes\n";
			if (clientes.size() > 0) {
				if (direcciones.size() > 0) {
					for (Clientes cliente : clientes) {
						for(Direccion direccion : direcciones) {
							output += "\t[Nombre y Apellidos: " + cliente.getNombre() + " " + cliente.getApellidos()
									+ "\n\t\tEmail: " + cliente.getEmail()
									+ "\n\t\tTelf. de contacto: " + cliente.getTelfContacto()
									+ "\n\t\t- Dirección"
									+ "\n\t\t\tCalle:" + direccion.getCalle()
									+ "\n\t\t\tNúmero:" + direccion.getNumero()
									+ "\n\t\t\tCódigo Postal:" + direccion.getCodigoPostal()
									+ "\n\t\t\tPoblación:" + direccion.getPoblacion()
									+ "\n\t\t\tPaís:" + direccion.getPais() + "]\n";
						}
					}
				}
				
				else {
					output += "\tSin direciones...\n";
				}
			} 
			
			else {
				output += "\tSin clientes...\n";
			}
		}

		if (mostrarProductos) {
			output += "- Productos\n";
			if (productos.size() > 0) {
				for (Producto producto : productos) {
					output += "\t[Codigo: " + producto.getCodigo()
							+ "\n\t\tNombre: " + producto.getNombre()
							+ "\n\t\tDescripción: " + producto.getDescripcion()
							+ "\n\t\tStock: " + producto.getStock() 
							+ "\n\t\tLocalización: " + producto.getLocalizacion()
							+ "\n\t\tPendiente: " + producto.getPendiente() + "]\n";
				}
			} else {
				output += "\tSin productos...\n";
			}
		}

		if (mostrarPedidos) {
			output += "- Pedidos\n";
			if (pedidos.size() > 0) {
				for (Pedidos pedido : pedidos) {
					if (productos.size() > 0) {
						for (Producto producto : productos) {
							if (direccionesEntrega.size() > 0) {
								for (DireccionEntrega direccionEntrega : direccionesEntrega) {
									output += "\t\t[- Producto"
									+ "\n\t\t\tCodigo: " + producto.getCodigo()
									+ "\n\t\t\tNombre: " + producto.getNombre()
									+ "\n\t\t\tDescripción: " + producto.getDescripcion()
									+ "\n\t\t\tStock: " + producto.getStock() 
									+ "\n\t\t\ttLocalización: " + producto.getLocalizacion()
									+ "\n\t\t\tPendiente: " + producto.getPendiente()
									+ "\n\t\tCantidad: " + pedido.getCantidad()
									+ "\n\t\t- Dirección\n"
									+ "\n\t\t\tCalle:" + direccionEntrega.getCalle()
									+ "\n\t\t\tNúmero:" + direccionEntrega.getNumero()
									+ "\n\t\t\tCódigo Postal:" + direccionEntrega.getCodigoPostal()
									+ "\n\t\t\tPoblación:" + direccionEntrega.getPoblacion()
									+ "\n\t\t\tPaís:" + direccionEntrega.getPais()
									+ "\n\t\tDestinatario: " + pedido.getDestinatario()
									+ "\n\t\tFecha de entrega estimada: " + pedido.getFechaDeEntregaEstimada() + "]\n";
								}
							}
							else {
								output += "\tSin direccion de entrega...\n";
							}
						}
					}
					else {
						output += "\tSin productos...\n";
					}
				}
			} else {
				output += "\tSin pedidos...\n";
			}
		}
		System.out.println(output);
	}

	
	public void printDirecciones(String encabezado) {
		
		String output = "=================[ " + encabezado + " ]=================\n";
		
		if (direcciones.size() > 0) {
			for (Direccion direccion : direcciones) {
				output += "\t[- Dirección"
						+ "\n\t\t\tCalle:" + direccion.getCalle()
						+ "\n\t\t\tNúmero:" + direccion.getNumero()
						+ "\n\t\t\tCódigo Postal:" + direccion.getCodigoPostal()
						+ "\n\t\t\tPoblación:" + direccion.getPoblacion()
						+ "\n\t\t\tPaís:" + direccion.getPais() + "]\n";
			}
		}
		else {
			output += "\tSin direcciones...\n";
		}
		
		System.out.println(output);
	}

	public void addClientes(Clientes cliente) {
		this.clientes.add(cliente);
	}

	public void removeClientes(int index) {
		this.clientes.remove(index);
	}

	public void addProducto(Producto producto) {
		this.productos.add(producto);
	}

	public void removeProducto(int index) {
		this.productos.remove(index);
	}

	public void addPedidos(Pedidos pedido) {
		this.pedidos.add(pedido);
	}

	public void removePedidos(int index) {
		this.pedidos.remove(index);
	}

	public String toJSON() {		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		return gson.toJson(this);
	}

	public String getAlmacenID() {
		return almacenID;
	}

	public void setAlmacenID(String almacenID) {
		this.almacenID = almacenID;
	}

	public ArrayList<Clientes> getClientes() {
		return clientes;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public ArrayList<Pedidos> getPedidos() {
		return pedidos;
	}
	
	public ArrayList<Direccion> getDirecciones() {
		return direcciones;
	}

	public ArrayList<DireccionEntrega> getDireccionesEntrega() {
		return direccionesEntrega;
	}

}
