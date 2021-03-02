package engine;

import java.util.AbstractMap;
import java.util.List;

import factionConfiguration.Barbare;
import factionConfiguration.Empire;
import factionConfiguration.ForBuilding;
import factionConfiguration.Gaia;
import factionConfiguration.Race;
import factionConfiguration.Royaume;

public class Faction 
{
	private Race race;
	private int age;
	private int nbBuilding;
	private int nbUnit;
	private int population;
	private int money;
	private List<Upgrades> upgradesDone;
	private AbstractMap<Integer, ForBuilding> buildings;

	public Faction(int id) 
	{
		age = 1;
		nbBuilding = 0;
		population = 20;
		money = 0;
		setNbUnit(0);
		
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
		
		buildings = race.getBuildings();
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
		return nbBuilding;
	}

	public void setBuildingCount(int buildingCount) 
	{
		this.nbBuilding = buildingCount;
	}

	public Race getRace() 
	{
		return race;
	}

	public void setRace(Race race) 
	{
		this.race = race;
	}
	

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public AbstractMap<Integer, ForBuilding> getBuildings() {
		return buildings;
	}

	public void setBuildings(AbstractMap<Integer, ForBuilding> buildings) {
		this.buildings = buildings;
	}

	public List<Upgrades> getUpgradesDone() {
		return upgradesDone;
	}

	public void setUpgradesDone(List<Upgrades> upgradesDone) {
		this.upgradesDone = upgradesDone;
	}

	public int getNbUnit() {
		return nbUnit;
	}

	public void setNbUnit(int nbUnit) {
		this.nbUnit = nbUnit;
	}
}
