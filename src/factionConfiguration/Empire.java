package factionConfiguration;

import configuration.EntityConfiguration;

public class Empire extends Race
{
	public Empire()
	{
		super();
		
		initCavalry();
		initInfantry();
		initArcher();
		initSpecial();
		initWorker();
		initForge();
		initHq();
		initCastle();
		initTower();
		initRessourceStockage();
		initBarrack();
		initArchery();
		initStable();
		initUpgrades();
		
		setName("Empire");
	}
	
	public void initWorker()
	{
		setWorker(new ForWorker(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Travailleur empire", 5, 10, 5));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForFighter(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Cavalier empire", 5));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin empire", 5));
	}
	
	public void initArcher()
	{
		setArcher(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer empire", 5));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special empire", 5));
	}
	
	public void initForge()
	{
		this.getBuildings().put(EntityConfiguration.FORGE, new ForBuilding(100, 10, 10, 1, 10, null, "Forge Empire", 100));
	}
	
	public void initHq()
	{
		this.getBuildings().put(EntityConfiguration.HQ, new ForBuilding(100, 10, 10, 1, 10, null, "Quartier general Empire", 100));
	}
	
	public void initCastle()
	{
		this.getBuildings().put(EntityConfiguration.CASTLE, new ForBuilding(100, 10, 10, 1, 10, null, "Chateau Empire", 100));
	}
	
	public void initTower()
	{
		this.getBuildings().put(EntityConfiguration.TOWER, new ForBuilding(100, 10, 10, 1, 10, null, "Tour Empire", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getBuildings().put(EntityConfiguration.STORAGE, new ForBuilding(100, 10, 10, 1, 10, null, "Stockage Empire", 100));
	}
	
	public void initBarrack()
	{
		this.getBuildings().put(EntityConfiguration.BARRACK, new ForBuilding(100, 10, 10, 1, 10, null, "Caserne Empire", 100));
	}
	
	public void initArchery()
	{
		this.getBuildings().put(EntityConfiguration.ARCHERY, new ForBuilding(100, 10, 10, 1, 10, null, "Archerie Empire", 100));
	}
	
	public void initStable()
	{
		this.getBuildings().put(EntityConfiguration.STABLE, new ForBuilding(100, 10, 10, 1, 10, null, "Ecurie Empire", 100));
	}
	
	public void initUpgrades()
	{
		
	}
}
