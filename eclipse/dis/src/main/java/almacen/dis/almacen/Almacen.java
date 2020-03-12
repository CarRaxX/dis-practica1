package almacen.dis.almacen;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
				node21.addContent(new Element("Apellidos").setText(cliente.getNombre()));
				node21.addContent(new Element("Email").setText(cliente.getEntidad()));
				node21.addContent(new Element("Telf. Contacto").setText(dateToString(cliente.getFechaInicio())));
				node21.addContent(new Element("Direccion").setText(dateToString(cliente.getFechaFin())));
				if (titulo.getDetalles() != null && !titulo.getDetalles().isEmpty())
					node21.addContent(new Element("Detalles").setText(titulo.getDetalles()));

				node2.addContent(node21);
			}
			doc.getRootElement().addContent(node2);

			Element node3 = new Element("ExperienciaProfesional");
			for (Producto experiencia : this.productos) {
				Element node31 = new Element("Experiencia");
				node31.addContent(new Element("Nombre").setText(experiencia.getPuesto()));
				node31.addContent(new Element("Entidad").setText(experiencia.getEmpresa()));
				node31.addContent(new Element("FechaInicio").setText(dateToString(experiencia.getFechaInicio())));
				node31.addContent(new Element("FechaFin").setText(dateToString(experiencia.getFechaFin())));
				if (experiencia.getDetalles() != null && !experiencia.getDetalles().isEmpty())
					node31.addContent(new Element("Detalles").setText(experiencia.getDetalles()));

				node3.addContent(node31);
			}
			doc.getRootElement().addContent(node3);

			Element node4 = new Element("Idiomas");
			for (Pedidos idioma : this.pedidos) {
				Element node41 = new Element("Idioma");
				node41.addContent(new Element("Nombre").setText(idioma.getNombre()));
				node41.addContent(new Element("Nivel").setText(idioma.getNivel().toString()));

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
		// Damos por válido la ruta absoluta al archivo a cargar
		File xmlFile = new File(path);

		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();

			Element node1 = rootNode.getChild("DatosPersonales");

			this.nombreCompleto = node1.getChildText("NombreCompleto");
			this.email = node1.getChildText("Email");
			this.fechaNacimiento = getCalendar(node1.getChildText("FechaNacimiento"));
			this.dni = node1.getChildText("DNI");
			this.telefono = Integer.parseInt(node1.getChildText("Telefono"));
			this.disponibilidad = Disponibilidad.valueOf(node1.getChildText("Disponibilidad"));

			for (Element node11 : node1.getChildren("MovilidadGeografica")) {
				this.destino = Destino.valueOf(node11.getChildText("Destino"));
				this.tiempo = Integer.parseInt(node11.getChildText("Tiempo"));
			}

			Element node2 = rootNode.getChild("Estudios");
			for (Element node21 : node2.getChildren("Titulo")) {
				String detalles = node21.getChildText("Detalles");
				if (detalles == null || detalles.isEmpty()) {
					clientes.add(new Clientes(node21.getChildText("Nombre"), node21.getChildText("Entidad"),
							getCalendar(node21.getChildText("FechaInicio")),
							getCalendar(node21.getChildText("FechaFin"))));
				} else {
					clientes.add(new Clientes(node21.getChildText("Nombre"), node21.getChildText("Entidad"),
							getCalendar(node21.getChildText("FechaInicio")),
							getCalendar(node21.getChildText("FechaFin")), detalles));
				}
			}

			Element node3 = rootNode.getChild("ExperienciaProfesional");
			for (Element node31 : node3.getChildren("Experiencia")) {
				String detalles = node31.getChildText("Detalles");
				if (detalles == null || detalles.isEmpty()) {
					productos.add(new Producto(node31.getChildText("Puesto"), node31.getChildText("Empresa"),
							getCalendar(node31.getChildText("FechaInicio")),
							getCalendar(node31.getChildText("FechaFin"))));
				} else {
					productos.add(new Producto(node31.getChildText("Puesto"), node31.getChildText("Empresa"),
							getCalendar(node31.getChildText("FechaInicio")),
							getCalendar(node31.getChildText("FechaFin")), detalles));
				}
			}

			Element node4 = rootNode.getChild("Idiomas");
			for (Element node41 : node4.getChildren("Idioma")) {
				pedidos.add(new Pedidos(node41.getChildText("Nombre"), Nivel.valueOf(node41.getChildText("Nivel"))));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void printCV(String encabezado, boolean mostrarDatosPersonales, boolean mostrarEstudios,
			boolean mostrarExperiencia, boolean mostrarIdiomas) {
		String output = "=================[ " + encabezado + " ]=================\n";

		if (mostrarDatosPersonales) {
			output += "- Datos Personales\n";
			output += "\tNombre Completo: " + nombreCompleto + "\n";
			output += "\tEmail: " + email + "\n";
			output += "\tFecha de Nacimiento: " + dateToString(fechaNacimiento) + "\n";
			output += "\tDNI: " + dni + "\n";
			output += "\tTelefono: " + telefono + "\n";
			output += "\tDisponibilidad: " + disponibilidad + "\n";

			output += "\t- Movilidad\n";
			output += "\t\tDestino: " + destino.toString() + "\n";
			output += "\t\tTiempo: " + tiempo + " meses\n";
		}

		if (mostrarEstudios) {
			output += "- Estudios\n";
			if (clientes.size() > 0) {
				for (Clientes titulo : clientes) {
					output += "\t[" + dateToString(titulo.getFechaInicio()) + " - " + dateToString(titulo.getFechaFin())
							+ "] " + titulo.getNombre() + " en " + titulo.getEntidad() + "\n\tInformación Adicional: "
							+ titulo.getDetalles() + "\n";
				}
			} else {
				output += "\tSin estudios...\n";
			}
		}

		if (mostrarExperiencia) {
			output += "- Experiencia\n";
			if (productos.size() > 0) {
				for (Producto experiencia : this.productos) {
					output += "\t[" + dateToString(experiencia.getFechaInicio()) + " - "
							+ dateToString(experiencia.getFechaFin()) + "] " + experiencia.getPuesto() + " en "
							+ experiencia.getEmpresa() + "\n\tInformación Adicional: " + experiencia.getDetalles()
							+ "\n";
				}
			} else {
				output += "\tSin experiencia...\n";
			}
		}

		if (mostrarIdiomas) {
			output += "- Idiomas\n";
			if (pedidos.size() > 0) {
				for (Pedidos idioma : pedidos) {
					output += "\t" + idioma.getNombre() + " nivel " + idioma.getNivel() + "\n";
				}
			} else {
				output += "\tSin idiomas...\n";
			}
		}
		System.out.println(output);
	}

	public String dateToString(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/"
				+ calendar.get(Calendar.YEAR);
	}

	public GregorianCalendar getCalendar(String string) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date date = df.parse(string);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}

	public void addEstudios(Clientes titulo) {
		this.clientes.add(titulo);
	}

	public void removeEstudios(int index) {
		this.clientes.remove(index);
	}

	public void addExperiencia(Producto experiencia) {
		this.productos.add(experiencia);
	}

	public void removeExperiencia(int index) {
		this.productos.remove(index);
	}

	public void addIdioma(Pedidos idioma) {
		this.pedidos.add(idioma);
	}

	public void removeIdioma(int index) {
		this.pedidos.remove(index);
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public GregorianCalendar getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(GregorianCalendar fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public Destino getDestinoMovilidad() {
		return destino;
	}

	public void setDestinoMovilidad(Destino destino) {
		this.destino = destino;
	}

	public int getTiempoMovilidad() {
		return tiempo;
	}

	public void setTiempoMovilidad(int tiempo) {
		this.tiempo = tiempo;
	}

	public Disponibilidad getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Disponibilidad disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String toJSON() {		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		return gson.toJson(this);
	}

	public ArrayList<Clientes> getEstudios() {
		return clientes;
	}

	public ArrayList<Producto> getExperiencia() {
		return productos;
	}

	public ArrayList<Pedidos> getIdiomas() {
		return pedidos;
	}
	
	

}
