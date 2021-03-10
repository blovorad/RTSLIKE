package engine.entity.unit;


import engine.Position;

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
	
	public Worker (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int range, int armor, int repair, Position position, int id, String description, int hpMax, int faction, Position destination, int sightRange)
	{	
		super(hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, range, armor, position, id, description, destination, hpMax, faction, sightRange);
		this.repair = repair;
		System.out.println("maxspeed worker : " + maxSpeed);
	}

	public int getRepair() 
	{
		return repair;
	}

	public void setRepair(int repair) 
	{
		this.repair = repair;
	}
	
	public void update() {
		super.update();
	}
}
