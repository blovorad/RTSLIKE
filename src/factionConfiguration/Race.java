package factionConfiguration;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Race 
{
	private ForUnit cavalry;
	private ForUnit infantry;
	private ForUnit archer;
	private ForUnit special;
	private ForUnit worker;
	private String name;
	private AbstractMap<Integer, ForBuilding>buildings;
	private List<ForUpgrade> upgrades;
	
	public Race()
	{
		this.buildings = new HashMap<Integer, ForBuilding>();
		this.upgrades = new ArrayList<ForUpgrade>();
	}
	
	public ForUnit getCavalry() 
	{
		return cavalry;
	}
	
	public void setCavalry(ForUnit cavalry) 
	{
		this.cavalry = cavalry;
	}
	
	public ForUnit getInfantry() 
	{
		return infantry;
	}
	
	public void setInfantry(ForUnit infantry) 
	{
		this.infantry = infantry;
	}
	
	public ForUnit getArcher() 
	{
		return archer;
	}
	
	public void setArcher(ForUnit archer) 
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

	public ForUnit getSpecial() {
		return special;
	}

	public void setSpecial(ForUnit special) {
		this.special = special;
	}

	public List<ForUpgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(List<ForUpgrade> upgrades) {
		this.upgrades = upgrades;
	}

	public ForUnit getWorker() {
		return worker;
	}

	public void setWorker(ForUnit worker) {
		this.worker = worker;
	}
}
