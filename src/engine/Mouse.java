package engine;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;
import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 *	this class is require to interact with map, needing to place building
 */
public class Mouse {
	
	/**
	 * id of the building need to be construct
	 */
	private int id = -1;
	/**
	 * position of the mouse
	 */
	private Position position;
	/**
	 * if id > -1, represent the image of the building
	 */
	private BufferedImage[][] leftImages;
	
	/**
	 * constructor
	 */
	public Mouse()
	{
		setPosition(new Position(0,0));
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setX(int x) {
		position.setX(x);
	}
	
	/**
	 * same method in Animation
	 * @param graphicsManager to get animation bufferedImage
	 * @param id id to entity who need animation
	 * @param faction faction of the entity
	 */
	public void getEntityNoAnimationTexture(GraphicsManager graphicsManager, int id, int faction)
	{
		if(id == EntityConfiguration.BARRACK) {
			leftImages = graphicsManager.getBarrack(faction);
		}
		else if(id == EntityConfiguration.ARCHERY) {
			leftImages = graphicsManager.getArchery(faction);
		}
		else if(id == EntityConfiguration.STABLE) {
			leftImages = graphicsManager.getStable(faction);
		}
		else if(id == EntityConfiguration.CASTLE) {
			leftImages = graphicsManager.getCastle(faction);
		}
		else if(id == EntityConfiguration.FORGE) {
			leftImages = graphicsManager.getForge(faction);
		}
		else if(id == EntityConfiguration.HQ) {
			leftImages = graphicsManager.getHq(faction);
		}
		else if(id == EntityConfiguration.TOWER) {
			leftImages = graphicsManager.getTower(faction);
		}
		else if(id == EntityConfiguration.STORAGE) {
			leftImages = graphicsManager.getStorage(faction);
		}
	}
	
	public BufferedImage getFrame() {
		return leftImages[0][0];
	}
	
	public void setY(int y) {
		position.setY(y);
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void reset()
	{
		id = -1;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}