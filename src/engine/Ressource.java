package engine;

import configuration.EntityConfiguration;
import engine.manager.GraphicsManager;
import engine.map.Tile;
/**
 * 
 * @author gautier
 * class who manage ressource on map
 */
public class Ressource extends Entity
{
	/**
	 * on which tile the ressource is
	 */
	private Tile tileAttach;
	
	/**
	 * constructor of ressource
	 * @param hp hp of the ressource
	 * @param description description of the ressource
	 * @param position position of the ressource
	 * @param tile tile who attach with ressource
	 * @param faction faction of the ressource, gaia
	 * @param graphicsManager to get the bufferedImage of the ressource
	 */
	public Ressource(int hp, String description, Position position, Tile tile, int faction, GraphicsManager graphicsManager)
	{
		super(EntityConfiguration.RESSOURCE_HP, EntityConfiguration.RESSOURCE_HP, description, position, EntityConfiguration.RESSOURCE, faction, 0, 0, graphicsManager);
		tileAttach = tile;
	}
	
	/**
	 * calling update of entity
	 */
	public void update() {
		super.update();
	}
	
	/**
	 * if we need to remove it, setSolid of tileAttach at false
	 */
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