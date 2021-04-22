package tree;

import engine.Position;
public class Node 
{
	private float countHereToDestination;
	private float countHere;
	private float countPath;
	
	private Position position;
	
	private Node parent;
	
	
	public Node(Node parent, Position position)
	{
		this.parent = parent;
		this.countHere = calculateCountHere();
		this.position = position;
	}
	
	public float calculateCountHere()
	{
		float countParent = this.parent.getCountHere();
		float count =(float) Math.sqrt(Math.pow(position.getX() - this.getPosition().getX(), 2) + Math.pow(position.getY() - this.getPosition().getY(), 2));
		
		return (float) count + countParent;
	}
	
	public float getCountHere()
	{
		return this.countHere;
	}
	
	public void setCountHere(float countHere)
	{
		this.countHere = countHere;
	}
	
	public float getCountHereToDestination()
	{
		return this.countHereToDestination;
	}
	
	public void setCountHereToDestination(float countHereToDestination)
	{
		this.countHereToDestination = countHereToDestination;
	}
	
	public float getCountPath()
	{
		return this.countPath;
	}
	
	public void setCountPath(float countPath)
	{
		this.countPath = countPath;
	}
	
	public Node getParent()
	{
		return this.parent;
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public Position getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Position position)
	{
		this.position = position;
	}

}
