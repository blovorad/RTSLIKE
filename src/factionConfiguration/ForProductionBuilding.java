package factionConfiguration;

import java.util.AbstractMap;

public class ForProductionBuilding extends Patron{
	
	private AbstractMap<Integer, ForUpgrade> upgrades;
	// TODO ajout cost et modifier constructeur
	
	public ForProductionBuilding(int hp, int age, AbstractMap<Integer, ForUpgrade> upgrades, String description, int hpMax, int sightRange)
	{
		super(hp, age, description, hpMax, 0, sightRange);
		this.upgrades = upgrades;
	}

	public AbstractMap<Integer, ForUpgrade> getUpgrades() 
	{
		return upgrades;
	}

	public void setUpgrades(AbstractMap<Integer, ForUpgrade> upgrades) 
	{
		this.upgrades = upgrades;
	}

}
