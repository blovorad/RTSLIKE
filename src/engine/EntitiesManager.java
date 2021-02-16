package engine;

import java.util.ArrayList;
import java.util.List;

public class EntitiesManager 
{
	private Faction faction;
	private List<Fighter> fighters;
	private List<Gatherer> gatherers;
	private List<Building> buildings;
	private List<Ressource> ressources;
	private List<Unit> selectedUnits;
	
	public EntitiesManager(Faction faction) 
	{
		this.setFaction(faction);
		this.fighters = new ArrayList<Fighter>();
		this.gatherers = new ArrayList<Gatherer>();
		this.buildings = new ArrayList<Building>();
		this.ressources = new ArrayList<Ressource>();
		this.setSelectedUnits(new ArrayList<Unit>());
	}
	
	public void update() 
	{
		for(Fighter fighter : fighters) 
		{
			fighter.update();
		}
		
		for(Gatherer gatherer : gatherers) 
		{
			gatherer.update();
		}
		
		for(Building building : buildings) 
		{
			building.update();
		}
		
		for(Ressource ressource : ressources) 
		{
			ressource.update();
			if(ressource.getHp() < 1)
			{
				
			}
		}
	}

	public Faction getFaction() {
		return faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public List<Unit> getSelectedUnits() {
		return selectedUnits;
	}

	public void setSelectedUnits(List<Unit> selectedUnits) {
		this.selectedUnits = selectedUnits;
	}
}
