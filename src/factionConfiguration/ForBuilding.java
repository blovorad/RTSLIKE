package factionConfiguration;

import java.util.List;

public class ForBuilding extends Patron
{
	private List<ForUpgrade>upgrades;
	
	public ForBuilding(int hp, int age, List<ForUpgrade>upgrades, String description, int hpMax)
	{
		super(hp, age, description, hpMax);
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
