package engine;

import configuration.EntityConfiguration;
import engine.manager.GraphicsManager;
import engine.map.Tile;
/**
 * 
 * @author gautier
 *
 */
public class Ressource extends Entity
{
	private Tile tileAttach;
	
	public Ressource(int hp, String description, Position position, Tile tile, int faction, GraphicsManager graphicsManager)
	{
		super(3, 3, description, position, EntityConfiguration.RESSOURCE, faction, 0, 0, graphicsManager);
		tileAttach = tile;
	}
	
	public void update() {
		super.update();
	}
	
	public void remove() {
		this.setSelected(false);
		this.getTileAttach().setSolid(false);
	}

	public Tile getTileAttach() {
		return tileAttach;
	}

	public void setTileAttach(Tile tileAttach) {
		this.tileAttach = tileAttach;
	}
}
