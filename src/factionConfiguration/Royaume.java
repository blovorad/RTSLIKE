package factionConfiguration;

import configuration.EntityConfiguration;

public class Royaume extends Race 
{
	public Royaume()
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
		
		setName("Royaume");
	}
	
	public void initWorker()
	{
		setWorker(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Travailleur royaume", 5));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Cavalier royaume", 5));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin royaume", 5));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer royaume", 5));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special royaume", 5));
	}
	
	public void initForge()
	{
		this.getBuildings().put(EntityConfiguration.FORGE, new ForBuilding(100, 10, 10, 1, 10, null, "Forge Royaume", 100));
	}
	
	public void initHq()
	{
		this.getBuildings().put(EntityConfiguration.HQ, new ForBuilding(100, 10, 10, 1, 10, null, "Quartier general Royaume", 100));
	}
	
	public void initCastle()
	{
		this.getBuildings().put(EntityConfiguration.CASTLE, new ForBuilding(100, 10, 10, 1, 10, null, "Chateau Royaume", 100));
	}
	
	public void initTower()
	{
		this.getBuildings().put(EntityConfiguration.TOWER, new ForBuilding(100, 10, 10, 1, 10, null, "Tour Royaume", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getBuildings().put(EntityConfiguration.STORAGE, new ForBuilding(100, 10, 10, 1, 10, null, "Stockage Royaume", 100));
	}
	
	public void initBarrack()
	{
		this.getBuildings().put(EntityConfiguration.BARRACK, new ForBuilding(100, 10, 10, 1, 10, null, "Caserne Royaume", 100));
	}
	
	public void initArchery()
	{
		this.getBuildings().put(EntityConfiguration.ARCHERY, new ForBuilding(100, 10, 10, 1, 10, null, "Archerie Royaume", 100));
	}
	
	public void initStable()
	{
		this.getBuildings().put(EntityConfiguration.STABLE, new ForBuilding(100, 10, 10, 1, 10, null, "Ecurie Royaume", 100));
	}
	
	public void initUpgrades()
	{
		
	}
	
}
