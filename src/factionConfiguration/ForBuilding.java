package factionConfiguration;

import java.util.List;

public class ForBuilding extends Patron
{
	private List<ForUpgrade>upgrades;
	private List<ForUnit>units;
	
	public ForBuilding(int hp, int attackRange, int sightRange, int age, int attackSpeed, List<ForUnit> units, List<ForUpgrade>upgrades)
	{
		super(hp, age, sightRange, attackSpeed, attackRange);
		this.upgrades = upgrades;
		this.units = units;
	}

	public List<ForUnit> getUnits() 
	{
		return units;
	}

	public void setUnits(List<ForUnit> units) 
	{
		this.units = units;
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
