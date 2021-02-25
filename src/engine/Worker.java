package engine;
/**
 * 
 * create: 16/02/2021
 * @author girard
 * @version: 16/02/2021
 *
 */

public class Worker extends Unit
{
	private int repair;
	
	public Worker (int currentAction, int attackRange, int maxSpeed, int damage, int range, int armor, int repair)
	{	
		super(currentAction, attackRange, maxSpeed, damage, range, armor);
		this.repair = repair;
	}

	public int getRepair() 
	{
		return repair;
	}

	public void setRepair(int repair) 
	{
		this.repair = repair;
	}
}
