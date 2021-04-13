package engine;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;

/**
 * 
 * @author gautier
 *
 */
public class Position 
{
	private int x;
	private int y;
	
	public Position(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public void setPosition(Position position)
	{
		this.x = position.getX();
		this.y = position.getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean allerLesBleus(Position position, int faction) {
		if(this.x == position.getX() && this.y == position.getY())
		{
			return true;
		}
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			System.out.println("equals position : " + this.x + "," + this.y + " alors :: " + position.getX() + "," + position.getY());
		}
	return false;
	}
	
	public boolean equals(Position position)
	{
		if(this.x == position.getX() && this.y == position.getY())
		{
			
			return true;
		}
		//System.out.println("equals position : " + this.x + "," + this.y + " alors :: " + position.getX() + "," + position.getY());
	return false;
	}
	
	public boolean inTile(Position unit)
	{
		if((this.x / GameConfiguration.TILE_SIZE) == (unit.getX() / GameConfiguration.TILE_SIZE) && (this.y/ GameConfiguration.TILE_SIZE) == (unit.getY() / GameConfiguration.TILE_SIZE))
		{
			return true;
		}
		return false;
	}
	
}
