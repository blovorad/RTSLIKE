package factionConfiguration;

import java.util.List;

public class ForBuilding extends Patron
{
	private List<ForUpgrade>upgrades;
	private List<ForUnit>units;
	
	public ForBuilding(int hp, int sightRange, int age, int timeToBuild, List<ForUnit> units, List<ForUpgrade>upgrades)
	{
		this.setHp(hp);
		this.setAge(age);
		this.setTimeToBuild(timeToBuild);
		this.setUnits(units);
		this.setUpgrades(upgrades);
		this.setSightRange(sightRange);
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
