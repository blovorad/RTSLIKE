package configuration;

import java.awt.Color;

/**
 * 
 * @author gautier
 *
 */

public class MapConfiguration {
	
	public static final int GRASS = 0;
	
	public static final int WATER = 1;
	
	public static final int ROCK = 2;
	
	public static final int WOOD = 3;
	
	public static final int GOLD = 4;
	
	public final static int WATER_BORD_UP = 5;
	
	public final static int WATER_BORD_DOWN = 6;
	
	public final static int WATER_BORD_LEFT = 7;
	
	public final static int WATER_BORD_RIGHT = 8;
	
	public final static int WATER_TURN_UP_LEFT = 9;
	
	public final static int WATER_TURN_UP_RIGHT = 10;
	
	public final static int WATER_TURN_DOWN_LEFT = 11;
	
	public final static int WATER_TURN_DOWN_RIGHT = 12;
	
	public final static int SAND = 25;
	
	public static final boolean getTileSolid(int id)
	{
		/*if(id == WATER || id == ROCK || id == WOOD || id == GOLD)
		{
			return true;
		}*/
		if(id == GRASS || id == SAND)
		{
			return false;
		}
		
		return true;
	}
	
	public static final Color getTileColor(int id)
	{
		if(id == WATER)
		{
			return Color.blue;
		}
		if(id == ROCK)
		{
			return Color.gray;
		}
		if(id == GRASS)
		{
			return new Color(0, 100, 0);
		}
		if(id == GOLD)
		{
			return new Color(0, 100, 0);
		}
		if(id == WOOD)
		{
			return new Color(88, 41, 0);
		}
		
		return Color.black;
	}
}
