package engine;

import configuration.GameConfiguration;

public class Collision 
{
	public static boolean collide(Position p, SelectionRect r, Camera camera)
	{
		int x = (p.getX()) / GameConfiguration.TILE_SIZE;
		int y = (p.getY()) / GameConfiguration.TILE_SIZE;
		int w = 1;
		int h = 1;
		
		System.out.println("rect debut  : " + (r.getX() + camera.getX()) + "," + (r.getY() + camera.getY()));
		
		int x2 = (r.getX() + camera.getX()) / GameConfiguration.TILE_SIZE;
		int y2 = (r.getY() + camera.getY()) / GameConfiguration.TILE_SIZE;
		int w2 = r.getW() / GameConfiguration.TILE_SIZE;
		int h2 = r.getH() / GameConfiguration.TILE_SIZE;
		
		System.out.println("objet : " + x + "," + y + " rapport : " + w + "," + h);
		System.out.println("rectangle : " + x2 + "," + y2 + " rapport : " + w2 + "," + h2);
		
		if(x >= x2 && x <= x2 + w2 && y >= y2 && y <= y2 + h2)
		{
			return true;
		}
		else if(x + w >= x2 && x + w <= x2 + w2 && y >= y2 && y <= y2 + h2)
		{
			return true;
		}
		else if(x + w >= x2 && x + w <= x2 + w2 && y + h >= y2 && y <= y2 + h2)
		{
			return true;
		}
		else if(x >= x2 && x <= x2 + w2 && y + h >= y2 && y + h <= y2 + h2)
		{
			return true;
		}
		else if(x >= x2 && x <= x2 + w2 && y >= y2 && y <= y2 + h2)
		{
			return true;
		}
		
		return false;
	}
}
