package engine;
/**
 * 
 * @author gautier
 *
 */
public class Speed 
{
	private int vx;
	private int vy;
	
	public Speed()
	{
		this(0,0);
	}
	
	public Speed(int vx, int vy)
	{
		this.vx = vx;
		this.vy = vy;
	}

	public int getVx() 
	{
		return vx;
	}

	public void setVx(int vx) 
	{
		this.vx = vx;
	}

	public int getVy() 
	{
		return vy;
	}

	public void setVy(int vy) 
	{
		this.vy = vy;
	}

	public void reset()
	{
		vx = 0;
		vy = 0;
	}
	
}
