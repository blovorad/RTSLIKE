package engine;
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
	
	public boolean equalsX(Position position)
	{
		if( this.x == position.x )
		{
			return true;
		}
	return false;
	}
	
	public boolean equalsY(Position position)
	{
		if(this.y == position.y)
		{
			return true;
		}
	return false;
	}
	
	public boolean equals(Position position)
	{
		if(this.x == position.x && this.y == position.y)
		{
			return true;
		}
		
	return false;
	}
	
	
}
