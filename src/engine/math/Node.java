package engine.math;

import engine.Position;

/**
 * 
 * @author gautier
 * this class represent a node, use for A* algorithm
 */

public class Node {
	
	/**
	 * parent of the node
	 */
	private Node parent;
	/**
	 * cost of moving
	 */
	private int g;
	/**
	 * distance to final Node
	 */
	private int h;
	/**
	 * g + h
	 */
	private int f;
	/**
	 * pos in term of position in tab of map
	 */
	private Position p;
	
	/**
	 * constructor
	 * @param p position in tab of map
	 */
	public Node(Position p) {
		this.p = p;
		this.g = 0;
		this.h = 0;
		this.f = 0;
		this.parent = null;
	}
	
	/**
	 * this method calculate a cost of a node
	 * @param parent, his parent
	 * @param p2, final destination pos
	 */
	public void calculateCost(Node parent, Position p2) {
		//System.out.println("parent G : " + parent.getG());
		g = parent.getG() + 10;
		//System.out.println("G : " + this.g);
		h = Math.abs(p.getX() - p2.getX()) + Math.abs(p.getY() - p2.getY()) * 10;
		f = g + h;
		//System.out.println("LE F :" + f);
		this.parent = parent;
	}
	
	/**
	 * this method recalculate cost to see if a parent of current node is a better way
	 * @param parent
	 * @param p2
	 * @return true if it is a better way
	 */
	public boolean recalculateF(Node parent, Position p2) {
		//System.out.println("Recalculate");
		int gBis = this.g;
		int hBis = this.h;
		int fBis = this.f;
		Node parentBis = this.parent;
		Position pBis = this.p;
		
		calculateCost(parent, p2);
		//System.out.println("ANCIEN G : " + gBis + " et nouveau g : " + g);
		if(gBis < this.g) {
			this.g = gBis;
			this.h = hBis;
			this.f = fBis;
			this.parent = parentBis;
			this.p = pBis;
			return true;
		}
		
		return false;
	}
	
	public void setParent(Node n) {
		this.parent = n;
	}
	
	public Node getParent() {
		return this.parent;
	}
	
	public int getG() {
		return this.g;
	}
	
	public int getF() {
		return this.f;
	}
	
	public int getH() {
		return this.h;
	}
	
	public Position getPosition() {
		return this.p;
	}
}
