package almacen.dis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import almacen.dis.almacen.Almacen;
import almacen.dis.almacen.Producto;
import almacen.dis.almacen.Producto.Localizacion;
import almacen.dis.almacen.Pedidos;
import almacen.dis.almacen.Clientes;

public class App {

	private static String filePath;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Sistema de Gestión de Almacenes");

		System.out.print("Ruta del archivo xml: ");
		filePath = input.next();
		Almacen almacen = new Almacen(filePath);
		
		printHelp();

		boolean run = true;
		while (run) {
			String inputStr = input.nextLine();

			// Convertir la entrada a comando + argumentos (soporta strings espaciados usando dobles comillas "TEXTO ESPACIADO")
			String command = "";
			List<String> arguments = new ArrayList<String>();
			Matcher match = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputStr);
			
			while(match.find()) {
				String str = match.group(1);
				str = str.replace("\"", "");
				
				if(command.isEmpty()) {
					command = str;
				} else {
					arguments.add(str);
				}
			}

			// Comando de ayuda
			if (command.equalsIgnoreCase("help")) {
				printHelp();
			}

			// Comando para pasar a JSON
			if (command.equalsIgnoreCase("json")) {
				System.out.println(almacen.toJSON());
			}

			// Comando para guardar archivo
			if (command.equalsIgnoreCase("save")) {
				if (arguments.size() > 0) {
					almacen.saveFile(arguments.get(0));
					System.out.println("Archivo guardado en " + arguments.get(0));
				} else {
					almacen.saveFile(filePath);
					System.out.println("Archivo guardado en " + filePath);
				}
			}

