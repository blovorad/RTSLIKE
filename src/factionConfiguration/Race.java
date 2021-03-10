package factionConfiguration;

import java.util.AbstractMap;
import java.util.HashMap;

public abstract class Race 
{
	private String name;
	private AbstractMap<Integer, ForFighter>patronFighters;
	private AbstractMap<Integer, ForWorker>patronWorkers;
	private AbstractMap<Integer, ForProductionBuilding>productionBuildings;
	private AbstractMap<Integer, ForAttackBuilding>attackBuildings;
	private AbstractMap<Integer, ForStorageBuilding>storageBuildings;
	private AbstractMap<Integer, ForUpgrade> HQUpgrades;
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
