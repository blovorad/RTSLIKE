package engine;

public class Collision 
{
	public static boolean collide(Position p, SelectionRect r)
	{
		int x = p.getX();
		int y = p.getY();
		
		int x2 = r.getX();
		int y2 = r.getY();
		int w = r.getW();
		int h = r.getH();
		
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
		
		if(x > x2 && x < x2 + w && y > y2 && y < y2 + h)
		{
			return true;
		}
		
		return false;
	}
}
