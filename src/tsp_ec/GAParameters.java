package tsp_ec;

/**
 * Clase para almacenar los valores de los parámetros del Algoritmo Genético
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class GAParameters {

	private Integer populationSize;
	private Double crossoverRate; // range[0.5,1.0]
	private Integer tournamentSize;
	private Double mutationRate; //

	public GAParameters() {

	}

	/**
	 * Constructor con todos los parámetros necesarios
	 * 
	 * @param populationSize Tamaño de la población
	 * @param crossoverRate Probabilidad de cruce
	 * @param mutationRate	Probabilidad de mutación
	 * @param tournamentSize Tamaño del torneo para la selección de padres
	 */
	public GAParameters(Integer populationSize, Double crossoverRate, Double mutationRate, Integer tournamentSize) {
		this.populationSize = populationSize;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.tournamentSize = tournamentSize;
	}
	/**
	 * Constructor con todos los parámetros salvo la probabilidad de mutación (calculada a partir del tamaño de la población)
	 * 
	 * @param populationSize Tamaño de la población
	 * @param crossoverRate	Probabilidad de cruce
	 * @param tournamentSize Tamaño del torneo para la selección de padres
	 */
	public GAParameters(Integer populationSize, Double crossoverRate, Integer tournamentSize) {
		this.populationSize = populationSize;
		this.crossoverRate = crossoverRate;
		this.mutationRate = 1.0 / populationSize;
		this.tournamentSize = tournamentSize;
	}

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
		this.mutationRate = 1.0 / populationSize;
	}

	public Double getCrossoverRate() {
		return crossoverRate;
	}

	public void setCrossoverRate(Double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	public Double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(Double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public Integer getTournamentSize() {
		return tournamentSize;
	}

	public void setTournamentSize(Integer tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

}
