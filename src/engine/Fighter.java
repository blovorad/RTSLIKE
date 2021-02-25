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
	
	public Fighter (int currentAction, int attackRange, int maxSpeed, int damage, int range, int armor, int state)
	{	
		super(currentAction, attackRange, maxSpeed, damage, range, armor);
		this.state = state;
	}

	public int getState() 
	{
		return state;
	}

	public void setState(int state) 
	{
		this.state = state;
	}
	
	public void update()
	{
		
	}
}
