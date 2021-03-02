package configuration;

import java.awt.Color;

public class MapConfiguration {
	
	public static final int GRASS = 0;
	
	public static final int WATER = 1;
	
	public static final int ROCK = 2;
	
	public static final int WOOD = 3;
	
	public static final int GOLD = 4;
	
	public static final boolean getTileSolid(int id)
	{
		/*if(id == WATER || id == ROCK || id == WOOD || id == GOLD)
		{
			return true;
		}*/
		if(id == GRASS)
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
