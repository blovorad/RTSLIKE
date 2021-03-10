package factionConfiguration;

public class ForStorageBuilding extends Patron{
	
	private int ressources;
	
	public ForStorageBuilding(int hp, int age, String description, int hpMax, int sightRange, int cost)
	{
		super(hp, age, description, hpMax, sightRange, cost);
	}

	public int getRessources() {
		return ressources;
	}

	public void setRessources(int ressources) {
		this.ressources = ressources;
	}
	
	
}
