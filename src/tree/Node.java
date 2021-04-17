package tree;

public class Node 
{
	private float countHere;
	private float countHereToDestination;
	private float countPath;
	private Node parent;
	
	public Node(Node parent)
	{
		this.parent = parent;
		this.countHere = calculateCountHere();
	}
	
	public float calculateCountHere()
	{
		float count = this.parent.getCountHere();
		
		//Math.sqrt(Math.pow(position.getX() - this.getPosition().getX(), 2) + Math.pow(position.getY() - this.getPosition().getY(), 2));
		return 1;
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

}
