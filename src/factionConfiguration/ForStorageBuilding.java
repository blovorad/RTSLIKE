package factionConfiguration;

public class ForStorageBuilding extends Patron{
	
	private int ressources;
	// TODO ajout cost et modifier constructeur
	
	public ForStorageBuilding(int hp, int age, String description, int hpMax, int sightRange)
	{
		super(hp, age, description, hpMax, 0, sightRange);
	}

	public int getRessources() {
		return ressources;
	}

	public void setRessources(int ressources) {
		this.ressources = ressources;
	}
	
	
}
