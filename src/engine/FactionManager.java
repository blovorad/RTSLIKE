package engine;

import java.util.ArrayList;
import java.util.List;

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
	
	public void update() 
	{
		for(Faction faction : factions) 
		{
			faction.update();
		}
	}

	public void cleanFactionManager() 
	{
		factions.clear();
	}
}
