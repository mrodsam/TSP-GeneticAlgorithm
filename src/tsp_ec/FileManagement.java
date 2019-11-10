package tsp_ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;
import java.util.TreeMap;

public class FileManagement {
	public static void readCities() {

		System.out.println(
				"Por favor, introduzca el path del fichero donde se encuentran las coordenadas de las ciudades: ");

		String entradaTeclado = "";
		Scanner entradaEscaner = new Scanner(System.in);
		entradaTeclado = entradaEscaner.nextLine();

		System.out.println("Entrada recibida por teclado es: \"" + entradaTeclado + "\"");

		Main.cities = new TreeMap<Integer, City>();

		URL url = null;
		if (entradaTeclado.isEmpty())
			url = Main.class.getResource("TSP-15.txt");
		else
			url = Main.class.getResource(entradaTeclado);

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
