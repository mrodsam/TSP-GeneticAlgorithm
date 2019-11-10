package geneticAlgorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import tsp_ec.Main;
import tsp_ec.Utils;

public class Variation {

	public static void crossover() {

		//System.out.println("CRUCE");

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

		/*
		 * Escoger aleatoriamente a dos padres y repetir hasta que matingPool esté vacía
		 */

		while (!end) {

			String[] child1 = new String[Main.cities.size()];
			String[] child2 = new String[Main.cities.size()];

			// (Math.random() * ((max - min) + 1)) + min

			randomParent1D = (Math.random() * ((Main.matingPool.size() - 1) + 1));
			randomParent1 = (int) randomParent1D;

			while (randomParent1 == randomParent2) {
				randomParent2D = (Math.random() * ((Main.matingPool.size() - 1) + 1));
				randomParent2 = (int) randomParent2D;
			}

			parent1 = Main.matingPool.get(randomParent1).split(",");
			parent2 = Main.matingPool.get(randomParent2).split(",");

			//System.out.println("Parent1");
			//for (String string : parent1) {
				//System.out.print(string + ",");
			//}
			//System.out.println();
			//System.out.println("Parent2");
			//for (String string : parent2) {
				//System.out.print(string + ",");
			//}
			//System.out.println();

			double random = Math.random();

			if (random < Main.params.getCrossoverRate()) {
				// Puntos de corte aleatorios
				double result1 = Math.random() * ((Main.cities.keySet().size() - 1) + 1);
				point1 = (int) result1;

				double result2 = Math.random() * ((Main.cities.keySet().size() - (point1 + 1)) + 1) + (point1 + 1);
				point2 = (int) result2;

				//System.out.println("Puntos de crossover: " + point1 + ", " + point2);

				partiallyMappedCrossover(parent1, parent2, child1, point1, point2);
				partiallyMappedCrossover(parent2, parent1, child2, point1, point2);

				Main.offspring.add(Utils.arrayToString(child1));
				Main.offspring.add(Utils.arrayToString(child2));

				//System.out.println("Child1");
				for (int i = 0; i < child1.length; i++) {
					//System.out.print(child1[i] + ",");
				}
				//System.out.println();
				//System.out.println("Child2");
				for (int i = 0; i < child2.length; i++) {
					//System.out.print(child2[i] + ",");
				}
				//System.out.println();

			} else {
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

		//Main.offspring.forEach((v) -> System.out.println("Child: " + v + " Fitness: " + Main.fitnessFunction(v)));

	}

	private static void partiallyMappedCrossover(String[] parent1, String[] parent2, String[] child, int point1,
			int point2) {
		// copy the segment between them from the first parent (P1) into the first
		// offspring
		for (int i = point1; i < point2; i++) {
			child[i] = parent1[i];
		}

		/* Recorrer el segmento de cruce en P2 */
		for (int i = point1; i < point2; i++) {
			boolean copied = false;

			/*
			 * Comprobar si el valor de la posición i de parent2 está en el segmento de
			 * child1
			 */
			for (int j = point1; j < point2; j++) {

				if (parent2[i].equals(child[j])) {
					copied = true;
					break;
				}
			}

			/* Si el valor no está en child1 */
			int ni = i;
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
		 * Copiar en los huecos vacíos del hijo los valores del segundo padre utilizado
		 */
		for (int i = 0; i < child.length; i++) {
			if (child[i] == null)
				child[i] = parent2[i];
		}
	}

	private static int findPositionInParent(String[] parent, String[] child, int alreadyOccupiedPosition) {
		int newPosition;
		for (newPosition = 0; newPosition < parent.length; newPosition++) {
			if (parent[newPosition].equals(child[alreadyOccupiedPosition])) {
				break;
			}
		}
		return newPosition;
	}

	public static void mutation() {

		ArrayList<String> mutation = new ArrayList<String>();

		Main.population.forEach((v) -> {
			double random = Math.random();
			if (random < Main.params.getMutationRate()) {
				/* El individuo sufre mutación */
				mutation.add(v);
			}
		});
		for (String string : mutation) {
			swapMutation(string);
		}

	}

	private static void swapMutation(String individual) {

		//System.out.println("MUTACIÓN");

		String[] individualStrArray = individual.split(",");

		// Puntos de corte aleatorios
		double result1 = Math.random() * ((Main.cities.keySet().size() - 1) + 1);
		int point1 = (int) result1;

		double result2 = Math.random() * ((Main.cities.keySet().size() - 1) + 1);
		int point2 = (int) result2;

		//System.out.println("Puntos de mutación: " + point1 + ", " + point2);

		//System.out.println("Valores de mutación: " + individualStrArray[point1] + ", " + individualStrArray[point2]);
		String value1 = individualStrArray[point1];
		String value2 = individualStrArray[point2];

		individualStrArray[point1] = value2;

		individualStrArray[point2] = value1;

		/*
		 * Aquí va a haber drama si hay individuos repetidos porque me borra la primera
		 * instancia que aparezca Aunque igual no es drama porque total yo solo quiero
		 * borrar uno
		 */
		Main.population.remove(individual);
		Main.population.add(Utils.arrayToString(individualStrArray));

//		//System.out.println("Individuo mutado de: " + individual + " a " + Utils.arrayToString(individualStrArray));
//		//System.out.println(Main.fitnessFunction(Utils.arrayToString(individualStrArray)));

	}
}
