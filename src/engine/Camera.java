package engine;


import java.awt.Toolkit;

import configuration.GameConfiguration;

/**
 * 
 * @author gautier
 *	this class is used to permit scrolling and select entity in the map
 */

public class Camera 
{
	/**
	 * x of the camera
	 */
	private int x;
	/**
	 * y of the camera
	 */
	private int y;
	
	/**
	 * using for collider with mousePos
	 */
	private int rectX;
	/**
	 * using for collider with mousePos
	 */
	private int rectY;
	/**
	 * using for collider with mousePos
	 */
	private int rectW;
	/**
	 * using for collider with mousePos
	 */
	private int rectH;
	
	/**
	 * speed of the scrolling
	 */
	private Speed speed;
	/**
	 * dim of the screen width for better acces
	 */
	private int screenWidth;
	/**
	 * dim of the screen height for better acces
	 */
	private int screenHeight;
	
	/**
	 *	constructor of the camera 
	 * @param width width of the game screen
	 * @param height height of the game screen
	 */
	public Camera(int width, int height)
	{
		this.x = 0;
		this.y = 0;
		this.speed = new Speed();
		rectX = 10;
		rectY = 10;
		if(GameConfiguration.launchInFullScreen) {
			rectW = Toolkit.getDefaultToolkit().getScreenSize().width - rectX - 20;
			rectH = Toolkit.getDefaultToolkit().getScreenSize().height - rectY - 40;
			this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		else {
			rectW = width - rectX - 20;
			rectH = height - rectY - 40;
			this.screenWidth = width;
			this.screenHeight = height;
		}
	}
	
	/**
	 * get of x
	 * @return int to know x position of the camera
	 */
	public int getX()
	{
		return this.x;
	}
	
	/**
	 * get of y
	 * @return int to know y position of the camera
	 */
	public int getY()
	{
		return this.y;
	}
	
	/**
	 * core function who move the camera, if we have a speed of course
	 */
	public void update()
	{
		x  += this.getSpeed().getVx();
		y  += this.getSpeed().getVy();
		if(x < 0)
		{
			x = 0;
			speed.setVx(0);
		}
		else if(x > GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - screenWidth)
		{
			x = GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - screenWidth;
			speed.setVx(0);
		}
		
		if(y < 0)
		{
			y = 0;
			speed.setVy(0);
		}
		else if(y > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - screenHeight)
		{
			y = GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - screenHeight;
			speed.setVy(0);
		}
	}
	
	/**
	 * using to set the camera speed
	 * @param vx velocity x of camera
	 * @param vy velocity y of camera
	 */
	public void move(float vx, float vy)
	{
		this.getSpeed().setVx(vx);
		this.getSpeed().setVy(vy);
	}

	public Speed getSpeed() 
	{
		return speed;
	}

	public void setSpeed(Speed speed) 
	{
		this.speed = speed;
	}
	
	/**
	 * reset the camera pos, and the speed
	 */
	public void reset()
	{
		x = 0;
		y = 0;
		speed.reset();
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
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
	
	public int getScreenWidth() {
		return this.screenWidth;
	}
	
	public int getScreenHeight() {
		return this.screenHeight;
	}
}