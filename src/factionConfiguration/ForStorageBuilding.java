package factionConfiguration;
/**
 * 
 * @author gautier
 * extends patron to fit with storage needs
 */
public class ForStorageBuilding extends Patron{
	
	public ForStorageBuilding(int hp, int age, String description, int sightRange, int cost)
	{
		super(hp, age, description, hp, sightRange, cost);
	}

}
