package tsp_ec;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import geneticAlgorithm.Selection;
import geneticAlgorithm.Variation;

/**
 * Clase principal del proyecto donde se realizan la secuencia de ejecución de
 * tareas del Algoritmo Genético y se definen las variables con las que
 * trabajará el sistema.
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Main {
	/**
	 * 
	 * Lectura fichero ciudades (DONE)
	 * 
	 * Lectura fichero parámetros (DONE)
	 * 
	 * Inicialización de la población (DONE)
	 * 
	 * Cálculo del fitness de cada individuo (DONE)
	 * 
	 * Funciones de mutación y crossover (DONE) (tener en cuenta las probabilidades
	 * DONE)
	 * 
	 * Funciones de selección de padres y supervivientes (DONE)
	 * 
	 * Condición de terminación (DONE a medias)
	 * 
	 * Pruebas con TSPs conocidos
	 * 
	 * Cambiar los parámetros y seguir probando
	 * 
	 * Comentar el código y depurarlo
	 * 
	 * Interfaz gráfica sencilla
	 * 
	 */

	/*
	 * Óptimo para las 15 ciudades: 1,13,2,15,9,5,7,3,12,14,10,8,6,4,11: 291
	 * 
	 * Individuo: 14,12,3,7,5,9,15,2,13,1,11,4,6,8,10, Fitness: 284.380862862478
	 * Child: 5,7,3,12,14,10,8,6,4,11,1,13,2,15,9, Fitness:284.380862862478
	 * Solución: 1,13,2,15,9,5,7,3,12,14,10,8,6,4,11, Fitness: 284.38086286247795
	 */

	public static TreeMap<Integer, City> cities; // Ciudad asociada a un número entero
	public static GAParameters params; // Parámetros del Algoritmo Genético
	public static LinkedList<String> population; // Población
	public static LinkedList<String> matingPool; // Conjunto de padres
	public static LinkedList<String> offspring; // Conjunto de descendientes

	public static void main(String args[]) {
		FileManagement.mainMenu();
		FileManagement.readCities();
		FileManagement.readParameters();

		Double optimalSolution = getOptimalSolution();

		for (int i = 0; i < 10; i++) {
			initialization();

			int generations = 0;

			long tStart = System.currentTimeMillis();
			while (fitnessFunction(getOptimal()) > optimalSolution && generations < 10000) {
				generations++;
				/*
				 * Mutación antes o después del parentSelection, pero siempre antes del
				 * survivorSelection
				 */
				Variation.mutation();
				//System.out.println("M");
				Selection.parentSelection();
				//System.out.println("PS");
				Variation.crossover();
				//System.out.println("Cr");
				Selection.survivorSelection();
				//System.out.println("SS");
			}

			long tEnd = System.currentTimeMillis();
			long difference = tEnd - tStart;
			FileManagement.writeData(difference, fitnessFunction(getOptimal()));
			System.out.println("Tiempo de ejecución: " + difference);

			System.out.println("Solución: " + getOptimal());
			System.out.println("Fitness: " + fitnessFunction(getOptimal()));
			System.out.println("Generaciones: " + generations);
			System.out.println("Óptimo: " + optimalSolution);
		}

		FileManagement.writeEvaluationFile(Evaluation.computeSuccessRate(optimalSolution),
				Evaluation.computeMBF());

	}

	private static Double getOptimalSolution() {
		Double optimalSolution;
		switch (FileManagement.citiesPath) {
		case "TSP-15":
			optimalSolution = fitnessFunction("1,13,2,15,9,5,7,3,12,14,10,8,6,4,11");
			break;
		case "TSP-100":
			optimalSolution = fitnessFunction(
					"1,47,93,28,67,58,61,51,87,25,81,69,64,40,54,2,44,50,73,68,85,82,95,13,76,33,37,5,52,78,96,39,30,48,100,41,71,14,3,43,46,29,34,83,55,7,9,57,20,12,27,86,35,62,60,77,23,98,91,45,32,11,15,17,59,74,21,72,10,84,36,99,38,24,18,79,53,88,16,94,22,70,66,26,65,4,97,56,80,31,89,42,8,92,75,19,90,49,6,63");
			break;
		case "TSP-280":
			optimalSolution = fitnessFunction(
					"1,2,242,243,244,241,240,239,238,237,236,235,234,233,232,231,246,245,247,250,251,230,229,228,227,226,225,224,223,222,221,220,219,218,217,216,215,214,213,212,211,210,207,206,205,204,203,202,201,198,197,196,195,194,193,192,191,190,189,188,187,186,185,184,183,182,181,176,180,179,150,178,177,151,152,156,153,155,154,129,130,131,20,21,128,127,126,125,124,123,122,121,120,119,157,158,159,160,175,161,162,163,164,165,166,167,168,169,170,172,171,173,174,107,106,105,104,103,102,101,100,99,98,97,96,95,94,93,92,91,90,89,109,108,110,111,112,88,87,113,114,115,117,116,86,85,84,83,82,81,80,79,78,77,76,75,74,73,72,71,70,69,68,67,66,65,64,58,57,56,55,54,53,52,51,50,49,48,47,46,45,44,59,63,62,118,61,60,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,22,25,23,24,14,15,13,12,11,10,9,8,7,6,5,4,277,276,275,274,273,272,271,16,17,18,19,132,133,134,270,269,135,136,268,267,137,138,139,149,148,147,146,145,199,200,144,143,142,141,140,266,265,264,263,262,261,260,259,258,257,254,253,208,209,252,255,256,249,248,278,279,3,280");
			break;
		case "TSP-2392":
			optimalSolution = 378032.0;
			break;
		default:
			optimalSolution = 0.0;
			break;
		}
		return optimalSolution;
	}

	/**
	 * Método que genera aleatoriamente los individuos de la población. Cada
	 * individuo es una cadena de texto que define una permutación de ciudades.
	 * Todos los individuos son almacenados en una lista.
	 */
	public static void initialization() {

		population = new LinkedList<String>();

		/*
		 * Se generan tantos individuos como el parámetro del tamaño de la población
		 * indique
		 */
		for (int i = 0; i < params.getPopulationSize(); i++) {
			String individualStr = "";
			LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();

			/*
			 * Para evitar que se repitan valores se crea un mapa con valores aleatorios
			 * comprendidos entre los valores máximo y mínimo de las claves del mapa en el
			 * que se encuentran todas las ciudades.
			 */
			while (individual.keySet().size() != cities.keySet().size()) {
				double rndm = Math.random() * ((cities.keySet().size() - 1) + 1) + 1;
				individual.put((int) rndm, cities.get((int) rndm));

			}

			/*
			 * Cada individuo se representa a través de una cadena de texto formada por
			 * números enteros separados por ',' indicando un recorrido, es decir, una
			 * permutación de las ciudades.
			 */
			for (Integer key : individual.keySet()) {
				individualStr += String.valueOf(key);
				individualStr += ",";
			}

			/* Se añade el individuo a la población */
			population.add(individualStr);
		}

	}

	/**
	 * Método para calcular el valor de adaptación de cada individuo
	 * 
	 * @param individualStr Cadena de texto que contiene la permutación que
	 *                      representa al individuo
	 * @return Valor de adaptación (distancia total del recorrido)
	 */
	public static Double fitnessFunction(String individualStr) {

		String[] individualStrArray = individualStr.split(",");

		/*
		 * Es necesario representar cada individuo como un mapa, para asociar los
		 * objetos City y poder calcular las distancias con el método implementado en
		 * esta clase
		 */
		LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();
		for (String string : individualStrArray) {
			individual.put(Integer.parseInt(string), cities.get(Integer.parseInt(string)));
		}

		Double fitness = Double.valueOf(0);
		boolean first = true;
		int i = 0;
		Integer previous = null;
		Integer firstKey = null;

		for (Integer key : individual.keySet()) {
			/*
			 * Si es la primera ciudad se guarda y se pasa a la siguiente, puesto que no hay
			 * anterior con la que comparar
			 */
			if (first) {
				previous = key;
				first = false;
				firstKey = key;
				i++;
				continue;
			}

			/*
			 * Se añade al valor de adaptación la distancia entre la ciudad anterior y la
			 * actual
			 */
			fitness += individual.get(previous).getDistance(individual.get(key));
			/* Se guarda la ciudad actual para compararla con la siguiente */
			previous = key;
			i++;

			/*
			 * Finalmente, se añade la distancia entre la última ciudad y la primera, puesto
			 * que son recorridos cíclicos
			 */
			if (i == individual.keySet().size()) {
				fitness += individual.get(key).getDistance(individual.get(firstKey));
			}

		}

		return fitness;

	}

	/**
	 * Comparación de los valores de adaptación de la población de la última
	 * generación para obtener el individuo con mayor valor de adaptación.
	 * 
	 * @return Cadena de texto que representa el recorrido con menor distancia
	 */
	public static String getOptimal() {
		Double lastFitness = null;
		Double currentFitness = null;
		String solution = "";

		for (String candidate : population) {

			if (lastFitness == null) {
				lastFitness = fitnessFunction(candidate);
			}

			currentFitness = fitnessFunction(candidate);

			if (Double.compare(currentFitness, lastFitness) <= 0) {
				lastFitness = currentFitness;
				solution = candidate;
			}

		}

		return solution;
	}
}
