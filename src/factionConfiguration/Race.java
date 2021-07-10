package factionConfiguration;

import java.util.AbstractMap;
import java.util.HashMap;
/**
 * 
 * class contain all stat of a race, all patron are stock in this class and use by faction
 * @author gautier
 * @see faction
 * @see patron
 */
public abstract class Race 
{
	/**
	 * name of the faction
	 */
	private String name;
	/**
	 * all patron of fighter, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForFighter>patronFighters;
	/**
	 * all patron of worker, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForWorker>patronWorkers;
	/**
	 * all patron of production building, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForProductionBuilding>productionBuildings;
	/**
	 * all patron of attack building, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForAttackBuilding>attackBuildings;
	/**
	 * all patron of storage building, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForStorageBuilding>storageBuildings;
	
	private AbstractMap<Integer, ForPopulationBuilding>populationBuildings;
	/**
	 * all patron of Hq upgrade, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForUpgrade> HQUpgrades;
	/**
	 * all patron of forge upgrade, hashMap require because it will be more easier to get what we need
	 */
	private AbstractMap<Integer, ForUpgrade> ForgeUpgrades;
	
	public Race()
	{
		this.patronFighters = new HashMap<Integer, ForFighter>();
		this.patronWorkers = new HashMap<Integer, ForWorker>();
		this.storageBuildings = new HashMap<Integer, ForStorageBuilding>();
		this.attackBuildings = new HashMap<Integer, ForAttackBuilding>();
		this.productionBuildings = new HashMap<Integer, ForProductionBuilding>();
		this.populationBuildings = new HashMap<Integer, ForPopulationBuilding>();
		this.HQUpgrades = new HashMap<Integer, ForUpgrade>();
		this.ForgeUpgrades = new HashMap<Integer, ForUpgrade>();
		
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String toString()
	{
		return "" + name;
	}

	public AbstractMap<Integer, ForProductionBuilding> getProductionBuildings() {
		return productionBuildings;
	}

	public AbstractMap<Integer, ForAttackBuilding> getAttackBuildings() {
		return attackBuildings;
	}

	public AbstractMap<Integer, ForStorageBuilding> getStorageBuildings() {
		return storageBuildings;
	}

	public AbstractMap<Integer, ForFighter> getPatronFighters() {
		return patronFighters;
	}


	public AbstractMap<Integer, ForWorker> getPatronWorkers() {
		return patronWorkers;
	}


	public AbstractMap<Integer, ForUpgrade> getHQUpgrades() {
		return HQUpgrades;
	}

	public AbstractMap<Integer, ForUpgrade> getForgeUpgrades() {
		return ForgeUpgrades;
	}

	public AbstractMap<Integer, ForPopulationBuilding> getPopulationBuildings() {
		return populationBuildings;
	}
}
