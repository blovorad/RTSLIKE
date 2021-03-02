package engine;

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
	
	private Speed speed;
	
	public Unit(int hp, int currentAction, int attackRange, int maxSpeed, int damage, int range, int armor, Position position, int id, String description)
	{
		super(hp, description, position, id);
		
		this.currentAction = currentAction;
		this.attackRange = attackRange;
		this.maxSpeed = maxSpeed;
		this.damage = damage;
		this.range = range;
		this.armor = armor;
		
		this.speed = new Speed(0, 0);
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
	
	public void restaurHP(int hp)
	{
		this.setHp(this.getHp() + hp);
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
	
	public void calculateSpeed(Position p)
	{
		this.setDestination(p);
		double angle = Math.atan2( (p.getY() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getY() + GameConfiguration.TILE_SIZE /2), (p.getX() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getX() + GameConfiguration.TILE_SIZE));
		this.move((int)(this.maxSpeed * Math.cos(angle)), (int)(this.maxSpeed * Math.sin(angle)));
	}
	

	public void update()
	{
		Position p = this.getPosition();
		
		if(this.getTarget() != null && (!this.getTarget().getPosition().equals(this.getPosition())))
		{
			calculateSpeed(this.getTarget().getPosition());
		}
		else
		{			
			/*if(this.getDestination() != null)
			{
				if(this.getPosition().getX() < this.getDestination().getX() || this.getPosition().getX() > this.getDestination().getX())
				{
					this.getPosition().setX(this.getDestination().getX());
					speed.setVx(0);
					this.setDestination(null);
				}
				else if(this.getPosition().getY() < this.getDestination().getY() || this.getPosition().getY() > this.getDestination().getY())
				{
					this.getPosition().setY(this.getDestination().getY());
					speed.setVy(0);
					this.setDestination(null);
				}
			}*/
		}
		
		this.getPosition().setX(this.getPosition().getX() + this.getSpeed().getVx());
		this.getPosition().setY(this.getPosition().getY() + this.getSpeed().getVy());
		
		if(p.getX() < 0)
		{
			p.setX(0);
			this.getSpeed().setVx(0);
		}
		else if(p.getX() + GameConfiguration.TILE_SIZE > GameConfiguration.COLUMN_COUNT * GameConfiguration.TILE_SIZE)
		{
			p.setX(GameConfiguration.COLUMN_COUNT * GameConfiguration.TILE_SIZE - GameConfiguration.TILE_SIZE);
			this.getSpeed().setVx(0);
		}
		
		if(p.getY() < 0)
		{
			p.setY(0);
			this.getSpeed().setVy(0);
		}
		else if(p.getY() + GameConfiguration.TILE_SIZE > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT)
		{
			p.setY(GameConfiguration.LINE_COUNT * GameConfiguration.TILE_SIZE - GameConfiguration.TILE_SIZE);
			this.getSpeed().setVy(0);
		}
		//ici collision
	}
}
