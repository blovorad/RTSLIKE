package engine;

import java.awt.event.MouseEvent;

import configuration.GameConfiguration;

/**
 *
 * création:16/02/2021
 * @author Girard 
 * @version:16/02/2021
 * 
 */

public class Unit extends Entity
{
	private int currentAction;
	private int attackRange;
	private int maxSpeed;
	private int damage;
	private int range;
	private int armor;
	
	private int xSible;
	private int ySible;
	
	private Speed speed;
	
	public Unit(int currentAction, int attackRange, int maxSpeed, int damage, int range, int armor)
	{
		super();
		
		this.currentAction = currentAction;
		this.attackRange = attackRange;
		this.maxSpeed = maxSpeed;
		this.damage = damage;
		this.range = range;
		this.armor = armor;
	}
	
	public void move(int vx, int vy)
	{
		this.getSpeed().setVx(vx);
		this.getSpeed().setVy(vy);
	}
	
	public void attack(int damage)
	{
		this.getTarget().damage(damage);
	}
	
	
	public void checkTarget()
	{
		if(this.getTarget().getHp() <= 0)
		{
			this.setTarget(null);
		}
	}
	
	public void restaurHP()
	{
		
	}
	
	public int getDamage() 
	{
		return damage;
	}

	public void setDamage(int damage) 
	{
		this.damage = damage;
	}

	public int getRange() 
	{
		return range;
	}

	public void setRange(int range) 
	{
		this.range = range;
	}

	public int getArmor() 
	{
		return armor;
	}

	public void setArmor(int armor) 
	{
		this.armor = armor;
	}

	public int getAttackRange() 
	{
		return attackRange;
	}

	public void setAttackRange(int attackRange) 
	{
		this.attackRange = attackRange;
	}

	public int getCurrentAction() 
	{
		return  currentAction;
	}

	public void setCurrentAction(int currentAction) 
	{
		this.currentAction = currentAction;
	}

	public Speed getSpeed() 
	{
		return speed;
	}

	public void setSpeed(Speed speed) 
	{
		this.speed = speed;
	}
	

	void update()
	{
		if(this.getTarget() != null && (!this.getTarget().getPosition().equals(this.getPosition())))
		{
			this.getDestination().setPosition(this.getTarget().getPosition());
			double angle = Math.atan2( (this.getTarget().getPosition().getY() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getY() + GameConfiguration.TILE_SIZE /2), (this.getTarget().getPosition().getX() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getX() + GameConfiguration.TILE_SIZE));
			this.move((int)(this.maxSpeed * Math.cos(angle)), (int)(this.maxSpeed * Math.sin(angle)));
			
		}
		
		else
		{

			this.getPosition().setX(this.getPosition().getX() + this.getSpeed().getVx());
			this.getPosition().setY(this.getPosition().getY() + this.getSpeed().getVy());
			
			if(this.getPosition().getX() < this.getDestination().getX() || this.getPosition().getX() > this.getDestination().getX())
			{
				this.getPosition().setX(this.getDestination().getX());
				speed.setVx(0);
			}
			else if(this.getPosition().getY() < this.getDestination().getY() || this.getPosition().getY() > this.getDestination().getY())
			{
				this.getPosition().setY(this.getDestination().getY());
				speed.setVy(0);
			}
		}

	}
}
