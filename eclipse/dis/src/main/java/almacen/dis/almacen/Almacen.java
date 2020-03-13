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
				node21.addContent(new Element("Telf.Contacto").setText(cliente.getTelfContacto()));

				for (Direccion direccion : cliente.getDireccion()) {
					Element node211 = new Element("Direccion");
					node211.addContent(new Element("Calle").setText(direccion.getCalle()));
					node211.addContent(new Element("N�mero").setText(Integer.toString(direccion.getNumero())));
					node211.addContent(new Element("C�digoPostal").setText(Integer.toString(direccion.getCodigoPostal())));
					node211.addContent(new Element("Poblaci�n").setText(direccion.getPoblacion()));
					node211.addContent(new Element("Pa�s").setText(direccion.getPais()));
					
					node21.addContent(node211);
				}

				node2.addContent(node21);
			}
			doc.getRootElement().addContent(node2);

			Element node3 = new Element("Productos");
			for (Producto producto : productos) {
				Element node31 = new Element("Producto");
				node31.addContent(new Element("C�digo").setText(producto.getCodigo()));
				node31.addContent(new Element("Nombre").setText(producto.getNombre()));
				node31.addContent(new Element("Descripci�n").setText(producto.getDescripcion()));
				node31.addContent(new Element("Stock").setText(Integer.toString(producto.getStock())));
				node31.addContent(new Element("Localizaci�n").setText(producto.getLocalizacion().toString()));
				node31.addContent(new Element("Pendientes").setText(Boolean.toString(producto.getPendiente())));
				
				node3.addContent(node31);
			}
			doc.getRootElement().addContent(node3);

			Element node4 = new Element("Pedidos");
			for (Pedidos pedido : pedidos) {
				Element node41 = new Element("Pedido");
				node41.addContent(new Element("IDPedido").setText(pedido.getIdPedido()));
				
				for (Producto producto : productos) {
					Element node411 = new Element("Producto");
					node411.addContent(new Element("C�digo").setText(producto.getCodigo()));
					node411.addContent(new Element("Nombre").setText(producto.getNombre()));
					node411.addContent(new Element("Descripci�n").setText(producto.getDescripcion()));
					node411.addContent(new Element("Stock").setText(Integer.toString(producto.getStock())));
					node411.addContent(new Element("Pendientes").setText(Boolean.toString(producto.getPendiente())));
					
					node41.addContent(node411);
				}
				
				node41.addContent(new Element("Cantidad").setText(Integer.toString(pedido.getCantidad())));
				
				for (DireccionEntrega direccionEntrega : pedido.getDireccionEntrega()) {
					Element node421 = new Element("DireccionDeEntrega");
					node421.addContent(new Element("Calle").setText(direccionEntrega.getCalle()));
					node421.addContent(new Element("N�mero").setText(Integer.toString(direccionEntrega.getNumero())));
					node421.addContent(new Element("C�digoPostal").setText(Integer.toString(direccionEntrega.getCodigoPostal())));
					node421.addContent(new Element("Poblaci�n").setText(direccionEntrega.getPoblacion()));
					node421.addContent(new Element("Pa�s").setText(direccionEntrega.getPais()));
					
					node41.addContent(node421);
				}
				
				node41.addContent(new Element("Destinatario").setText(pedido.getDestinatario()));
				node41.addContent(new Element("FechaDeEntregaEstimada").setText(pedido.getFechaDeEntregaEstimada()));
				
				
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
					for (int i = 0; i < clientes.size(); i++) {
						clientes.add(new Clientes(node21.getChildText("Nombre"), node21.getChildText("Apellidos"), node21.getChildText("Email"), node21.getChildText("Telf.Contacto"), null));
						clientes.get(i).getDireccion().add(new Direccion(node211.getChildText("Calle"), Integer.parseInt(node211.getChildText("Numero")), Integer.parseInt(node211.getChildText("C�digoPostal")), node211.getChildText("Poblaci�n"), node211.getChildText("Pa�s")));
					}	
				}
			}

			Element node3 = rootNode.getChild("Productos");
			for (Element node31 : node3.getChildren("Producto")) {
				productos.add(new Producto(node31.getChildText("C�digo"), node31.getChildText("Nombre"), node31.getChildText("Descripci�n"), Integer.parseInt(node31.getChildText("Stock")), Localizacion.valueOf(node31.getChildText("Localizaci�n")), Boolean.parseBoolean(node31.getChildText("Pendientes"))));
			}

			Element node4 = rootNode.getChild("Pedidos");
			for (Element node41 : node4.getChildren("Pedido")) {
				for (Element node411 : node4.getChildren("Producto")) {
					for (Element node421 : node4.getChildren("DireccionDeEntrega")) {
						for (int i = 0; i < pedidos.size(); i++) {
							pedidos.get(i).getProductos().add(new Producto(node411.getChildText("C�digo"), node411.getChildText("Nombre"), node411.getChildText("Descripci�n"), Integer.parseInt(node411.getChildText("Stock")), Localizacion.valueOf(node411.getChildText("Localizaci�n")), Boolean.parseBoolean(node411.getChildText("Pendientes"))));
							pedidos.get(i).getDireccionEntrega().add(new DireccionEntrega(node421.getChildText("Calle"), Integer.parseInt(node421.getChildText("Numero")), Integer.parseInt(node421.getChildText("C�digoPostal")), node421.getChildText("Poblaci�n"), node421.getChildText("Pa�s")));
							pedidos.add(new Pedidos(node41.getChildText("IDPedido"), null, Integer.parseInt(node41.getChildText("Cantidad")), null, node41.getChildText("Destinatario"), node41.getChildText("FechaDeEntregaEstimada")));
						}
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
			output += "- Datos del Almac�n\n";
			output += "\tAlmac�nID: " + almacenID + "\n";
		}

		if (mostrarClientes) {
			output += "\n- Clientes\n";
			if (clientes.size() > 0) {
				for (Clientes cliente : clientes) {
					output += "\t[Nombre y Apellidos: " + cliente.getNombre() + " " + cliente.getApellidos()
					+ "\n\tEmail: " + cliente.getEmail()
					+ "\n\tTelf. de contacto: " + cliente.getTelfContacto() 
					+ "\n\t- Direcci�n";
					
					for (int i = 0; i < cliente.getDireccion().size(); i++) {
						if(clientes.get(i).getDireccion().size() > 0) {
							output += "\n\t\tCalle: " + cliente.getDireccion().get(i).getCalle()
									+ "\n\t\tN�mero: " + cliente.getDireccion().get(i).getNumero()
									+ "\n\t\tC�digo Postal: " + cliente.getDireccion().get(i).getCodigoPostal()
									+ "\n\t\tPoblaci�n: " + cliente.getDireccion().get(i).getPoblacion()
									+ "\n\t\tPa�s: " + cliente.getDireccion().get(i).getPais() + "]\n";	
						}
						else {
							output += "\tSin Direcciones...\n";
						}
					}
				}
			} 
			else {
				output += "\tSin clientes...\n";
			}
		}

		if (mostrarProductos) {
			output += "\n- Productos\n";
			if (productos.size() > 0) {
				for (Producto producto : productos) {
					output += "\t[Codigo: " + producto.getCodigo()
							+ "\n\tNombre: " + producto.getNombre()
							+ "\n\tDescripci�n: " + producto.getDescripcion()
							+ "\n\tStock: " + producto.getStock() 
							+ "\n\tLocalizaci�n: " + producto.getLocalizacion()
							+ "\n\tPendiente: " + producto.getPendiente() + "]\n";
				}
			} else {
				output += "\tSin productos...\n";
			}
		}

		if (mostrarPedidos) {
			output += "\n- Pedidos\n";
			if (pedidos.size() > 0) {
				for (Pedidos pedido : pedidos) {
					output += "\n\t[Cantidad: " + pedido.getCantidad()
					+ "\n\tDestinatario: " + pedido.getDestinatario()
					+ "\n\tFecha de entrega estimada: " + pedido.getFechaDeEntregaEstimada();
					
					for (int i = 0; i < pedidos.size(); i++) {
						if (pedidos.get(i).getProductos().size() > 0) {
							for (int j = 0; j < pedidos.get(i).getProductos().size(); j++) {
								output += "\t- Producto "
										+ "\n\t\tCodigo: " + pedidos.get(i).getProductos().get(j).getCodigo()
										+ "\n\t\tNombre: " + pedidos.get(i).getProductos().get(j).getNombre()
										+ "\n\t\tDescripci�n: " + pedidos.get(i).getProductos().get(j).getDescripcion()
										+ "\n\t\tStock: " + pedidos.get(i).getProductos().get(j).getStock() 
										+ "\n\t\tLocalizaci�n: " + pedidos.get(i).getProductos().get(j).getLocalizacion()
										+ "\n\t\tPendiente: " + pedidos.get(i).getProductos().get(j).getPendiente();
							}
						}
						else {
							output += "\tSin productos...\n";
						}
						
						if (pedidos.get(i).getDireccionEntrega().size() > 0) {
							for (int j = 0; j < pedidos.get(i).getProductos().size(); j++) {
								output += "\n\t- Direcci�n "
										+ "\n\t\tCalle:" + pedidos.get(i).getDireccionEntrega().get(j).getCalle()
										+ "\n\t\tN�mero:" + pedidos.get(i).getDireccionEntrega().get(j).getNumero()
										+ "\n\t\tC�digo Postal:" + pedidos.get(i).getDireccionEntrega().get(j).getCodigoPostal()
										+ "\n\t\tPoblaci�n:" + pedidos.get(i).getDireccionEntrega().get(j).getPoblacion()
										+ "\n\t\tPa�s:" + pedidos.get(i).getDireccionEntrega().get(j).getPais() + "]\n";
							}
						}
						else{
							output += "\tSin Direcciones de entrega...\n";
						}
					}
				}
			} else {
				output += "\tSin pedidos...\n";
			}
		}
		System.out.println(output);
	}

	public void addClientes(Clientes cliente) {
		this.clientes.add(cliente);
	}
	
	public void addProducto(Producto producto) {
		this.productos.add(producto);
	}

	public void addPedidos(Pedidos pedido) {
		this.pedidos.add(pedido);
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

}
