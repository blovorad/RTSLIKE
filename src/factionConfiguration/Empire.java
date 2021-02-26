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
		setWorker(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "Travailleur empire"));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "Cavalier empire"));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin empire"));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer empire"));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special empire"));
	}
	
	public void initForge()
	{
		this.getBuildings().put(EntityConfiguration.FORGE, new ForBuilding(100, 10, 10, 1, 10, null, "Forge Empire"));
	}
	
	public void initHq()
	{
		this.getBuildings().put(EntityConfiguration.HQ, new ForBuilding(100, 10, 10, 1, 10, null, "Quartier general Empire"));
	}
	
	public void initCastle()
	{
		this.getBuildings().put(EntityConfiguration.CASTLE, new ForBuilding(100, 10, 10, 1, 10, null, "Chateau Empire"));
	}
	
	public void initTower()
	{
		this.getBuildings().put(EntityConfiguration.TOWER, new ForBuilding(100, 10, 10, 1, 10, null, "Tour Empire"));
	}
	
	public void initRessourceStockage()
	{
		this.getBuildings().put(EntityConfiguration.STORAGE, new ForBuilding(100, 10, 10, 1, 10, null, "Stockage Empire"));
	}
	
	public void initBarrack()
	{
		this.getBuildings().put(EntityConfiguration.BARRACK, new ForBuilding(100, 10, 10, 1, 10, null, "Caserne Empire"));
	}
	
	public void initArchery()
	{
		this.getBuildings().put(EntityConfiguration.ARCHERY, new ForBuilding(100, 10, 10, 1, 10, null, "Archerie Empire"));
	}
	
	public void initStable()
	{
		this.getBuildings().put(EntityConfiguration.STABLE, new ForBuilding(100, 10, 10, 1, 10, null, "Ecurie Empire"));
	}
	
	public void initUpgrades()
	{
		
	}
}
