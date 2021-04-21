package engine.math;

import engine.Position;

/**
 * 
 * @author gautier
 *
 */

public class Node {
	
	private Node parent;
	private int g;
	private int h;
	private int f;
	private Position p;
		
	public Node(Position p) {
		this.p = p;
		this.g = 0;
		this.h = 0;
		this.f = 0;
		this.parent = null;
	}
	
	public void calculateCost(Node parent, Position p2) {
		//System.out.println("parent G : " + parent.getG());
		g = parent.getG() + 10;
		//System.out.println("G : " + this.g);
		h = Math.abs(p.getX() - p2.getX()) + Math.abs(p.getY() - p2.getY()) * 10;
		f = g + h;
		//System.out.println("LE F :" + f);
		this.parent = parent;
	}
	
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
