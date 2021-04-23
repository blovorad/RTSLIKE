package factionConfiguration;

import java.util.AbstractMap;
/**
 * 
 * extends patron to fit with production building neeed
 * @author gautier
 */
public class ForProductionBuilding extends Patron{
	
	/**
	 * upgrade
	 */
	private AbstractMap<Integer, ForUpgrade> upgrades;
	
	public ForProductionBuilding(int hp, int age, AbstractMap<Integer, ForUpgrade> upgrades, String description, int sightRange, int cost)
	{
		super(hp, age, description, hp, sightRange, cost);
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
