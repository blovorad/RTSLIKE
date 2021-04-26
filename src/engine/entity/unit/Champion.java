package engine.entity.unit;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import engine.Entity;
import engine.Position;
import engine.entity.building.StorageBuilding;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.Map;
import engine.math.Collision;

public class Champion extends Fighter
{
	int timerHeal;
	int timerGroupAttack;
	
	public Champion(int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int armor, int state, Position position, int id, String description, int hpMax, int faction, Position destination, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{
		super(hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, armor, state, position, id, description, hpMax, faction, destination, sightRange, maxFrame, graphicsManager);
		this.timerGroupAttack = 350;
		this.timerHeal = 350;
	}
	
	public void update(List<Unit> units, Fog playerFog, Map map)
	{
		super.update(units,playerFog, map);
	}
	
	public void toHeal(List<Unit> units)
	{
		List<Unit> unitInRange = new ArrayList<Unit>();
		if(this.timerHeal <= 0)
		{	
			for(Unit value: units)
			{
				if(Collision.collideVision(value , this))
				{
					unitInRange.add(value);
				}
			}
				
			if(unitInRange.isEmpty())
			{
				for(Unit value: unitInRange)
				{
					value.heal(10);
				}
					
			}
				
			this.timerHeal = 350;
		}
		this.timerHeal--;
	}
	
	public void toGroupAttack(List<Unit> units)
	{
		List<Unit> unitInRange = new ArrayList<Unit>();
			if(this.timerHeal <= 0)
			{	
				for(Unit value: units)
				{
					if(Collision.collideVision(value , this) && value.getFaction() != this.getFaction())
					{
						unitInRange.add(value);
					}
				}
				
				if(unitInRange.isEmpty())
				{
					for(Unit value: unitInRange)
					{
						value.damage(this.getDamage());
					}
					
				}
				
				this.timerGroupAttack = 350;
			}
			this.timerGroupAttack--;
		}
}
