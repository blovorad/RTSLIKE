package engine.entity.building;

import engine.Entity;
import engine.Position;

public class StorageBuilding extends Entity{
	
	public StorageBuilding(Position position, int id, String description, int hpMax) {
		super(100, hpMax, description , position, id);
	}
}
