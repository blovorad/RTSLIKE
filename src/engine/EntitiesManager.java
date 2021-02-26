package engine;

import java.util.ArrayList;
import java.util.List;

import configuration.MapConfiguration;

public class EntitiesManager 
{
	private Faction faction;
	/*private List<Fighter> fighters;
	private List<Fighter> removeFighters = new ArrayList<Fighter>();
	
	private List<Worker> workers;
	private List<Worker> removeWorkers = new ArrayList<Worker>();*/
	
	private List<Unit> units;
	private List<Unit> removeUnits = new ArrayList<Unit>();
	
	private List<Building> buildings;
	private List<Building> removeBuildings = new ArrayList<Building>();
	
	private List<Ressource> ressources;
	private List<Ressource> removeRessources = new ArrayList<Ressource>();
	
	private List<Unit> selectedUnits;
	private List<Building> selectedBuildings;
	
	public EntitiesManager(Faction faction) 
	{
		this.setFaction(faction);
		/*this.fighters = new ArrayList<Fighter>();
		this.workers = new ArrayList<Worker>();*/
		this.units = new ArrayList<Unit>();
		this.buildings = new ArrayList<Building>();
		this.ressources = new ArrayList<Ressource>();
		this.selectedUnits = new ArrayList<Unit>();
		this.selectedBuildings = new ArrayList<Building>();
	}
	
	public void update() 
	{
		/*for(Fighter fighter : fighters) 
		{
			fighter.update();
			if(fighter.getHp() < 1)
			{
				removeFighters.add(fighter);
			}
		}
		
		//removing fighter
		fighters.removeAll(removeFighters);
		removeFighters.clear();
		
		for(Worker worker : workers) 
		{
			worker.update();
			if(worker.getHp() < 1)
			{
				removeWorkers.add(worker);
			}
		}
		
		//removing worker
		workers.removeAll(removeWorkers);
		removeWorkers.clear();*/
		
		for(Unit unit : units)
		{
			unit.update();
			
			if(unit.getHp() < 1)
			{
				removeUnits.add(unit);
			}
		}
		
		//removing units
		units.removeAll(removeUnits);
		removeUnits.clear();
		
		for(Building building : buildings) 
		{
			building.update();
			if(building.getIsProducing())
			{
				if(building.getTimer() <= 0)
				{
					units.add(building.produce());
					System.out.println("producing unit");
				}
			}
			if(building.getHp() < 1)
			{
				//System.out.println("We removing a building cause : " + building.getHp());
				removeBuildings.add(building);
			}
		}
		
		//removing building
		buildings.removeAll(removeBuildings);
		removeBuildings.clear();
		
		for(Ressource ressource : ressources) 
		{
			//ressource.update();
			if(ressource.getHp() < 1)
			{
				ressource.getTileAttach().setSolid(false);
				removeRessources.add(ressource);
			}
		}
		
		//removing ressource
		ressources.removeAll(removeRessources);
		removeRessources.clear();
		
	}
	
	public void selectBuilding(Building building)
	{
		selectedBuildings.add(building);
	}
	
	public void clearSelectedUnits()
	{
		selectedUnits.clear();
	}
	
	public void clearSelectedBuildings()
	{
		selectedBuildings.clear();
	}
	
	public void addBuilding(Building building)
	{
		this.buildings.add(building); 
	}
	
	public void addSelectedUnit(Unit unit)
	{
		this.selectedUnits.add(unit);
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

	public List<Building> getSelectedBuildings() {
		return selectedBuildings;
	}

	public void setSelectedBuildings(List<Building> selectedBuildings) {
		this.selectedBuildings = selectedBuildings;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}
}
