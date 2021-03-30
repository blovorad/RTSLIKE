package engine;
/**
 * 
 * @author gautier
 *
 */
public class Speed 
{
	private float vx;
	private float vy;
	
	public Speed()
	{
		this(0,0);
	}
	
	public Speed(float vx, float vy)
	{
		this.vx = vx;
		this.vy = vy;
	}

	public float getVy() 
	{
		return vy;
	}
    public float getVx() 
    {
        return vx;
    }

	public void setVy(float vy) 
	{
		this.vy = vy;
	}

    public void setVx(float vx) 
    {
        this.vx = vx;
    }

    public void reset()
    {
        vx = 0;
        vy = 0;
    }

}