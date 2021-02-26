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
		setWorker(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "Travailleur barbare", 5));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "Cavalier barbare", 5));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Fantassin barbare", 5));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer barbare", 5));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special barbare", 5));
	}
	
	public void initForge()
	{
		this.getBuildings().put(EntityConfiguration.FORGE, new ForBuilding(100, 10, 10, 1, 10, null, "Forge Barbare", 100));
	}
	
	public void initHq()
	{
		this.getBuildings().put(EntityConfiguration.HQ, new ForBuilding(100, 10, 10, 1, 10, null, "Quartier general Barbare", 100));
	}
	
	public void initCastle()
	{
		this.getBuildings().put(EntityConfiguration.CASTLE, new ForBuilding(100, 10, 10, 1, 10, null, "Chateau Barbare", 100));
	}
	
	public void initTower()
	{
		this.getBuildings().put(EntityConfiguration.TOWER, new ForBuilding(100, 10, 10, 1, 10, null, "Tour Barbare", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getBuildings().put(EntityConfiguration.STORAGE, new ForBuilding(100, 10, 10, 1, 10, null, "Stockage Barbare", 100));
	}
	
	public void initBarrack()
	{
		this.getBuildings().put(EntityConfiguration.BARRACK, new ForBuilding(100, 10, 10, 1, 10, null, "Caserne Barbare", 100));
	}
	
	public void initArchery()
	{
		this.getBuildings().put(EntityConfiguration.ARCHERY, new ForBuilding(100, 10, 10, 1, 10, null, "Archerie Barbare", 100));
	}
	
	public void initStable()
	{
		this.getBuildings().put(EntityConfiguration.STABLE, new ForBuilding(100, 10, 10, 1, 10, null, "Ecurie Barbare", 100));
	}
	
	public void initUpgrades()
	{
		
	}
}
