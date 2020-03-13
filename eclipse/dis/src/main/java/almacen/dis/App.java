package almacen.dis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import almacen.dis.almacen.Almacen;
import almacen.dis.almacen.Producto;
import almacen.dis.almacen.Producto.Localizacion;
import almacen.dis.almacen.direccionEntrega.Direccion;
import almacen.dis.almacen.direccionEntrega.DireccionEntrega;
import almacen.dis.almacen.Pedidos;
import almacen.dis.almacen.Clientes;

public class App {

	private static String filePath;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Sistema de Gestión de Almacenes");
		printHelp();
		System.out.print("Rutay nombre del archivo xml: ");
		filePath = input.next();
		Almacen almacen = new Almacen(filePath);

		boolean run = true;
		while (run) {
			String inputStr = input.nextLine();

			// Convertir la entrada a comando + argumentos (soporta strings espaciados
			// usando dobles comillas "TEXTO ESPACIADO")
			String command = "";
			List<String> arguments = new ArrayList<String>();
			Matcher match = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputStr);

			while (match.find()) {
				String str = match.group(1);
				str = str.replace("\"", "");

				if (command.isEmpty()) {
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

			// Comando para cargar archivo
			if (command.equalsIgnoreCase("load")) {
				if (arguments.size() > 0) {
					almacen.loadFile(arguments.get(0));
					System.out.println("Archivo cargado desde " + arguments.get(0));
				} else {
					almacen.loadFile(filePath);
					System.out.println("Archivo cargado desde " + filePath);
				}
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
					if (arguments.get(0).equalsIgnoreCase("almacen")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", true, false, false, false);
						} else {
							System.out.println("Introduza:\n display <all|almacen|clientes|productos|pedidos>");
						}
					}
					// Mostrar CLIENTES
					if (arguments.get(0).equalsIgnoreCase("clientes")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", false, true, false, false);
						} else {
							System.out.println("Introduza:\n display <all|almacen|clientes|productos|pedidos>");
						}
					}
					// Mostrar PRODUCTOS
					if (arguments.get(0).equalsIgnoreCase("productos")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", false, false, true, false);
						} else {
							System.out.println("Introduza:\n display <all|almacen|clientes|productos|pedidos>");
						}
					}
					// Mostrar PEDIDOS
					if (arguments.get(0).equalsIgnoreCase("pedidos")) {
						if (arguments.size() == 1) {
							almacen.printAlmacen("ALMACEN", false, false, false, true);
						} else {
							System.out.println("Introduza:\n display <all|almacen|clientes|productos|pedidos>");
						}
					}
				} else {
					System.out.println("Introduza:\n display <all|almacen|clientes|productos|pedidos>");
				}
			}

			// Comando para Añadir
			if (command.equalsIgnoreCase("add")) {
				if (arguments.size() > 0) {
					// Añadiendo pedidos con un productos a una dirección de entrega.
					if (arguments.get(0).equalsIgnoreCase("pedido")) {
						if (almacen.getProductos().size() > 0) {
							if (arguments.size() == 11) {
								almacen.addPedidos(new Pedidos(arguments.get(1), null, Integer.parseInt(arguments.get(3)), null, arguments.get(9), arguments.get(10)));
								for (int i = 0; i < almacen.getPedidos().size(); i++) {
									// Añadimos la direccion de entrega
									almacen.getPedidos().get(i).getDireccionEntrega().add(new DireccionEntrega(arguments.get(4),Integer.parseInt(arguments.get(5)),Integer.parseInt(arguments.get(6)), arguments.get(7),arguments.get(8)));
									for (int j = 0; j < almacen.getProductos().size(); j++) {
										if (arguments.get(2).equals(almacen.getProductos().get(j).getCodigo())) {
											// Añadimos el producto seleccionado
											almacen.getPedidos().get(i).getProductos().add(almacen.getProductos().get(j));	
										} else {
											System.out.println("Error. código del producto no encontrado!");
										}
									}
								}
								System.out.println("Pedido añadido correctamente!");
							} else {
								System.out.println(
										"Introduza:\n add pedido <idPedido> <codigo del producto (str)> <cantidad> <calle (str)> <numero (int)> <codigo postal (int)> <poblacion (str)> <pais (str)> <destinatario> <fecha de entrega estimada>");
							}
						} else {
							System.out.println(
									"No hay productos!\n Introduza:\n add producto <codigo (str)> <nombre (str)> <descripcion (str)> <stock (int)> <PASILLO|ESTANTERIA|ESTANTE> <true|false>");
						}
					}

					// Añadiendo más productos a un pedido
					if (arguments.get(0).equalsIgnoreCase("productos")) {
						if (arguments.size() == 3) {
							if (almacen.getPedidos().size() > 0) {
								for (int i = 0; i < almacen.getProductos().size(); i++) {
									if (arguments.get(1).equals(almacen.getProductos().get(i).getCodigo())) {
										for (int j = 0; i < almacen.getPedidos().size(); i++) {
											if (arguments.get(2).equals(almacen.getPedidos().get(j).getIdPedido())) {
												almacen.getPedidos().get(j).getProductos()
														.add(almacen.getProductos().get(i));
												System.out.println("Producto con código ["
														+ almacen.getProductos().get(i).getCodigo()
														+ "] añadido al pedido con id ["
														+ almacen.getPedidos().get(j).getIdPedido() + "]!");
											} else {
												System.out.println("Id del pedido no encontrado");
											}
										}
									} else {
										System.out.println("Código de producto no encontrado");
									}
								}
							} else {
								System.out.println(
										"No hay pedidos!\n Introduza:\n add pedido <id del pedido> <codigo del producto (str)> <cantidad> <calle (str)> <numero (int)> <codigo postal (int)> <poblacion (str)> <pais (str)> <destinatario> <fecha de entrega estimada>");
							}
						} else {
							System.out.println("Introduza:\n add productos <codigo producto (str)> <id pedido (str)>");
						}
					}

					// Añadiendo producto
					if (arguments.get(0).equalsIgnoreCase("producto")) {
						if (arguments.size() == 7) {
							if ("PASILLO|ESTANTERIA|ESTANTE".contains(arguments.get(5))) {
								almacen.addProducto(new Producto(arguments.get(1), arguments.get(2), arguments.get(3),
										Integer.parseInt(arguments.get(4)), Localizacion.valueOf(arguments.get(5)),
										Boolean.parseBoolean(arguments.get(6))));
								System.out.println("Producto añadido correctamente!");
							} else {
								System.out.println("Localización del producto no válida!");
							}
						} else {
							System.out.println(
									"Introduza:\n add producto <codigo (str)> <nombre (str)> <descripcion (str)> <stock (int)> <PASILLO|ESTANTERIA|ESTANTE> <true|false>");
						}
					}
					// Añadiendo cliente
					if (arguments.get(0).equalsIgnoreCase("cliente")) {
						if (arguments.size() == 5) {
							almacen.addClientes(new Clientes(arguments.get(1), arguments.get(2), arguments.get(3),
									arguments.get(4), null));
							System.out.println("Cliente añadido correctamente!");
						} else {
							System.out.println(
									"Introduza:\n add cliente <nombre (str)> <apellido (str)> <email (str)> <telf. contacto (str)>");
						}
					}
					// Añadiendo dirección a un cliente
					if (arguments.get(0).equalsIgnoreCase("direccion")) {
						if (arguments.size() == 7) {
							if (almacen.getClientes().size() > 0) {
								for (int i = 0; i < almacen.getClientes().size(); i++) {
									if (arguments.get(1).equals(almacen.getClientes().get(i).getNombre())) {
										almacen.getClientes().get(i).getDireccion()
												.add(new Direccion(arguments.get(2), Integer.parseInt(arguments.get(3)),
														Integer.parseInt(arguments.get(4)), arguments.get(5),
														arguments.get(6)));
										System.out.println("Dirección para el cliente con nombre ["
												+ almacen.getClientes().get(i).getNombre()
												+ "] añadida correctamente!");
									} else {
										System.out.println("Nombre de cliente no encontrado");
									}
								}
							} else {
								System.out.println(
										"No hay clientes!\n Introduza:\n add cliente <nombre (str)> <apellido (str)> <email (str)> <telf. contacto (str)>");
							}
						} else {
							System.out.println(
									"Introduza:\n add direccion <nombre cliente (str)> <calle (str)> <numero (int)> <codigo postal (int)> <poblacion (str)> <pais (str)>");
						}
					}
				}
			}

			// Comando para salir de la consola
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("Consola cerrada correctamente.");
				run = false;
			}
			System.out.print("almacen > ");
		}
		input.close();
	}

	public static void printHelp() {
		String output = "=========| AYUDA |=========\n";
		output += "help - Lista de comandos\n";
		output += "add <producto|cliente|direccion|pedido|productos> CONTENIDO\n";
		output += "display <all|almacen|clientes|productos|pedidos>\n";
		output += "json - Crear archivo json\n";
		output += "load <path> - Carga el documento XML desde la ruta especificada\n";
		output += "save <path> - Guarda el documento XML en la ruta especificada\n";
		output += "exit\n";
		System.out.println(output);
	}
}
