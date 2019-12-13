package tsp_ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
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

	public static String tspFile = Paths.get("./ProblemFiles/").toString();
	public static String parametersFile = Paths.get("./ProblemFiles/").toString();

	public static void mainMenu() {
		int menuInput;

		System.out.println(
				"Introduzca el número del menú correspondiente a la instancia del problema (o el nombre del archivo): ");
		System.out.println("1. TSP-15");
		System.out.println("2. TSP-100");
		System.out.println("3. TSP-280");
		System.out.println("4. TSP-2392");

		Scanner scInput = new Scanner(System.in);
		if (scInput.hasNextInt()) {
			menuInput = scInput.nextInt();
			switch (menuInput) {
			case 1:
				tspFile += "/TSP-15.txt";
				break;
			case 2:
				tspFile += "/TSP-100.txt";
				break;
			case 3:
				tspFile += "/TSP-280.txt";
				break;
			case 4:
				tspFile += "/TSP-2392.txt";
				break;

			default:
				tspFile += "/TSP-15.txt";
				break;
			}
		} else
			tspFile += "/" + scInput.next();

		System.out.println(
				"Introduzca el número del menú correspondiente al archivo en el que se encuentran los parámetros del algoritmo: (o el nombre de un nuevo archivo) ");
		System.out.println("1. GAParameters.txt");
		if (scInput.hasNextInt())
			parametersFile += "/GAParameters.txt";
		else {
			String parametersInput = scInput.next();
			parametersFile += "/" + parametersInput;
		}

	}

	/**
	 * Lectura del archivo de texto que contiene las coordenadas de las ciudades.
	 */
	public static void readCities() {

		Main.cities = new TreeMap<Integer, City>();

		File file = new File(Paths.get(tspFile).toUri());
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
		File file = new File(Paths.get(parametersFile).toUri());
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

	/**
	 * Almacenamiento en un archivo de texto del tiempo que tarda en realizarse cada
	 * ejecución del algoritmo y del mejor valor de adaptación obtenido en dicha
	 * ejecución.
	 * 
	 * @param executionTime Duración de una ejecución
	 * @param fitness       Mejor valor de adaptación al finalizar la ejecución
	 */
	public static void writeTimes(long executionTime, Double fitness) {
		try {
			FileWriter fw = new FileWriter(Paths.get("./Evaluation") + "/times.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(executionTime + "\t");
			pw.println(fitness);
			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo de tiempos: " + e.getMessage());
		}

	}

	/**
	 * Almacenamiento en un archivo de texto de la tasa de éxito y el mejor fitness
	 * promedio. También se incluyen la solución óptima conocida para el problema y
	 * los parámetros utilizados para la ejecución del algoritmo
	 * 
	 * @param sr  Tasa de éxito
	 * @param MBF Mejor fitness promedio
	 */
	public static void writeEvaluationFile(Double sr, Double MBF, Double aes) {

		try {
			FileWriter fw = new FileWriter(Paths.get("./Evaluation") + "/evaluation.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(sr + "\t");
			pw.print(MBF + "\t");
			pw.print(aes + "\t");
			pw.print(Main.getOptimalSolution() + "\t");
			pw.print(Main.params.getPopulationSize() + "\t");
			pw.print(Main.params.getCrossoverRate() + "\t");
			pw.print(Main.params.getMutationRate() + "\t");
			pw.println(Main.params.getTournamentSize());

			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo de evaluación: " + e.getMessage());
		}

	}

	/**
	 * Almacenamiento en varios archivos de texto (uno por ejecución) del mejor
	 * valor de adaptación obtenido en cada generación para representar
	 * posteriormente las curvas de progreso.
	 * 
	 * @param fitness    Mejor valor de adaptación
	 * @param generation Número de generaciones
	 * @param execution  Número de ejecución
	 */
	public static void writeProgressCurveValues(Double fitness, int generation, int execution) {
		try {
			File file = new File(Paths.get("./Evaluation") + "/progressCurves" + execution + ".txt");
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(generation + "\t");
			pw.print(fitness);
			pw.println();

			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo de curvas de progreso: " + e.getMessage());
		}
	}

	/**
	 * Almacenamiento en un archivo de texto del número de generaciones hasta
	 * alcanzar el óptimo global para el cálculo del AES
	 * 
	 * @param generations Número de generaciones
	 */
	public static void writeAESValues(int generations) {
		try {
			FileWriter fw = new FileWriter(Paths.get("./Evaluation") + "/aes.txt", true);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(generations);
			pw.println();

			pw.close();
		} catch (IOException e) {
			System.out.println("Error al escribir el archivo aes.txt: " + e.getMessage());
		}
	}
}
