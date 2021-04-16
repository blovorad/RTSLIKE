package factionConfiguration;

import configuration.EntityConfiguration;
/**
 * 
 * @author gautier
 *
 */
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
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(5 , 10, 10, 5, 3, 20, 20, 2, 25, "Travailleur gaia", 20, 10, 1, 10, 10, 15, 25));
	}
	
	public void initCavalry()
	{
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(5 , 10, 10, 5, 3, 50, 20, 2, 25, "Cavalier gaia", 5, 25));
	}
	
	public void initInfantry()
	{
		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(5, 10, 10, 3, 1, 25, 15, 1, 25, "Fantassin gaia", 5, 25));
	}
	
	public void initArcher()
	{
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(5, 10, 10, 3, 1, 25, 15, 1, 25, "Archer gaia", 5, 25));
	}
	
	public void initSpecial()
	{
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(5, 10, 10, 3, 1, 25, 15, 1, 25, "Special gaia", 5, 25));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 2, null, "Forge Gaia", 100, 20, 25));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, null, "Quartier general Gaia", 100, 20, 25));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 3, null, "Chateau Gaia", 100, 20, 25));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 10, 10, 10 ,10 ,1, "Tour Gaia", 100, 25));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Gaia", 100, 20, 25));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Gaia", 100, 20, 25));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 2, null, "Archerie Gaia", 100, 20, 25));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 2, null, "Ecurie Gaia", 100, 20, 25));
	}
	
	public void initUpgrades()
	{
		this.getForgeUpgrades().put(EntityConfiguration.ARMOR_UPGRADE, new ForUpgrade(1, "better armor", 5, EntityConfiguration.ARMOR_UPGRADE, 60, 25));
		this.getForgeUpgrades().put(EntityConfiguration.DAMAGE_UPGRADE, new ForUpgrade(1, "better sword", 5, EntityConfiguration.DAMAGE_UPGRADE, 60, 25));
	}
	
	public void initHQUpgrades()
	{
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE, new ForUpgrade(1, "upgrade age tier 2", 1, EntityConfiguration.AGE_UPGRADE, 130, 300));
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE_2, new ForUpgrade(1, "upgrade age tier 3", 1, EntityConfiguration.AGE_UPGRADE_2, 160, 300));
	}
}
