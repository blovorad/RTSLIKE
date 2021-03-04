package factionConfiguration;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Race 
{
	/*private ForFighter cavalry;
	private ForFighter infantry;
	private ForFighter archer;
	private ForFighter special;
	private ForWorker worker;*/
	private String name;
	private AbstractMap<Integer, ForFighter>patronFighters;
	private AbstractMap<Integer, ForWorker>patronWorkers;
	private AbstractMap<Integer, ForProductionBuilding>productionBuildings;
	private AbstractMap<Integer, ForAttackBuilding>attackBuildings;
	private AbstractMap<Integer, ForStorageBuilding>storageBuildings;
	private List<ForUpgrade> upgrades;
	
	public Race()
	{
		this.patronFighters = new HashMap<Integer, ForFighter>();
		this.patronWorkers = new HashMap<Integer, ForWorker>();
		this.storageBuildings = new HashMap<Integer, ForStorageBuilding>();
		this.attackBuildings = new HashMap<Integer, ForAttackBuilding>();
		this.productionBuildings = new HashMap<Integer, ForProductionBuilding>();
		this.upgrades = new ArrayList<ForUpgrade>();
	}
	
	/*public ForFighter getCavalry() 
	{
		return cavalry;
	}
	
	public void setCavalry(ForFighter cavalry) 
	{
		this.cavalry = cavalry;
	}
	
	public ForFighter getInfantry() 
	{
		return infantry;
	}
	
	public void setInfantry(ForFighter infantry) 
	{
		this.infantry = infantry;
	}
	
	public ForFighter getArcher() 
	{
		return archer;
	}
	
	public void setArcher(ForFighter archer) 
	{
		this.archer = archer;
	}*/
	
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

	/*public ForFighter getSpecial() {
		return special;
	}

	public void setSpecial(ForFighter special) {
		this.special = special;
	}*/

	public List<ForUpgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(List<ForUpgrade> upgrades) {
		this.upgrades = upgrades;
	}

	/*public ForWorker getWorker() {
		return worker;
	}

	public void setWorker(ForWorker worker) {
		this.worker = worker;
	}*/

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
}
