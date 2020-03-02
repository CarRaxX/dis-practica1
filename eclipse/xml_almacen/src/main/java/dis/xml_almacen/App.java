package disg4.xmlcv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import disg4.xmlcv.cv.Almacen;
import disg4.xmlcv.cv.Almacen.Destino;
import disg4.xmlcv.cv.Almacen.Disponibilidad;
import disg4.xmlcv.cv.Producto;
import disg4.xmlcv.cv.Clientes;
import disg4.xmlcv.cv.Clientes.Nivel;
import disg4.xmlcv.cv.Pedidos;

public class App {

	private static String filePath;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("\n\n  MM'\"\"\"\"'YMM M\"\"MMMMM\"\"M oo        v2.0 ");
		System.out.println("  M' .mmm. `M M  MMMMM  M                        ");
		System.out.println("  M  MMMMMooM M  MMMMP  M dP .d8888b. dP  dP  dP ");
		System.out.println("  M  MMMMMMMM M  MMMM' .M 88 88ooood8 88  88  88 ");
		System.out.println("  M. `MMM' .M M  MMP' .MM 88 88.  ... 88.88b.88' ");
		System.out.println("  MM.     .dM M     .dMMM dP `88888P' 8888P Y8P  ");
		System.out.println("  MMMMMMMMMMM MMMMMMMMMMM                        \n\n");

