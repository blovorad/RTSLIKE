package engine.entity.unit;

import java.util.List;

import engine.Position;
import engine.manager.GraphicsManager;

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
	
	
	public Fighter (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int range, int armor, int state, Position position, int id, String description, int hpMax, int faction, Position destination, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{	
		super(hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, range, armor, position, id, description, destination, hpMax, faction, sightRange, maxFrame, graphicsManager);
		this.state = state;
		//System.out.println("maxspeed fighter : " + maxSpeed);
	}

	public int getState() 
	{
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public void update(List<Unit> units) 
	{
		super.update(units);
	}
}
