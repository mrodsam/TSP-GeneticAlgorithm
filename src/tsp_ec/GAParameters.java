package tsp_ec;

public class GAParameters {

	private Integer populationSize;
	private Double crossoverRate; //range[0.5,1.0]
	private Integer tournamentSize;
	private Double mutationRate; //

	public GAParameters() {

	}

	public GAParameters(Integer populationSize, Double crossoverRate, Double mutationRate, Integer tournamentSize) {
		this.populationSize = populationSize;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.tournamentSize = tournamentSize;
	}
	
	public GAParameters(Integer populationSize, Double crossoverRate, Integer tournamentSize) {
		this.populationSize = populationSize;
		this.crossoverRate = crossoverRate;
		this.mutationRate = 1.0/populationSize;
		this.tournamentSize = tournamentSize;
	}

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
		this.mutationRate = 1.0/populationSize;
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