		System.out.print("Ruta del archivo xml: ");
		filePath = input.next();
		Almacen cv = new Almacen(filePath);
		
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
				System.out.println(cv.toJSON());
			}

			// Comando para guardar archivo
			if (command.equalsIgnoreCase("save")) {
				if (arguments.size() > 0) {
					cv.saveFile(arguments.get(0));
					System.out.println("Archivo guardado en " + arguments.get(0));
				} else {
					cv.saveFile(filePath);
					System.out.println("Archivo guardado en " + filePath);
				}
			}

			// Comando para mostrar parte del CV
			if (command.equalsIgnoreCase("display")) {
				if (arguments.size() > 0) {
					// Mostrar todo el CV
					if (arguments.get(0).equalsIgnoreCase("all")) {
						cv.printCV("CV", true, true, true, true);
					}
					// Mostrar PERSONAL
					if (arguments.get(0).equalsIgnoreCase("datos")) {
						if (arguments.size() == 1) {
							cv.printCV("CV", true, false, false, false);
						} else {
							System.out.println("display <all|datos|estudios|experiencia|idiomas>");
						}
					}
					// Mostrar ESTUDIOS
					if (arguments.get(0).equalsIgnoreCase("estudios")) {
						if (arguments.size() == 1) {
							cv.printCV("CV", false, true, false, false);
						} else {
							System.out.println("display <all|datos|estudios|experiencia|idiomas>");
						}
					}
					// Mostrar EXPERIENCIA
					if (arguments.get(0).equalsIgnoreCase("experiencia")) {
						if (arguments.size() == 1) {
							cv.printCV("CV", false, false, true, false);
						} else {
							System.out.println("display <all|datos|estudios|experiencia|idiomas>");
						}
					}
					// Mostrar IDIOMAS
					if (arguments.get(0).equalsIgnoreCase("idiomas")) {
						if (arguments.size() == 1) {
							cv.printCV("CV", false, false, false, true);
						} else {
							System.out.println("display <all|datos|estudios|experiencia|idiomas>");
						}
					}
				} else {
					System.out.println("display <all|datos|estudios|experiencia|idiomas>");
				}
			}

			// Comando para añadir
			if (command.equalsIgnoreCase("add")) {
				if (arguments.size() > 0) {
					// Añadiendo idioma
					if (arguments.get(0).equalsIgnoreCase("idioma")) {
						if (arguments.size() == 3) {
							if("BASICO|MEDIO|FLUIDO|NATIVO".contains(arguments.get(2))) {
								cv.addIdioma(new Clientes(arguments.get(1), Nivel.valueOf(arguments.get(2))));
								System.out.println("Añadido correctamente!");
							} else {
								System.out.println("Nivel de idioma no válido!");
							}
						} else {
							System.out.println("add idioma <nombre> <BASICO|MEDIO|FLUIDO|NATIVO>");
						}
					}
					// Añadiendo experiencia
					if (arguments.get(0).equalsIgnoreCase("experiencia")) {
						if (arguments.size() == 6) {
							cv.addExperiencia(new Producto(arguments.get(1), arguments.get(2), null, null, arguments.get(5)));
							System.out.println("Añadido correctamente!");
						} else {
							System.out.println("add experiencia <empresa> <puesto> <FechaInicio> <FechaFin> <detalles>");
						}
					}
					// Añadiendo titulo
					if (arguments.get(0).equalsIgnoreCase("titulo")) {
						if (arguments.size() == 6) {
							cv.addEstudios(new Pedidos(arguments.get(1), arguments.get(2), null, null, arguments.get(5)));
							System.out.println("Añadido correctamente!");
						} else {
							System.out.println("add titulo <nombre> <entidad> <FechaInicio> <FechaFin> <detalles>");
						}
					}
				} else {
					System.out.println("add <titulo|experiencia|idioma> CONTENIDO");
				}
			}

			// Comando para modificar
			if (command.equalsIgnoreCase("mod")) {
				if (arguments.size() > 0) {
					// Modificar nombre
					if (arguments.get(0).equalsIgnoreCase("nombre")) {
						if (arguments.size() == 2) {
							cv.setNombreCompleto(arguments.get(1));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <nombre> VALOR");
						}
					}
					// Modificar telefono
					if (arguments.get(0).equalsIgnoreCase("telefono")) {
						if (arguments.size() == 2) {
							cv.setTelefono(Integer.parseInt(arguments.get(1)));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <telefono> VALOR");
						}
					}
					// Modificar email
					if (arguments.get(0).equalsIgnoreCase("email")) {
						if (arguments.size() == 2) {
							cv.setEmail(arguments.get(1));
							System.out.println("Modificado correctamente!");
						} else {
							System.out.println("mod <email> VALOR");
						}
					}
					// Modificar disponibilidad
					if (arguments.get(0).equalsIgnoreCase("disponibilidad")) {
						if (arguments.size() == 2) {
							if("BAJA|MEDIA|ALTA".contains(arguments.get(1))) {
								cv.setDisponibilidad(Disponibilidad.valueOf(arguments.get(1)));
								System.out.println("Modificado correctamente!");
							} else {
								System.out.println("Disponibilidad no válida!");
							}
						} else {
							System.out.println("mod <disponibilidad> <BAJA|MEDIA|ALTA>");
						}
					}
					// Modificar disponibilidad
					if (arguments.get(0).equalsIgnoreCase("destino")) {
						if (arguments.size() == 2) {
							
							if("NACIONAL|EUROPA|ASIA|AMERICA_NORTE|AMERICA_SUR|OTROS".contains(arguments.get(1))) {
								cv.setDestinoMovilidad(Destino.valueOf(arguments.get(1)));
								System.out.println("Modificado correctamente!");
							} else {
								System.out.println("Destino de movilidad no válido!");
							}
						} else {
							System.out.println("mod <destino> <NACIONAL|EUROPA|ASIA|AMERICA_NORTE|AMERICA_SUR|OTROS>");
						}
					}
					// Modificar disponibilidad
					if (arguments.get(0).equalsIgnoreCase("tiempo")) {
						if (arguments.size() == 2) {
							cv.setTiempoMovilidad(Integer.parseInt(arguments.get(1)));
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
							cv.removeIdioma(Integer.parseInt(arguments.get(1)));
						} else {
							System.out.println("IDs de Idiomas:");
							for (int i = 0; i < cv.getIdiomas().size(); i++) {
								System.out.println(i + " - " + cv.getIdiomas().get(i).getNombre());
								System.out.println("Eliminado correctamente!");
							}
							System.out.println("remove idioma <id>");
						}
					}
					// Eliminar experiencia
					if (arguments.get(0).equalsIgnoreCase("experiencia")) {
						if (arguments.size() == 2) {
							cv.removeExperiencia(Integer.parseInt(arguments.get(1)));
						} else {
							System.out.println("IDs de Experiencias:");
							for (int i = 0; i < cv.getExperiencia().size(); i++) {
								System.out.println(i + " - " + cv.getExperiencia().get(i).getEmpresa());
								System.out.println("Eliminado correctamente!");
							}
							System.out.println("remove experiencia <id>");
						}
					}
					// Eliminar titulo
					if (arguments.get(0).equalsIgnoreCase("titulo")) {
						if (arguments.size() == 2) {
							cv.removeEstudios(Integer.parseInt(arguments.get(1)));
							System.out.println("Eliminado correctamente!");
						} else {
							System.out.println("IDs de Titulos:");
							for (int i = 0; i < cv.getEstudios().size(); i++) {
								System.out.println(i + " - " + cv.getEstudios().get(i).getNombre());
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
