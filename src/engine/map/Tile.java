package engine.map;

import java.awt.Color;
import java.awt.image.BufferedImage;

import configuration.MapConfiguration;

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
	private BufferedImage texture;
	
	public Tile(int line, int column, int id, BufferedImage texture){
		this.line = line;
		this.column = column;
		this.id = id;
		this.setTexture(texture);
		this.solid = MapConfiguration.getTileSolid(this.id);
		this.setColor(MapConfiguration.getTileColor(this.id));
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

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
}
