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
	
	public Worker (int hp, int currentAction, int attackRange, int maxSpeed, int damage, int range, int armor, int repair, Position position, int id)
	{	
		super(hp, currentAction, attackRange, maxSpeed, damage, range, armor, position, id);
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
