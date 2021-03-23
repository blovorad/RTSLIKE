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
	private int ressourceStock;
	
	public StorageBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile, BufferedImage texture, int sightRange) 
	{
		super(50, hpMax, description, position, id, faction, texture, sightRange);
		this.setTile(tile);
	}
	
	public void addRessource(int ressource) {
		setRessourceStock(getRessourceStock() + ressource);
	}
	
	public int takeRessourceStock() {
		int ressource = ressourceStock;
		ressourceStock = 0;
		
		return ressource;
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

	public int getRessourceStock() {
		return ressourceStock;
	}

	public void setRessourceStock(int ressourceStock) {
		this.ressourceStock = ressourceStock;
	}
}
