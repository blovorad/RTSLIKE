package engine;

import java.util.List;

public class EntitiesManager 
{
	private Faction faction;
	private List<Entity> entities;
	private List<Unit> selectedUnits;
	
	public EntitiesManager() 
	{
		
	}
	
	public void update() 
	{
		
	}

	public Faction getFaction() 
	{
		return faction;
	}

	public void setFaction(Faction faction) 
	{
		this.faction = faction;
	}

	public List<Entity> getEntities() 
	{
		return entities;
	}

	public void setEntities(List<Entity> entities) 
	{
		this.entities = entities;
	}

	public List<Unit> getSelectedUnits() 
	{
		return selectedUnits;
	}

	public void setSelectedUnits(List<Unit> selectedUnits) 
	{
		this.selectedUnits = selectedUnits;
	}
}
