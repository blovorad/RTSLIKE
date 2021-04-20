package engine.math;

import engine.Position;

public class Node {
	
	private Node parent;
	private double g;
	private double h;
	private double f;
	private Position p;
		
	public Node(Position p) {
		this.p = p;
		this.g = 0f;
		this.h = 0f;
		this.f = 0f;
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
		System.out.println("Recalculate");
		double gBis = this.g;
		double hBis = this.h;
		double fBis = this.f;
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
	
	public double getG() {
		return this.g;
	}
	
	public double getF() {
		return this.f;
	}
	
	public double getH() {
		return this.h;
	}
	
	public Position getPosition() {
		return this.p;
	}
}
