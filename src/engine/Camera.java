package engine;


import configuration.GameConfiguration;

public class Camera 
{
	private int x;
	private int y;
	
	private int rectX = 50;
	private int rectY = 25;
	private int rectW = 1700;
	private int rectH = 800;
	
	private Speed speed;
	
	public Camera()
	{
		this.x = 0;
		this.y = 0;
		this.speed = new Speed();
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void update()
	{
		x  += this.getSpeed().getVx();
		y  += this.getSpeed().getVy();
		if(x < 0)
		{
			x = 0;
			speed.setVx(0);
		}
		else if(x > GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - GameConfiguration.WINDOW_WIDTH)
		{
			x = GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - GameConfiguration.WINDOW_WIDTH;
			speed.setVx(0);
		}
		
		if(y < 0)
		{
			y = 0;
			speed.setVy(0);
		}
		else if(y > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - GameConfiguration.WINDOW_HEIGHT)
		{
			y = GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - GameConfiguration.WINDOW_HEIGHT;
			speed.setVy(0);
		}
		//System.out.println("X: " + x + ", Y: " + y + ", VX : " + this.getSpeed().getVx() + ", VY: " + this.getSpeed().getVy());
	}
	
	public void move(int vx, int vy)
	{
		this.getSpeed().setVx(vx);
		this.getSpeed().setVy(vy);
		//System.out.println("on move" + vx + " et " + vy);
	}

	public Speed getSpeed() 
	{
		return speed;
	}

	public void setSpeed(Speed speed) 
	{
		this.speed = speed;
	}
	
	public void reset()
	{
		x = 0;
		y = 0;
		speed.reset();
	}

	public int getRectX() {
		return rectX;
	}

	public void setRectX(int rectX) {
		this.rectX = rectX;
	}

	public int getRectY() {
		return rectY;
	}

	public void setRectY(int rectY) {
		this.rectY = rectY;
	}

	public int getRectW() {
		return rectW;
	}

	public void setRectW(int rectW) {
		this.rectW = rectW;
	}

	public int getRectH() {
		return rectH;
	}

	public void setRectH(int rectH) {
		this.rectH = rectH;
	}
}
