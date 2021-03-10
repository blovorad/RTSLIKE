package engine.manager;

import java.util.AbstractMap;


import java.util.HashMap;

import engine.Faction;

/**
 * 
 * @author gautier
 *
 */

public class FactionManager 
{
	private AbstractMap<Integer, Faction> factions;
	
	public FactionManager() 
	{
		factions = new HashMap<Integer, Faction>();
	}

	public AbstractMap<Integer, Faction> getFactions() 
	{
		return factions;
	}

	public void setFactions(AbstractMap<Integer, Faction> factions) 
	{
		this.factions = factions;
	}
	
	public void addFaction(int id, Faction faction)
	{
		factions.put(id, faction);
	}

	public void clean() 
	{
		factions.clear();
	}
}
