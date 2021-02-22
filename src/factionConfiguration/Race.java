package factionConfiguration;

import java.util.AbstractMap;

public abstract class Race 
{
	private ForUnit cavalry;
	private ForUnit infantry;
	private ForUnit archer;
	private String name;
	private AbstractMap<String, ForBuilding>buildings;
	
	
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
	
	public AbstractMap<String, ForBuilding> getBuildings() 
	{
		return buildings;
	}
	
	public void setBuildings(AbstractMap<String, ForBuilding> buildings) 
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
}
