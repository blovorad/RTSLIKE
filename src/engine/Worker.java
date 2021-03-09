package engine;


/**
 * 
 * create: 16/02/2021
 * @author girard
 * @version: 16/02/2021
 *
 */

public class Worker //extends Unit
{
	private int repair;
	
	private int nbrRessource;
	
	//private Building building;
	private Ressource ressource;
	
	public Worker (int hp, int currentAction, int attackRange, int maxSpeed, int damage, int range, int armor, int repair, Position position, int id, String description, int hpMax)
	{	
		//super(hp, currentAction, attackRange, maxSpeed, damage, range, armor, position, id, description, hpMax);
		this.repair = repair;
	}
	
	public void toHarvest()
	{
		if(this.ressource != null)
		{
			this.ressource.setHp(this.ressource.getHp() -1);
			this.nbrRessource++;
			if(this.ressource.getHp() == 0)
			{
				this.ressource = null;
			}
		}
	}
	
	public void toRepair()
	{
		
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
