package engine;
/**
 * 
 *	class to manage speed, needing to all move
 *	@author gautier
 */
public class Speed 
{
	/**
	 * velocity x
	 */
	private float vx;
	/**
	 * velocity y
	 */
	private float vy;
	
	/**
	 * constructor
	 */
	public Speed()
	{
		this(0,0);
	}
	
	/**
	 * constructor
	 * @param vx velocity x
	 * @param vy velocity y
	 */
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
    
    /**
     * reset the speed, need to stop a entity
     */
    public void reset()
    {
        vx = 0;
        vy = 0;
    }
    
    /**
     * if the current entity has a speed different of 0
     * @return true if he has a speed
     */
    public boolean hasSpeed() {
    	if(this.vx != 0.0 || this.vy != 0.0) {
    		return true;
    	}
    	return false;
    }

}