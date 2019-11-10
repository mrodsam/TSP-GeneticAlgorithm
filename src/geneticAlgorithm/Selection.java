package geneticAlgorithm;

import java.util.LinkedList;

import tsp_ec.Main;

public class Selection {

	/*
	 * Hay que escoger 10 padres y emparejarlos aleatoriamente, los individuos más
	 * aptos en la población actual van a tener más probabilidad de ser
	 * seleccionados como padres y podrán aparecer repetidos más veces
	 */
	public static void parentSelection() {
		//System.out.println();
		//System.out.println("SELECCIÓN DE PADRES");

		Main.matingPool = new LinkedList<String>();
		LinkedList<String> candidatesPool = new LinkedList<String>();

		String currentMember = "";
		Double lastFitness = null;
		Double currentFitness = null;

		/*
		 * Tournament selection: with replacement
		 * 
		 * With/without replacement, deterministic/stochastic
		 */

		/* Deterministic: pick first the most fit member */
		double random;
		/*
		 * Necesitamos tantos padres como el número de individuos en la población actual
		 */

		for (int i = 0; i < Main.params.getPopulationSize(); i++) {

			/*
			 * Escoger k candidatos aleatoriamente entre la población actual Entiendo que
			 * aquí también se pueden repetir
			 */
			for (int i1 = 0; i1 < Main.params.getTournamentSize(); i1++) {
				random = (Math.random() * ((Main.population.size() - 1) + 1));

				currentMember = Main.population.get((int) random);
				candidatesPool.add(currentMember);

			}

			/* Selección del mejor candidato */
			for (String candidate : candidatesPool) {

				if (lastFitness == null) {
					lastFitness = Main.fitnessFunction(candidate);
				}

				currentFitness = Main.fitnessFunction(candidate);

				if (Double.compare(currentFitness, lastFitness) <= 0) {
					lastFitness = currentFitness;
					currentMember = candidate;
				}

			}

			lastFitness = null;
			currentFitness = null;

			candidatesPool.clear();
			Main.matingPool.add(currentMember);
		}

		//System.out.println("Padres: " + Main.matingPool.size());
		//Main.matingPool.forEach((v) -> System.out.println("Padre: " + v + " Fitness: " + Main.fitnessFunction(v)));

	}

	public static void survivorSelection() {
		//System.out.println("SELECCIÓN DE SUPERVIVIENTES");
		/*
		 * Comparar el fitness del padre que es el más fit con los hijos, si ninguno
		 * gana, se queda el padre Si se queda el padre, buscamos otro en la población
		 * que sustituir con el hijo Si no se queda el padre se cambian padres por hijos
		 */
		String mostFit = "";
		String leastFit = "";
		boolean first = true;
		double lastFitness = 0.0;
		boolean offspringMostFit = false;

		/*
		 * Escoger al individuo más fit de la población
		 */
		for (String individual : Main.population) {

			if (first) {
				lastFitness = Main.fitnessFunction(individual);
				mostFit = individual;
				first = false;
			}

			if (Main.fitnessFunction(individual) < lastFitness) {
				lastFitness = Main.fitnessFunction(individual);
				mostFit = individual;

			}
		}
		/*
		 * Comparo el más fit de la población vieja con los hijos, para ver si ganan los
		 * hijos
		 */
		for (String child : Main.offspring) {
			if (Main.fitnessFunction(child) <= Main.fitnessFunction(mostFit)) {
				offspringMostFit = true;
				break;
			}
		}

		/*
		 * Si gana uno de los hijos, me cargo a toda la población y meto a todos
		 * los hijos en su lugar
		 */
		if (offspringMostFit) {
			Main.population.clear();
			Main.population = (LinkedList<String>) Main.offspring.clone();
			/*
			 * Si ninguno de los hijos gana: Busco al menos fit de los hijos, me lo cargo y
			 * creo una nueva población con el más fit de los padres y los hijos restantes
			 */
		} else {
			first = true;
			for (String child : Main.offspring) {
				if (first) {
					lastFitness = Main.fitnessFunction(child);
					first = false;
				}

				if (Main.fitnessFunction(child) > lastFitness) {
					lastFitness = Main.fitnessFunction(child);
					leastFit = child;
				}
			}
			Main.offspring.remove(leastFit);

			Main.population.clear();
			Main.population = (LinkedList<String>) Main.offspring.clone();
			Main.population.add(mostFit);
		}

		//Main.population.forEach((v) -> System.out.println("Individuo: " + v));

	}
}
