package engine;


import configuration.GameConfiguration;

public class Camera 
{
	private int x;
	private int y;
	
	/*private int circleX;

	private int circleY;
	private int circleRayon;*/
	
	private Speed speed;
	
	public Camera(int width, int height)
	{
		this.x = 0;
		this.y = 0;
		this.speed = new Speed();
		
		/*if(width == 800 && height == 600)
		{
			circleRayon = 400;
		}
		else if(width == 1920 && height == 1080)
		{
			circleX = 30;
			circleY = 30;
			circleRayon = 1500;
		}*/
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
	
	/*public int getCircleX() {
		return circleX;
	}

	public void setCircleX(int circleX) {
		this.circleX = circleX;
	}

	public int getCircleY() {
		return circleY;
	}

	public void setCircleY(int circleY) {
		this.circleY = circleY;
	}

	public int getCircleRayon() {
		return circleRayon;
	}

	public void setCircleRayon(int circleRayon) {
		this.circleRayon = circleRayon;
	}*/
}
