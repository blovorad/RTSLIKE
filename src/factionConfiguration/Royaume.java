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
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Travailleur royaume", 5, 10, 1, 10));
	}
	
	public void initCavalry()
	{
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Cavalier royaume", 5));
	}
	
	public void initInfantry()
	{
		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin royaume", 5));
	}
	
	public void initArcher()
	{
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer royaume", 5));
	}
	
	public void initSpecial()
	{
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special royaume", 5));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 1, null, "Forge Royaume", 100));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, null, "Quartier general Royaume", 100));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 1, null, "Chateau Royaume", 100));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 10, 10, 10 ,10 ,1, "Tour Royaume", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Royaume", 100));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Royaume", 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 1, null, "Archerie Royaume", 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 1, null, "Ecurie Royaume", 100));
	}
	
	public void initUpgrades()
	{
		
	}
	
}
