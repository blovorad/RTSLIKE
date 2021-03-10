package factionConfiguration;

import java.util.List;

public class ForBuilding extends Patron
{
	private List<ForUpgrade>upgrades;
	// TODO ajout cost et modifier constructeur
	
	public ForBuilding(int hp, int age, List<ForUpgrade>upgrades, String description, int hpMax)
	{
		super(hp, age, description, hpMax, 0);
		this.upgrades = upgrades;
	}

	public List<ForUpgrade> getUpgrades() 
	{
		return upgrades;
	}

	public void setUpgrades(List<ForUpgrade> upgrades) 
	{
		this.upgrades = upgrades;
	}
}
