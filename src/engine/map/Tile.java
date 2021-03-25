package engine.map;

import java.awt.Color;

import configuration.MapConfiguration;
import engine.Animation;
import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 *
 */

public class Tile {
	private int line;
	private int column;
	private int id;
	private boolean solid;
	private Color color;
	private Animation animation;
	
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
			animation = new Animation(0, false, graphicsManager.getWater());
		}
		else if(this.id == MapConfiguration.WOOD) {
			animation = new Animation(0, false, graphicsManager.getTree());
		}
		else if(this.id == MapConfiguration.GOLD) {
			animation = new Animation(0, false, graphicsManager.getGrass());
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
