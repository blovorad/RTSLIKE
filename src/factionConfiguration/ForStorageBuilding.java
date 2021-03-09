package factionConfiguration;

public class ForStorageBuilding extends Patron{
	
	private int ressources;
	
	public ForStorageBuilding(int hp, int age, String description, int hpMax)
	{
		super(hp, age, description, hpMax);
	}

	public int getRessources() {
		return ressources;
	}

	public void setRessources(int ressources) {
		this.ressources = ressources;
	}
	
	
}
