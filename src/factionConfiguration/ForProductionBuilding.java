package factionConfiguration;

import java.util.AbstractMap;
/**
 * 
 * @author gautier
 *
 */
public class ForProductionBuilding extends Patron{
	
	private AbstractMap<Integer, ForUpgrade> upgrades;
	
	public ForProductionBuilding(int hp, int age, AbstractMap<Integer, ForUpgrade> upgrades, String description, int hpMax, int sightRange, int cost)
	{
		super(hp, age, description, hpMax, sightRange, cost);
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