			// Comando para mostrar parte del Almacén
			if (command.equalsIgnoreCase("display")) {
				if (arguments.size() > 0) {
					// Mostrar todo el Almacén
					if (arguments.get(0).equalsIgnoreCase("all")) {
						almacen.printAlmacen("ALMACEN", true, true, true, true);
					}
					// Mostrar ALMACÉN
					if (arguments.get(0).equalsIgnoreCase("clientes")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", true, false, false, false);
						} else {
							System.out.println("display <all|almacen|clientes|productos|pedidos>");
						}
					}
					// Mostrar CLIENTES
					if (arguments.get(0).equalsIgnoreCase("estudios")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", false, true, false, false);
						} else {
							System.out.println("display <all|almacen|clientes|productos|pedidos>");
						}
					}
					// Mostrar PRODUCTOS
					if (arguments.get(0).equalsIgnoreCase("experiencia")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", false, false, true, false);
						} else {
							System.out.println("display <all|almacen|clientes|productos|pedidos>");
						}
					}
					// Mostrar PEDIDOS
					if (arguments.get(0).equalsIgnoreCase("idiomas")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", false, false, false, true);
						} else {
							System.out.println("display <all|almacen|clientes|productos|pedidos>");
						}
					}
				} else {
					System.out.println("display <all|almacen|clientes|productos|pedidos>");
				}
			}

			// Comando para Añadir
			if (command.equalsIgnoreCase("add")) {
				if (arguments.size() > 0) {
					// Añadiendo pedidos
					if (arguments.get(0).equalsIgnoreCase("pedido")) {
						if (arguments.size() == 3) {
							if("BASICO|MEDIO|FLUIDO|NATIVO".contains(arguments.get(2))) {
								almacen.addPedidos(new Pedidos(arguments.get(1), Nivel.valueOf(arguments.get(2))));
								System.out.println("AÃ±adido correctamente!");
							} else {
								System.out.println("Nivel de idioma no válido!");
							}
						} else {
							System.out.println("add idioma <nombre> <BASICO|MEDIO|FLUIDO|NATIVO>");
						}
					}
					// Añadiendo producto
					if (arguments.get(0).equalsIgnoreCase("producto")) {
						if (arguments.size() == 7) {
							if("PASILLO|ESTANTERIA|ESTANTE".contains(arguments.get(5))) {
								almacen.addProducto(new Producto(arguments.get(1), arguments.get(2), arguments.get(3), Integer.parseInt(arguments.get(4)), Localizacion.valueOf(arguments.get(5)), Boolean.parseBoolean(arguments.get(6))));
								System.out.println("Producto añadido correctamente!");
							}							
							else {
								System.out.println("Localización del producto no válida!");
							}		
						} else {
							System.out.println("add producto <codigo (str)> <nombre (str)> <descripcion (str)> <stock (int)> <PASILLO|ESTANTERIA|ESTANTE> <true|false>");
						}
					}
					// Añadiendo cliente
					if (arguments.get(0).equalsIgnoreCase("cliente")) {
						if(almacen.getDirecciones().size() > 0) {
							if (arguments.size() == 6) {
								almacen.addClientes(new Clientes(arguments.get(1), arguments.get(2), null, null, null));
								System.out.println("Cliente añadido correctamente!");
							} else {
								System.out.println("add cliente <nombre (str)> <apellido 1 (str)> <apellido 2 (str)> <email (str)> <telf. contacto (str)> <DIRECCION>");
							}
						}
						else {
							System.out.println("add direccion <calle (str)> <numero (int)> <codigo postal (int)> <poblacion (str)> <pais (str)>");
						}
					}
				} else {
					System.out.println("add <producto|cliente|pedido> CONTENIDO");
				}
			}

			// Comando para modificar
			if (command.equalsIgnoreCase("mod")) {
				if (arguments.size() > 0) {
					// Modificar nombre
					if (arguments.get(0).equalsIgnoreCase("nombre")) {
						if (arguments.size() == 2) {
							almacen.setNombreCompleto(arguments.get(1));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <nombre> VALOR");
						}
					}
					// Modificar telefono
					if (arguments.get(0).equalsIgnoreCase("telefono")) {
						if (arguments.size() == 2) {
							almacen.setTelefono(Integer.parseInt(arguments.get(1)));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <telefono> VALOR");
						}
					}
					// Modificar email
					if (arguments.get(0).equalsIgnoreCase("email")) {
						if (arguments.size() == 2) {
							almacen.setEmail(arguments.get(1));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <email> VALOR");
						}
					}
					// Modificar disponibilidad
					if (arguments.get(0).equalsIgnoreCase("disponibilidad")) {
						if (arguments.size() == 2) {
							if("BAJA|MEDIA|ALTA".contains(arguments.get(1))) {
								almacen.setDisponibilidad(Disponibilidad.valueOf(arguments.get(1)));
								System.out.println("Modificado correctamente!");
							} else {
								System.out.println("Disponibilidad no vÃ¡lida!");
							}
						} else {
							System.out.println("mod <disponibilidad> <BAJA|MEDIA|ALTA>");
						}
					}
					// Modificar disponibilidad
					if (arguments.get(0).equalsIgnoreCase("destino")) {
						if (arguments.size() == 2) {
							
							if("NACIONAL|EUROPA|ASIA|AMERICA_NORTE|AMERICA_SUR|OTROS".contains(arguments.get(1))) {
								almacen.setDestinoMovilidad(Destino.valueOf(arguments.get(1)));
								System.out.println("Modificado correctamente!");
							} else {
								System.out.println("Destino de movilidad no vÃ¡lido!");
							}
						} else {
							System.out.println("mod <destino> <NACIONAL|EUROPA|ASIA|AMERICA_NORTE|AMERICA_SUR|OTROS>");
						}
					}
					// Modificar disponibilidad
					if (arguments.get(0).equalsIgnoreCase("tiempo")) {
						if (arguments.size() == 2) {
							almacen.setTiempoMovilidad(Integer.parseInt(arguments.get(1)));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <tiempo> <meses>");
						}
					}
				} else {
					System.out.println("mod <nombre|telefono|email|disponibilidad> VALOR");
				}
			}

			// Comando para eliminar
			if (command.equalsIgnoreCase("remove")) {
				if (arguments.size() > 0) {
					// Eliminar idioma
					if (arguments.get(0).equalsIgnoreCase("idioma")) {
						if (arguments.size() == 2) {
							almacen.removeIdioma(Integer.parseInt(arguments.get(1)));
						} else {
							System.out.println("IDs de Idiomas:");
							for (int i = 0; i < almacen.getIdiomas().size(); i++) {
								System.out.println(i + " - " + almacen.getIdiomas().get(i).getNombre());
								System.out.println("Eliminado correctamente!");
							}
							System.out.println("remove idioma <id>");
						}
					}
					// Eliminar experiencia
					if (arguments.get(0).equalsIgnoreCase("experiencia")) {
						if (arguments.size() == 2) {
							almacen.removeExperiencia(Integer.parseInt(arguments.get(1)));
						} else {
							System.out.println("IDs de Experiencias:");
							for (int i = 0; i < almacen.getExperiencia().size(); i++) {
								System.out.println(i + " - " + almacen.getExperiencia().get(i).getEmpresa());
								System.out.println("Eliminado correctamente!");
							}
							System.out.println("remove experiencia <id>");
						}
					}
					// Eliminar titulo
					if (arguments.get(0).equalsIgnoreCase("titulo")) {
						if (arguments.size() == 2) {
							almacen.removeEstudios(Integer.parseInt(arguments.get(1)));
							System.out.println("Eliminado correctamente!");
						} else {
							System.out.println("IDs de Titulos:");
							for (int i = 0; i < almacen.getEstudios().size(); i++) {
								System.out.println(i + " - " + almacen.getEstudios().get(i).getNombre());
							}
							System.out.println("remove titulo <id>");
						}
					}
				} else {
					System.out.println("remove <titulo|experiencia|idioma> ID");
				}
			}

			// Comando para salir de la consola
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("Consola cerrada correctamente.");
				run = false;
			}
			System.out.print("cview > ");
		}
		input.close();
	}

	public static void printHelp() {
		String output = "=========| AYUDA |=========\n";
		output += "help\n";
		output += "json\n";
		output += "display <all|datos|estudios|experiencia|idiomas>\n";
		output += "save [ARCHIVO: en blanco utiliza el archivo de carga]\n";
		output += "mod <nombre|telefono|email|disponibilidad|destino|tiempo> VALOR\n";
		output += "add <titulo|experiencia|idioma> CONTENIDO\n";
		output += "remove <titulo|experiencia|idioma> ID\n";
		output += "exit\n";
		System.out.println(output);
	}

}
