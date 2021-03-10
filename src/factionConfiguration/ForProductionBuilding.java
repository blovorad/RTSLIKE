package factionConfiguration;

import java.util.AbstractMap;
import java.util.List;

public class ForProductionBuilding extends Patron{
	
	private AbstractMap<Integer, ForUpgrade> upgrades;
	// TODO ajout cost et modifier constructeur
	
	public ForProductionBuilding(int hp, int age, AbstractMap<Integer, ForUpgrade> upgrades, String description, int hpMax)
	{
		super(hp, age, description, hpMax, 0);
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
