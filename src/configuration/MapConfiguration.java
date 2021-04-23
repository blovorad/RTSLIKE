package configuration;

import java.awt.Color;

/**
 * this class contain all constant need to generate and maintain a map
 * @author gautier
 *
 */

public class MapConfiguration {
	
	/**
	 * id of grass
	 */
	public static final int GRASS = 0;
	
	/**
	 * id of water
	 */
	public static final int WATER = 1;
	
	/**
	 * id of rock
	 */
	public static final int ROCK = 2;
	
	/**
	 * id of wood
	 */
	public static final int WOOD = 3;
	
	/**
	 * id of gold, ressource
	 */
	public static final int GOLD = 4;
	
	/**
	 * id for specific water
	 */
	public final static int WATER_BORD_UP = 5;
	/**
	 * id for specific water
	 */
	public final static int WATER_BORD_DOWN = 6;
	/**
	 * id for specific water
	 */
	public final static int WATER_BORD_LEFT = 7;
	/**
	 * id for specific water
	 */
	public final static int WATER_BORD_RIGHT = 8;
	/**
	 * id for specific water
	 */
	public final static int WATER_TURN_UP_LEFT = 9;
	/**
	 * id for specific water
	 */
	public final static int WATER_TURN_UP_RIGHT = 10;
	/**
	 * id for specific water
	 */
	public final static int WATER_TURN_DOWN_LEFT = 11;
	/**
	 * id for specific water
	 */
	public final static int WATER_TURN_DOWN_RIGHT = 12;
	/**
	 * id for sand
	 */
	public final static int SAND = 25;
	
	/**
	 * this function is use to know if a tile is solid or not
	 * use when a tile is creating
	 * @param id id of the tile
	 * @return true of false is tile is solid
	 */
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
	
	/**
	 * use to give a color of each tile
	 * @param id of the tile
	 * @return the color of the tile
	 */
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