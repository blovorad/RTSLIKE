package engine.entity.unit;

import java.util.List;

import configuration.EntityConfiguration;
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
	public Fighter (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int range, int armor, int state, Position position, int id, String description, int hpMax, int faction, Position destination, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{	
		super(hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, range, armor, position, id, description, destination, hpMax, faction, sightRange, maxFrame, graphicsManager,EntityConfiguration.AGRESSIF_STATE);
		//System.out.println("maxspeed fighter : " + maxSpeed);
	}

	
	public void update(List<Unit> units) 
	{
		super.update(units);
	}
}
