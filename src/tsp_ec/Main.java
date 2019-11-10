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
		FileManagement.readCities();
		FileManagement.readParameters();

		initialization();

		for (int i = 0; i < 1000; i++) {
			/*
			 * Mutación antes o después del parentSelection, pero siempre antes del
			 * survivorSelection
			 */
			Variation.mutation();
			Selection.parentSelection();
			Variation.crossover();
			Selection.survivorSelection();
		}

		System.out.println("Solución: "+getOptimal());

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
