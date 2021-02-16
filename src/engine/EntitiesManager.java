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
			if(fighter.getHp() < 1)
			{
				fighters.remove(fighter);
			}
		}
		
		for(Gatherer gatherer : gatherers) 
		{
			gatherer.update();
			if(gatherer.getHp() < 1)
			{
				gatherers.remove(gatherer);
			}
		}
		
		for(Building building : buildings) 
		{
			building.update();
			if(building.getHp() < 1)
			{
				buildings.remove(building);
			}
		}
		
		for(Ressource ressource : ressources) 
		{
			ressource.update();
			if(ressource.getHp() < 1)
			{
				ressources.remove(ressource);
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
