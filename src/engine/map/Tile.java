package engine.map;

import java.awt.Color;

import configuration.MapConfiguration;

public class Tile 
{
	private int line;
	private int column;
	private int id;
	private boolean solid;
	private Color color;
	
	public Tile(int line, int column, int id)
	{
		this.line = line;
		this.column = column;
		this.id = id;
		this.solid = MapConfiguration.getTileSolid(this.id);
		this.setColor(MapConfiguration.getTileColor(this.id));
	}
	
	public int getLine()
	{
		return this.line;
	}
	
	public int getColumn()
	{
		return this.column;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public boolean isSolid()
	{
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
}
