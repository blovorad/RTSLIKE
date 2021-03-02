package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		//ForBuilding b = new ForBuilding(faction.getRace().getBuildings().get(key));
		/*Random rand = new Random();
		Building b = new Stable(new Position(rand.nextInt(400), rand.nextInt(400)));
		faction.createBuilding(b);*/
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
