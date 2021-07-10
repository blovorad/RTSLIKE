package engine.entity.building;

import engine.Entity;
import engine.Position;
import engine.manager.GraphicsManager;

public abstract class Building extends Entity{
	
	public Building(int hp, int hpMax, String description, Position position, int id, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager) {
		super(hp, hpMax, description , position, id, faction, sightRange, maxFrame, graphicsManager);
	}
}
