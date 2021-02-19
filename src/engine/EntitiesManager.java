package engine;

import java.util.ArrayList;
import java.util.List;

public class EntitiesManager 
{
	private Faction faction;
	private List<Fighter> fighters;
	
	private List<Worker> gatherers;
	
	private List<Building> buildings;
	private List<Building> removeBuilding = new ArrayList<Building>();
	
	private List<Ressource> ressources;
	
	private List<Unit> selectedUnits;
	
	public EntitiesManager(Faction faction) 
	{
		this.setFaction(faction);
		this.fighters = new ArrayList<Fighter>();
		this.gatherers = new ArrayList<Worker>();
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
		
		for(Worker gatherer : gatherers) 
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
			if(building.getIsProducing())
			{
				if(building.getTimer() <= 0)
				{
					building.produce();
				}
			}
			if(building.getHp() < 1)
			{
				//System.out.println("We removing a building cause : " + building.getHp());
				removeBuilding.add(building);
			}
		}
		
		//removing building
		buildings.removeAll(removeBuilding);
		removeBuilding.clear();
		
		for(Ressource ressource : ressources) 
		{
			ressource.update();
			if(ressource.getHp() < 1)
			{
				ressources.remove(ressource);
			}
		}
	}
	
	public void addBuilding(Building building)
	{
		this.buildings.add(building); 
	}
	
	public List<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(List<Fighter> fighters) {
		this.fighters = fighters;
	}

	public List<Worker> getGatherers() {
		return gatherers;
	}

	public void setGatherers(List<Worker> gatherers) {
		this.gatherers = gatherers;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	public List<Ressource> getRessources() {
		return ressources;
	}

	public void setRessources(List<Ressource> ressources) {
		this.ressources = ressources;
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
