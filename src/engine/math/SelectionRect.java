package engine.math;

import engine.Position;
/**
 * 
 * this class is used to manage the selection rect with mouse and rally point with building
 * @author gautier
 */
public class SelectionRect 
{
	private int x;
	private int y;
	private int w;
	private int h;
	private int alpha;
	private Position firstPosition;
	/**
	 * if selection rect is active, if a click is pressed
	 */
	private boolean active;

	/**
	 * constructor
	 */
	public SelectionRect()
	{
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
		this.firstPosition = new Position(0, 0);
		this.alpha = 0;
		this.active = false;
	}
	
	/**
	 * used by the rallyPoint of building
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public SelectionRect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.firstPosition = new Position(x, y);
		this.active = false;
	}
	
	/**
	 * core method of class that manage the width and height of the selection rect depending of mouse move
	 * @param x pos of mouse
	 * @param y pos of mouse
	 */
	public void moveSelectionRect(int x, int y) {
		
		if(x > this.firstPosition.getX()) {
			this.w = x - this.x;
		}
		else {
			this.w = this.firstPosition.getX() - x;
			this.x = x;
		}
		
		if(y > this.firstPosition.getY()) {
			this.h = y - this.y;
		}
		else {
			this.h = this.firstPosition.getY() - y;
			this.y = y;
		}
	}
	
	/**
	 * if we need to change the alpha of the rect represent
	 */
	public void updateAlpha() {
		alpha += 5;
		if(alpha > 255) {
			alpha = 0;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		this.firstPosition.setX(x);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.firstPosition.setY(y);
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getAlpha() {
		return alpha;
	}
}