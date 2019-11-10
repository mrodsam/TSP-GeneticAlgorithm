package tsp_ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Clase para la gestión de los ficheros en los que se encuentran las
 * coordenadas de cada ciudad y los parámetros del Algoritmo Genético
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class FileManagement {
	/**
	 * Lectura del archivo de texto que contiene las coordenadas de las ciudades.
	 */
	public static void readCities() {

		System.out.println(
				"Por favor, introduzca el path del fichero donde se encuentran las coordenadas de las ciudades: ");

		String keyboardInput = "";
		Scanner scInput = new Scanner(System.in);
		keyboardInput = scInput.nextLine();

		System.out.println("Entrada recibida por teclado es: \"" + keyboardInput + "\"");

		Main.cities = new TreeMap<Integer, City>();

		URL url = null;
		if (keyboardInput.isEmpty())
			url = Main.class.getResource("TSP-15.txt");
		else
			url = Main.class.getResource(keyboardInput);

		File file = new File(url.getPath());
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		City c = null;
		int i = 1;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			c = new City(Double.parseDouble(line.split(",")[0]), Double.parseDouble(line.split(",")[1]));

			Main.cities.put(i, c);
			i++;
		}

	}

	/**
	 * Lectura del archivo de texto que contiene los parámetros del Algoritmo
	 * Genético
	 */
	public static void readParameters() {

		System.out.println(
				"Por favor, introduzca el path del fichero donde se encuentran los parámetros del algoritmo genético ");

		String entradaTeclado = "";
		Scanner entradaEscaner = new Scanner(System.in);
		entradaTeclado = entradaEscaner.nextLine();

		System.out.println("Entrada recibida por teclado es: \"" + entradaTeclado + "\"");

		entradaEscaner.close();
		Main.params = new GAParameters();

		URL url = null;
		if (entradaTeclado.isEmpty())
			url = Main.class.getResource("GAParameters");
		else
			url = Main.class.getResource(entradaTeclado);
		File file = new File(url.getPath());
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			Main.params.setPopulationSize(Integer.parseInt(line.split(",")[0]));
			Main.params.setCrossoverRate(Double.parseDouble(line.split(",")[1]));
			Main.params.setTournamentSize(Integer.parseInt(line.split(",")[2]));

		}

	}
}
