package tree;

import java.util.ArrayList;

public class Tas 
{
	private ArrayList<Node> element;
	
	public Tas(ArrayList<Node> element)
	{
		this.element = element;
	}
	
	public ArrayList<Node> getElement()
	{
		return this.element;
	}
	
	public void setElement(ArrayList<Node> element)
	{
		this.element = element;
	}
	
	public int indexParent(int noeud)
	{
		return (noeud -1)/2;
	}
	
	public int indexSonLeft(int node)
	{
		return (2*node)+1;
	}
	
	public int indexSonRight(int node)
	{
		return (2*node) + 2;
	}
	
	public void addElement(Node newElement)
	{
		this.element.add(newElement);
		
		float elementParent = this.element.get(element.size() - 1).getCountHere();
		int index = element.size() - 1;
		int indexParent = this.indexParent(index);
		
		while(elementParent > newElement.getCountHere())
		{
			elementParent = this.element.get(index).getCountHere();
			indexParent = this.indexParent(index);
			
		/*	if(1)
			{
				Node tmp = element.get(index);
				
				index = indexParent;
			}*/
		}
	}
	
	public boolean inTheList(Node node)
	{
		if(this.element.contains(node))
		{
			return true;
		}
		return false;
	}
	
}
