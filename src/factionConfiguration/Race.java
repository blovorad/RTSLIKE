package factionConfiguration;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Race 
{
	private ForFighter cavalry;
	private ForFighter infantry;
	private ForFighter archer;
	private ForFighter special;
	private ForWorker worker;
	private String name;
	private AbstractMap<Integer, ForBuilding>buildings;
	private AbstractMap<Integer, ForProductionBuilding>productionBuildings;
	private AbstractMap<Integer, ForAttackBuilding>attackBuildings;
	private List<ForUpgrade> upgrades;
	
	public Race()
	{
		this.buildings = new HashMap<Integer, ForBuilding>();
		this.attackBuildings = new HashMap<Integer, ForAttackBuilding>();
		this.productionBuildings = new HashMap<Integer, ForProductionBuilding>();
		this.upgrades = new ArrayList<ForUpgrade>();
	}
	
	public ForFighter getCavalry() 
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
	}
	
	public AbstractMap<Integer, ForBuilding> getBuildings() 
	{
		return buildings;
	}
	
	public void setBuildings(AbstractMap<Integer, ForBuilding> buildings) 
	{
		this.buildings = buildings;
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

	public ForFighter getSpecial() {
		return special;
	}

	public void setSpecial(ForFighter special) {
		this.special = special;
	}

	public List<ForUpgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(List<ForUpgrade> upgrades) {
		this.upgrades = upgrades;
	}

	public ForWorker getWorker() {
		return worker;
	}

	public void setWorker(ForWorker worker) {
		this.worker = worker;
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
}
