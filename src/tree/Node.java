package tree;

public class Node 
{
	private Leaf leftLeaf;
	private Leaf rightLeaf;
	
	public Node(Leaf leftLeaf, Leaf rightLeaf)
	{
		this.leftLeaf = leftLeaf;
		this.rightLeaf = rightLeaf;
	}
	
	public Leaf getLeftLeaf()
	{
		return this.leftLeaf;
	}
	
	public void setLeftLeaf(Leaf leftLeaf)
	{
		this.leftLeaf = leftLeaf;
	}
	
	public Leaf getRightLeaf()
	{
		return this.rightLeaf;
	}
	
	public void setRightLeaf(Leaf rightLeaf)
	{
		this.rightLeaf = rightLeaf;
	}
}
