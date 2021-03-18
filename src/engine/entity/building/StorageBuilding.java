package engine.entity.building;

import java.awt.image.BufferedImage;

import engine.Entity;
import engine.Position;
import engine.map.Tile;
/**
 * 
 * @author maxime
 *
 */
public class StorageBuilding extends Entity{
	
	private Tile tile;
	
	public StorageBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile, BufferedImage texture, int sightRange) {
		super(100, hpMax, description, position, id, faction, texture, sightRange);
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
