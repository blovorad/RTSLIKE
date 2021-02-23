package engine;

public class RectangleMy 
{
	private int x;
	private int y;
	private int w;
	private int h;
	private boolean active;

	public RectangleMy()
	{
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
		this.active = false;
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
}
