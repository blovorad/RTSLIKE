package engine.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import engine.Faction;
import factionConfiguration.ForBuilding;

public class FactionManager 
{
	private List<Faction> factions;
	
	public FactionManager() 
	{
		factions = new ArrayList<Faction>();
	}

	public List<Faction> getFactions() 
	{
		return factions;
	}

	public void setFactions(List<Faction> factions) 
	{
		this.factions = factions;
	}
	
	public void addFaction(Faction faction)
	{
		factions.add(faction);
	}

	public void clean() 
	{
		factions.clear();
	}
}
