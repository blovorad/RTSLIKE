package engine.entity.building;

import engine.Entity;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;

/**
 * 
 * @author gautier
 *
 */
public class SiteConstruction extends Entity{
	private Tile tile;
	private int buildingId;
	
	public SiteConstruction(int buildingId, int hp, int hpMax, String description, Position position, int id, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager, Tile tile) {
		super(hp, hpMax, description, position, id, faction, sightRange, maxFrame, graphicsManager);
		
		this.buildingId = buildingId;
		this.tile = tile;
	}
	
	public void update() {
		super.update();
		if(this.getHp() >= this.getHpMax()) {
			this.setSelected(false);
			this.setRemove(true);
		}
	}
	
	public int getBuildingId() {
		return buildingId;
	}
	
	public Tile getTile() {
		return this.tile;
	}
}
