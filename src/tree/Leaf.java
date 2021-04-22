package tree;
import engine.Position;

public class Leaf 
{
	Position position;
	boolean solid;
	
	public Leaf(Position position, boolean solid)
	{
		this.position = position;
		this.solid = solid;
	}
	
	Position getPosition() 
	{
		return this.position;
	}
	
	boolean getSolid()
	{
		return this.solid;
	}
	
	public String toString()
	{
		return this.position.toString() + this.solid;
	}
	
	
}
