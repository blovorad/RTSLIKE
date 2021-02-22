package engine;
/**
 * 
 * créate:16/02/2021
 * @author girard
 * @version: 16/02/2021
 *
 */

public class Fighter extends Unit
{
	private int state;
	
	public Fighter()
	{
		super();
	}

	public int getState() 
	{
		return state;
	}

	public void setState(int state) 
	{
		this.state = state;
	}
}
