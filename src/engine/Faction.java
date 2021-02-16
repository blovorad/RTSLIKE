package engine;

import factionConfiguration.Barbare;
import factionConfiguration.Empire;
import factionConfiguration.Gaia;
import factionConfiguration.Race;
import factionConfiguration.Royaume;

public class Faction 
{
	private Race race;
	private int age;
	private int nbBuilding;
	private EntitiesManager entities;

	public Faction(int id) 
	{
		age = 1;
		nbBuilding = 0;
		
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
		
		entities = new EntitiesManager(this);
	}
	
	public void update() 
	{
		/*printRace();
		System.out.println("statistique infantry armor : " + race.getInfantry().getArmor());
		System.out.println("statistique cavlry armor : " + race.getCavalry().getArmor());*/
		entities.update();
	}
	
	public void createBuilding() 
	{
		nbBuilding++;
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

	public EntitiesManager getEntities() 
	{
		return entities;
	}

	public void setEntities(EntitiesManager entities) 
	{
		this.entities = entities;
	}

	public Race getRace() 
	{
		return race;
	}

	public void setRace(Race race) 
	{
		this.race = race;
	}
	
	//for testing not in final build
	public void printRace() 
	{
		System.out.println(race);
	}
}
