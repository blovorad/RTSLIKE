package engine.map;

import java.awt.Color;

import configuration.MapConfiguration;
import engine.Animation;
import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 *	this class represent a case of a map
 */

public class Tile {
	/**
	 * pos Y
	 */
	private int line;
	/**
	 * pos Y
	 */
	private int column;
	/**
	 * id of tile
	 */
	private int id;
	/**
	 * if tile is solid
	 */
	private boolean solid;
	/**
	 * color of the tile
	 */
	private Color color;
	/**
	 * animation of the tile
	 */
	private Animation animation;
	
	/**
	 * constructor 
	 * @param line place Y
	 * @param column place X
	 * @param id of tile
	 * @param graphicsManager to get the correct bufferedImage
	 */
	public Tile(int line, int column, int id, GraphicsManager graphicsManager){
		this.line = line;
		this.column = column;
		this.id = id;
		this.solid = MapConfiguration.getTileSolid(this.id);
		this.setColor(MapConfiguration.getTileColor(this.id));
		
		if(this.id == MapConfiguration.GRASS) {
			animation = new Animation(0, false, graphicsManager.getGrass());
		}
		else if(this.id == MapConfiguration.ROCK) {
			animation = new Animation(0, false, graphicsManager.getRock());
		}
		else if(this.id == MapConfiguration.WATER) {
			animation = new Animation(0, false, graphicsManager.getWater(id));
		}
		else if(this.id >= MapConfiguration.WATER_BORD_UP && this.id <= MapConfiguration.WATER_TURN_DOWN_RIGHT) {
			animation = new Animation(0, false, graphicsManager.getWater(id));
		}
		else if(this.id == MapConfiguration.WOOD) {
			animation = new Animation(0, false, graphicsManager.getTree());
		}
		else if(this.id == MapConfiguration.GOLD) {
			animation = new Animation(0, false, graphicsManager.getGrass());
		}
		else if(this.id == MapConfiguration.SAND) {
			animation = new Animation(0, false, graphicsManager.getSand());
		}
	}
	
	public int getLine(){
		return this.line;
	}
	
	public int getColumn(){
		return this.column;
	}
	
	public int getId(){
		return this.id;
	}
	
	public boolean isSolid(){
		return this.solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}