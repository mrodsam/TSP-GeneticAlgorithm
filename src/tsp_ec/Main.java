package tsp_ec;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import geneticAlgorithm.Selection;
import geneticAlgorithm.Variation;

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
	 * individual.put(1, cities.get(1)); individual.put(13, cities.get(13));
	 * individual.put(2, cities.get(2)); individual.put(15, cities.get(15));
	 * individual.put(9, cities.get(9)); individual.put(5, cities.get(5));
	 * individual.put(7, cities.get(7)); individual.put(3, cities.get(3));
	 * individual.put(12, cities.get(12)); individual.put(14, cities.get(14));
	 * individual.put(10, cities.get(10)); individual.put(8, cities.get(8));
	 * individual.put(6, cities.get(6)); individual.put(4, cities.get(4));
	 * individual.put(11, cities.get(11));
	 * 
	 * Individuo: 14,12,3,7,5,9,15,2,13,1,11,4,6,8,10, Fitness: 284.380862862478
	 * (óptimo alcanzado) Child: 5,7,3,12,14,10,8,6,4,11,1,13,2,15,9, Fitness: 284.380862862478
	 * Solución: 1,13,2,15,9,5,7,3,12,14,10,8,6,4,11, Fitness: 284.38086286247795
	 */

	public static TreeMap<Integer, City> cities;
	public static GAParameters params;
	/*
	 * String - Permutación de las claves del mapa/Mapa con las ciudades en el orden
	 * dado
	 */
	// static LinkedHashMap<String, LinkedHashMap<Integer, City>> population;
	public static LinkedList<String> population;
	public static LinkedList<String> matingPool;
	public static LinkedList<String> offspring;

	// static LinkedHashMap<String, LinkedHashMap<Integer, City>> offspring;

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
		
		getOptimal();


	}

	public static void initialization() {

		/*
		 * Uso mapas porque así no se pueden repetir las claves
		 * 
		 */

		population = new LinkedList<String>();

		/*
		 * Se generan tantos individuos como el parámetro del tamaño de la población
		 * indique
		 */
		for (int i = 0; i < params.getPopulationSize(); i++) {
			String individualKey = "";
			/*
			 * Cada individuo es un mapa, con el ID de una ciudad y la ciudad(coordenada
			 * X+Y)
			 */
			LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();

			/*
			 * Se generan los mapas aleatoriamente apartir del número de ciudades en el mapa
			 * original
			 */
			while (individual.keySet().size() != cities.keySet().size()) {
				double rndm = Math.random() * ((cities.keySet().size() - 1) + 1) + 1;

				individual.put((int) rndm, cities.get((int) rndm));

			}

			/*
			 * La clave del mapa del cada individuo es una cadena indicando la permutación
			 * correspondiente
			 */
			for (Integer key : individual.keySet()) {
				individualKey += String.valueOf(key);
				individualKey += ",";
			}

			/* Se añade el individuo a la población */
			population.add(individualKey);
		}

		//population.forEach((v) -> System.out.println("Individuo: " + v + " Fitness: " + fitnessFunction(v)));

	}

	public static Double fitnessFunction(String individualStr) {

		String[] individualStrArray = individualStr.split(",");

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

			/* Se añade al fitness la distancia entre la ciudad anterior y la actual */
			fitness += individual.get(previous).getDistance(individual.get(key));
			/* Se guarda la ciudad actual para compararla con la siguiente */
			previous = key;
			i++;

			/* Hay que añadir la distancia entre la última ciudad y la primera */
			if (i == individual.keySet().size()) {
				fitness += individual.get(key).getDistance(individual.get(firstKey));
			}

		}

		return fitness;

	}

	public static void getOptimal() {
		Double lastFitness = null;
		Double currentFitness = null;
		String currentMember = "";
		
		for (String candidate : population) {

			if (lastFitness == null) {
				lastFitness = fitnessFunction(candidate);
			}

			currentFitness = fitnessFunction(candidate);

			if (Double.compare(currentFitness, lastFitness) <= 0) {
				lastFitness = currentFitness;
				currentMember = candidate;
			}

		}
		
		System.out.println("Solución: "+currentMember+" Fitness: "+fitnessFunction(currentMember));
	}
}
