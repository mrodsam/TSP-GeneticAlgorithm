package tsp_ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	public static String citiesPath;

	public static void mainMenu() {
		int menuInput;

		System.out.println("Introduzca el valor entero correspondiente: ");
		System.out.println("1. TSP-15");
		System.out.println("2. TSP-100");
		System.out.println("3. TSP-280");
		System.out.println("4. TSP-2392");

		Scanner scInput = new Scanner(System.in);
		menuInput = scInput.nextInt();
		scInput.close();

		switch (menuInput) {
		case 1:
			citiesPath = "TSP-15";
			break;
		case 2:
			citiesPath = "TSP-100";
			break;
		case 3:
			citiesPath = "TSP-280";
			break;
		case 4:
			citiesPath = "TSP-2392";
			break;

		default:
			citiesPath = "TSP-15";
			break;
		}
	}

	/**
	 * Lectura del archivo de texto que contiene las coordenadas de las ciudades.
	 */
	public static void readCities() {

		Main.cities = new TreeMap<Integer, City>();

		URL url = Main.class.getResource(citiesPath);

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
			i = Integer.parseInt(sc.next());

			c = new City(Double.valueOf(sc.next()), Double.valueOf(sc.next()));

			Main.cities.put(i, c);
		}

	}

	/**
	 * Lectura del archivo de texto que contiene los parámetros del Algoritmo
	 * Genético
	 */
	public static void readParameters() {

		Main.params = new GAParameters();

		URL url = Main.class.getResource("GAParameters");
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
			if (line.split(",").length == 4)
				Main.params.setMutationRate(Double.parseDouble(line.split(",")[3]));

		}

	}

	public static void writeData(long executionTime, Double fitness) {
		try {
			FileWriter fw = new FileWriter("times.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(executionTime + "\t");
			pw.println(fitness);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeEvaluationFile(Double sr, Double MBF) {

		try {
			FileWriter fw = new FileWriter("evaluation.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sr+"\t");
			pw.println(MBF);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
