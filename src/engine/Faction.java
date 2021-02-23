package engine;

import java.util.List;

import configuration.EntityConfiguration;
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
	private int population;
	private int money;
	private EntitiesManager entities;
	private List<Upgrades> upgradesDone;

	public Faction(int id) 
	{
		age = 1;
		nbBuilding = 0;
		population = 0;
		money = 0;
		
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
	
	public void createBuilding(int id, Position position) 
	{
		//ForBuilding building = race.getBuildings().get(id);
		Building b = null;
		
		if(id == EntityConfiguration.FORGE)
		{
			//List<Upgrades> list = faction.getListUpgrade();
			//tu dois créer les upgrades a la main ici
			//exemple Upgrades epe = new Upgrades();
			for(int i =0; i < 1; i++)
			{
				//ici tu regarde si les upgrades sont deja faite et les remove  a la list ou celle des autres batiments
			}
			b = new Forge(position);
		}
		//dans les autres tu balances le ForUnit de la race.
		else if(id == EntityConfiguration.STABLE)
		{
			b = new Stable(position, race.getCavalry());
		}
		else if(id == EntityConfiguration.BARRACK)
		{
			b = new Barrack(position);
		}
		else if(id == EntityConfiguration.ARCHERY)
		{
			b = new Archery(position);
		}
		else if(id == EntityConfiguration.HQ)
		{
			b = new Hq(position);
		}
		else if(id == EntityConfiguration.CASTLE)
		{
			b = new Castle(position);
		}
		//coder pas prio storage et tower
		else if(id == EntityConfiguration.STORAGE)
		{
			b = new RessourcesStorage(position);
		}
		else if(id == EntityConfiguration.TOWER)
		{
			b = new Tower(position);
		}
		else
		{
			System.out.println("invalide ID");
		}
		
		this.entities.addBuilding(b);
		nbBuilding++;
		System.out.println("ajoutation building");
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
}
