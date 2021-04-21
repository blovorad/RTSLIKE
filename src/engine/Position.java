package engine;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;

/**
 * 
 * @author gautier
 *	this class is require to stock position of entity on map
 */
public class Position 
{
	/**
	 * the current x on screen
	 */
	private int x;
	/**
	 * the current y on screen
	 */
	private int y;
	
	/**
	 * constructor
	 * @param x x of entity
	 * @param y y of entity
	 */
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
	
	/**
	 * override of equals to be match with another position
	 * @param position position to see if equals
	 * @return true if pos is equals
	 */
	public boolean equals(Position position)
	{
		if(this.x == position.getX() && this.y == position.getY())
		{
			
			return true;
		}
		//System.out.println("equals position : " + this.x + "," + this.y + " alors :: " + position.getX() + "," + position.getY());
	return false;
	}
}
