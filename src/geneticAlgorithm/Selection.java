package geneticAlgorithm;

import java.util.LinkedList;

import tsp_ec.Main;

/**
 * Clase con la implementación de la selección de padres y la selección de
 * supervivientes
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Selection {

	/*
	 * Hay que escoger 10 padres y emparejarlos aleatoriamente, los individuos más
	 * aptos en la población actual van a tener más probabilidad de ser
	 * seleccionados como padres y podrán aparecer repetidos más veces
	 */
	/**
	 * Selección de padres.
	 * 
	 * La selección se realiza por torneo, con un tamaño configurado previamente.
	 * 
	 * Son seleccionados tantos padres como el tamaño de la población actual.
	 */
	public static void parentSelection() {

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
		 * Se escogen tantos padres como el número de individuos en la población actual
		 */
		for (int i = 0; i < Main.params.getPopulationSize(); i++) {

			/*
			 * Elegir k candidatos aleatoriamente entre la población actual
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

	}

	/**
	 * Selección de supervivientes.
	 * 
	 * Método generacional: se sustituye la población actual por la nueva población.
	 * 
	 * Elitismo: el individuo con mejor valor de adaptación permanece en la
	 * población.
	 */
	public static void survivorSelection() {
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
		 * Se busca al individuo con mejor valor de adaptación de la población
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
		 * Se compara al individuo con mejor valor de adaptación de la población con los
		 * hijos
		 */
		for (String child : Main.offspring) {
			if (Main.fitnessFunction(child) <= Main.fitnessFunction(mostFit)) {
				offspringMostFit = true;
				break;
			}
		}

		/*
		 * Si alguno de los hijos tiene mejor valor de adaptación, se sustituye a toda
		 * la generación anterior por la nueva
		 */
		if (offspringMostFit) {
			Main.population.clear();
			Main.population = (LinkedList<String>) Main.offspring.clone();
			
			/*
			 * En caso de que ninguno de los hijos tenga mejor valor de adaptación: se busca
			 * al que peor valor de adaptación tiene, se elimina y se sustituye por el
			 * individuo con mejor valor de adaptación de la generación anterior
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

	}
}
