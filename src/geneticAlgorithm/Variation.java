package geneticAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import tsp_ec.Main;
import tsp_ec.Utils;

/**
 * Clase con la implementación de los operadores de variación (cruce y mutación)
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Variation {

	/**
	 * Elección aleatoria de parejas de padres y puntos de cruce.
	 * 
	 * Se realiza el cruce en función de la probabilidad dada.
	 */
	public static void crossover() {

		Main.offspring = new LinkedList<String>();

		String[] parent1 = new String[Main.cities.size()];
		String[] parent2 = new String[Main.cities.size()];

		int randomParent1 = 0;
		int randomParent2 = 0;

		double randomParent1D = 0;
		double randomParent2D = 0;

		int point1;
		int point2;

		boolean end = false;

		while (!end) {

			String[] child1 = new String[Main.cities.size()];
			String[] child2 = new String[Main.cities.size()];

			/*
			 * Elección aleatoria de dos padres dentro de todos los elegidos en el proceso
			 * de selección
			 */
			randomParent1D = (Math.random() * ((Main.matingPool.size() - 1) + 1));
			randomParent1 = (int) randomParent1D;

			while (randomParent1 == randomParent2) {
				randomParent2D = (Math.random() * ((Main.matingPool.size() - 1) + 1));
				randomParent2 = (int) randomParent2D;
			}

			parent1 = Main.matingPool.get(randomParent1).split(",");
			parent2 = Main.matingPool.get(randomParent2).split(",");

			double random = Math.random();

			/*
			 * Según la probabilidad de cruce dada, se realiza Partially Mapped Crossover
			 * entre los dos padres escogidos aleatoriamente
			 */
			if (random < Main.params.getCrossoverRate()) {
				// Puntos de corte aleatorios
				double result1 = Math.random() * ((Main.cities.keySet().size() - 1) + 1);
				point1 = (int) result1;

				double result2 = Math.random() * ((Main.cities.keySet().size() - (point1 + 1)) + 1) + (point1 + 1);
				point2 = (int) result2;

				partiallyMappedCrossover(parent1, parent2, child1, point1, point2);
				partiallyMappedCrossover(parent2, parent1, child2, point1, point2);

				Main.offspring.add(Utils.arrayToString(child1));
				Main.offspring.add(Utils.arrayToString(child2));

			} else {
				/*
				 * En caso de no realizarse el método de cruce, la descendencia se genera de
				 * forma asexual, siendo cada hijo una copia de cada padre
				 */
				Main.offspring.add(Utils.arrayToString(parent1));
				Main.offspring.add(Utils.arrayToString(parent2));
			}

			Main.matingPool.remove(randomParent1);
			if (randomParent2 != 0)
				Main.matingPool.remove(randomParent2 - 1);
			else
				Main.matingPool.remove(randomParent2);

			randomParent1 = 0;
			randomParent2 = randomParent1;
			if (Main.matingPool.isEmpty())
				end = true;

		}

	}

	/**
	 * Implementación de Partially Mapped Crossover
	 * 
	 * @param parent1 Primer padre a partir del que se genera un hijo (P1)
	 * @param parent2 Segundo padre a partir del que se genera un hijo (P2)
	 * @param child   Descendencia creada a partir de ambos padres
	 * @param point1  Primer punto de cruce
	 * @param point2  Segundo punto de cruce
	 */
	private static void partiallyMappedCrossover(String[] parent1, String[] parent2, String[] child, int point1,
			int point2) {
		/*
		 * Se copia el segmento delimitado por los puntos de cruce de P1 en el hijo
		 */
		for (int i = point1; i < point2; i++) {
			child[i] = parent1[i];
		}

		/* Recorrer el segmento de cruce en P2 */
		for (int i = point1; i < point2; i++) {
			boolean copied = false;

			/*
			 * Comprobar si el valor de la posición i de P2 está en el segmento del hijo
			 */
			for (int j = point1; j < point2; j++) {

				if (parent2[i].equals(child[j])) {
					copied = true;
					break;
				}
			}

			int ni = i;
			/* Si el valor no se encuentra en el hijo */
			if (!copied) {
				int j;
				while (true) {
					j = findPositionInParent(parent2, child, ni);
					if (child[j] == null)
						break;
					else
						ni = j;
				}
				child[j] = parent2[i];

			}
		}
		/*
		 * Copiar en los huecos vacíos del hijo los valores restantes de P2
		 */
		for (int i = 0; i < child.length; i++) {
			if (child[i] == null)
				child[i] = parent2[i];
		}
	}

	/**
	 * Buscar en el padre la posición que ocupa el valor situado en una posición ya
	 * ocupada en el hijo
	 * 
	 * @param parent                  Array de números enteros que representa al
	 *                                padre
	 * @param child                   Array de números enteros que representa al
	 *                                hijo
	 * @param alreadyOccupiedPosition Posición no nula en el hijo
	 * @return Posición del valor en el padre
	 */
	private static int findPositionInParent(String[] parent, String[] child, int alreadyOccupiedPosition) {
		int newPosition;
		for (newPosition = 0; newPosition < parent.length; newPosition++) {
			if (parent[newPosition].equals(child[alreadyOccupiedPosition])) {
				break;
			}
		}
		return newPosition;
	}

	/**
	 * Se realiza la mutación en función de la probabilidad dada.
	 */
	public static void mutation() {

		ArrayList<String> mutation = new ArrayList<String>();

		/*
		 * Elección según la probabilidad de mutación dada de los individuos de la
		 * población que sufren mutación
		 */
		Main.population.forEach((v) -> {
			double random = Math.random();
			if (random < Main.params.getMutationRate()) {
				mutation.add(v);
			}
		});
		for (String string : mutation) {
			swapMutation(string);
		}

	}

	/**
	 * Implementación de Swap Mutation.
	 * 
	 * @param individual Individuo al que se aplica la mutación
	 */
	private static void swapMutation(String individual) {

		String[] individualStrArray = individual.split(",");

		// Posiciones aleatorias
		double result1 = Math.random() * ((Main.cities.keySet().size() - 1) + 1);
		int point1 = (int) result1;

		double result2 = Math.random() * ((Main.cities.keySet().size() - 1) + 1);
		int point2 = (int) result2;

		String value1 = individualStrArray[point1];
		String value2 = individualStrArray[point2];

		/* Intercambio de los valores de dos posiciones escogidas aleatoriamente */
		individualStrArray[point1] = value2;
		individualStrArray[point2] = value1;

		Main.population.remove(individual);
		Main.population.add(Utils.arrayToString(individualStrArray));

	}
}
