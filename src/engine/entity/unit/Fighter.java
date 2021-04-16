package engine.entity.unit;

import java.util.List;

import configuration.EntityConfiguration;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.Map;

/**
 * 
 * créate:16/02/2021
 * @author girard
 * @version: 16/02/2021
 *
 */

public class Fighter extends Unit
{
	public Fighter (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int armor, int state, Position position, int id, String description, int hpMax, int faction, Position destination, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{	
		super(hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, armor, position, id, description, destination, hpMax, faction, sightRange, maxFrame, graphicsManager,EntityConfiguration.AGRESSIF_STATE);
		//System.out.println("maxspeed fighter : " + maxSpeed);
	}

	
	public void update(List<Unit> units, Fog playerFog, Map map) 
	{
		super.update(units, playerFog, map);
	}
}
