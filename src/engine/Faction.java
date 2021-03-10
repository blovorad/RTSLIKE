package engine;

import java.util.AbstractMap;
import java.util.HashMap;

import factionConfiguration.Barbare;
import factionConfiguration.Empire;
import factionConfiguration.ForUpgrade;
import factionConfiguration.Gaia;
import factionConfiguration.Race;
import factionConfiguration.Royaume;

public class Faction 
{
	private Race race;
	private int age;
	private int buildingCount;
	private int populationCount;
	private int maxPopulation;
	private int moneyCount;
	private AbstractMap<Integer, ForUpgrade> upgradesDone;

	public Faction(int id) 
	{
		age = 1;
		buildingCount = 0;
		maxPopulation = 20;
		moneyCount = 200;
		populationCount = 0;
		upgradesDone = new HashMap<Integer, ForUpgrade>();
		
		if(id == 1)
		{
			race = new Royaume();
		}
		else if(id == 2)
		{
			race = new Barbare();
		}
		else if(id == 3)
		{
			race = new Empire();
		}
		else if(id == 4)
		{
			race = new Gaia();
		}
	}
	
	public int getAge() 
	{
		return age;
	}

	public void setAge(int age) 
	{
		this.age = age;
	}

	public int getBuildingCount() 
	{
		return buildingCount;
	}

	public void setBuildingCount(int buildingCount) 
	{
		this.buildingCount = buildingCount;
	}

	public Race getRace() 
	{
		return race;
	}

	public void setRace(Race race) 
	{
		this.race = race;
	}
	

	public int getMaxPopulation() {
		return maxPopulation;
	}

	public void setMaxPopulation(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public int getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(int moneyCount) {
		this.moneyCount = moneyCount;
	}

	public AbstractMap<Integer, ForUpgrade> getUpgradesDone() {
		return upgradesDone;
	}

	public void setUpgradesDone(AbstractMap<Integer, ForUpgrade> upgradesDone) {
		this.upgradesDone = upgradesDone;
	}

	public int getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(int populationCount) {
		this.populationCount = populationCount;
	}
}
