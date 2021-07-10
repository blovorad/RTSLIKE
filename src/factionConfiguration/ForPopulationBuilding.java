package factionConfiguration;

public class ForPopulationBuilding extends Patron{
	
	private int population;
	
	public ForPopulationBuilding(int hp, int age, String description, int sightRange, int cost, int population) {
		super(hp, age, description, hp, sightRange, cost);
		this.population = population;
	}
	
	public int getPopulation() {
		return this.population;
	}

}
