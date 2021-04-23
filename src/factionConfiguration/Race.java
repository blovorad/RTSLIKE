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
		this.setHQUpgrades(new HashMap<Integer, ForUpgrade>());
		this.setForgeUpgrades(new HashMap<Integer, ForUpgrade>());
		
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

	public void setProductionBuildings(AbstractMap<Integer, ForProductionBuilding> productionBuildings) {
		this.productionBuildings = productionBuildings;
	}

	public AbstractMap<Integer, ForAttackBuilding> getAttackBuildings() {
		return attackBuildings;
	}

	public void setAttackBuildings(AbstractMap<Integer, ForAttackBuilding> attackBuildings) {
		this.attackBuildings = attackBuildings;
	}

	public AbstractMap<Integer, ForStorageBuilding> getStorageBuildings() {
		return storageBuildings;
	}

	public void setStorageBuildings(AbstractMap<Integer, ForStorageBuilding> storageBuildings) {
		this.storageBuildings = storageBuildings;
	}

	public AbstractMap<Integer, ForFighter> getPatronFighters() {
		return patronFighters;
	}

	public void setPatronFighters(AbstractMap<Integer, ForFighter> patronFighters) {
		this.patronFighters = patronFighters;
	}

	public AbstractMap<Integer, ForWorker> getPatronWorkers() {
		return patronWorkers;
	}

	public void setPatronWorkers(AbstractMap<Integer, ForWorker> patronWorkers) {
		this.patronWorkers = patronWorkers;
	}

	public AbstractMap<Integer, ForUpgrade> getHQUpgrades() {
		return HQUpgrades;
	}

	public void setHQUpgrades(AbstractMap<Integer, ForUpgrade> hQUpgrades) {
		HQUpgrades = hQUpgrades;
	}

	public AbstractMap<Integer, ForUpgrade> getForgeUpgrades() {
		return ForgeUpgrades;
	}

	public void setForgeUpgrades(AbstractMap<Integer, ForUpgrade> forgeUpgrades) {
		ForgeUpgrades = forgeUpgrades;
	}
}
