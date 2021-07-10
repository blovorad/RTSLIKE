package factionConfiguration;

import configuration.EntityConfiguration;
/**
 * 
 * gaia faction taht represent nature, ressource, not realy usefull for the moment
 * @author gautier
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
		initHouse();
		initForgeUpgrades();
		initHQUpgrades();
		
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
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 2, this.getForgeUpgrades(), "Forge Gaia", 400, 150));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(300, 2, this.getHQUpgrades(), "Quartier general Gaia", 400, 250));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(400, 3, null, "Chateau Gaia", 400, 250));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(150, 400, 1, 80 ,10 ,1, "Tour Gaia", 175));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(50 , 1, "Stockage Gaia", 300, 50));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(125, 1, null, "Caserne Gaia", 400, 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(125, 2, null, "Archerie Gaia", 400, 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(140, 2, null, "Ecurie Gaia", 400, 125));
	}
	
	public void initHouse() {
		this.getPopulationBuildings().put(EntityConfiguration.HOUSE, new ForPopulationBuilding(125, 1, "Maison Gaia", 400, 100, 5));
	}
	
	public void initForgeUpgrades()
	{
		this.getForgeUpgrades().put(EntityConfiguration.ARMOR_UPGRADE, new ForUpgrade(2, "armure en fer", 1, EntityConfiguration.ARMOR_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.DAMAGE_UPGRADE, new ForUpgrade(2, "épée en acier", 1, EntityConfiguration.DAMAGE_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.ATTACK_SPEED_UPGRADE, new ForUpgrade(3, "apprentissage de l'équilibre", 5, EntityConfiguration.ATTACK_SPEED_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.ATTACK_RANGE_UPGRADE, new ForUpgrade(3, "lame plus longue", 1, EntityConfiguration.ATTACK_RANGE_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.SIGHT_RANGE_UPGRADE, new ForUpgrade(3, "lunette de vue", 75, EntityConfiguration.SIGHT_RANGE_UPGRADE, 4, 75));
	}
	
	public void initHQUpgrades()
	{
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE, new ForUpgrade(1, "upgrade age tier 2", 1, EntityConfiguration.AGE_UPGRADE, 7, 300));
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE_2, new ForUpgrade(1, "upgrade age tier 3", 1, EntityConfiguration.AGE_UPGRADE_2, 7, 500));
	}
	
}
