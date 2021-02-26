package factionConfiguration;

import configuration.EntityConfiguration;

public class Gaia extends Race
{
	public Gaia()
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
		
		setName("Gaia");
	}
	
	public void initWorker()
	{
		setWorker(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "Travailleur gaia"));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "Cavalier gaia"));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin gaia"));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer gaia"));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special gaia"));
	}
	
	public void initForge()
	{
		this.getBuildings().put(EntityConfiguration.FORGE, new ForBuilding(100, 10, 10, 1, 10, null, "Forge Gaia"));
	}
	
	public void initHq()
	{
		this.getBuildings().put(EntityConfiguration.HQ, new ForBuilding(100, 10, 10, 1, 10, null, "Quartier general Gaia"));
	}
	
	public void initCastle()
	{
		this.getBuildings().put(EntityConfiguration.CASTLE, new ForBuilding(100, 10, 10, 1, 10, null, "Chateau Gaia"));
	}
	
	public void initTower()
	{
		this.getBuildings().put(EntityConfiguration.TOWER, new ForBuilding(100, 10, 10, 1, 10, null, "Tour Gaia"));
	}
	
	public void initRessourceStockage()
	{
		this.getBuildings().put(EntityConfiguration.STORAGE, new ForBuilding(100, 10, 10, 1, 10, null, "Stockage Gaia"));
	}
	
	public void initBarrack()
	{
		this.getBuildings().put(EntityConfiguration.BARRACK, new ForBuilding(100, 10, 10, 1, 10, null, "Caserne Gaia"));
	}
	
	public void initArchery()
	{
		this.getBuildings().put(EntityConfiguration.ARCHERY, new ForBuilding(100, 10, 10, 1, 10, null, "Archerie Gaia"));
	}
	
	public void initStable()
	{
		this.getBuildings().put(EntityConfiguration.STABLE, new ForBuilding(100, 10, 10, 1, 10, null, "Ecurie Gaia"));
	}
	
	public void initUpgrades()
	{
		
	}
}
