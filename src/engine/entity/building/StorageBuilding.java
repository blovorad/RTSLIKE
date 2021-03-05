package engine.entity.building;

import engine.Entity;
import engine.Position;
import engine.map.Tile;

public class StorageBuilding extends Entity{
	
	private Tile tile;
	
	public StorageBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile) {
		super(100, hpMax, description , position, id, faction);
		this.setTile(tile);
	}
	
	public void update()
	{
		super.update();
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
