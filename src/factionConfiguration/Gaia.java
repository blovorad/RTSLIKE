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
		setWorker(new ForWorker(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Travailleur gaia", 5, 10, 5));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Cavalier gaia", 5));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin gaia", 5));
	}
	
	public void initArcher()
	{
		setArcher(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer gaia", 5));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special gaia", 5));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 1, null, "Forge Gaia", 100));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, null, "Quartier general Gaia", 100));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 1, null, "Chateau Gaia", 100));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 10, 10, 10 ,10 ,1, "Tour Gaia", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Gaia", 100));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Gaia", 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 1, null, "Archerie Gaia", 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 1, null, "Ecurie Gaia", 100));
	}
	
	public void initUpgrades()
	{
		
	}
}
