package factionConfiguration;

import configuration.EntityConfiguration;

public class Barbare extends Race
{
	public Barbare()
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
		
		setName("Barbare");
	}
	
	public void initWorker()
	{
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Travailleur barbare", 5, 10, 1, 10));
	}
	
	public void initCavalry()
	{
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Cavalier barbare", 5));
	}
	
	public void initInfantry()
	{
		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin barbare", 5));
	}
	
	public void initArcher()
	{
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer barbare", 5));
	}
	
	public void initSpecial()
	{
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special barbare", 5));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 1, null, "Forge Barbare", 100));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, null, "Quartier general Barbare", 100));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 1, null, "Chateau Barbare", 100));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 10, 10, 10 ,10 ,1, "Tour Barbare", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Barbare", 100));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Barbare", 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 1, null, "Archerie Barbare", 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 1, null, "Ecurie Barbare", 100));
	}
	
	public void initUpgrades()
	{
		
	}
}
