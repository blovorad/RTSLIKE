package engine;

import configuration.GameConfiguration;

public class Collision 
{
	public static boolean collide(Position p, SelectionRect r, Camera camera)
	{
		int x = p.getX();
		int y = p.getY();
		int w = GameConfiguration.TILE_SIZE;
		int h = GameConfiguration.TILE_SIZE;
		
		int x2 = r.getX();
		int y2 = r.getY();
		int w2 = r.getW();
		int h2 = r.getH();
		
		if(w < 0)
		{
			int bis = w;
			x2 = x2 + w;
			w = x2 - bis;
		}
		if(h < 0)
		{
			int bis = h;
			y2 = y2 + h;
			h = y2 - bis;
		}
		
		System.out.println("objet : " + x + "," + y + " rapport : 32,32");
		System.out.println("rectangle : " + x2 + "," + y2 + " rapport : " + w2 + "," + h2);
		
		if(x > x2 && x < x2 + w2 && y > y2 && y < y2 + h2)
		{
			return true;
		}
		else if(x + w > x2 && x + w < x2 + w2 && y > y2 && y < y2 + h2)
		{
			return true;
		}
		else if(x + w > x2 && x + w < x2 + w2 && y + h > y2 && y < y2 + h2)
		{
			return true;
		}
		
		return false;
	}
}
