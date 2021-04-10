package engine.entity.unit;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Position;
import engine.Speed;
import engine.manager.GraphicsManager;
import engine.math.Collision;

/**
 *
 * crÃ©ation:16/02/2021
 * @author Girard 
 * @version:16/02/2021
 * 
 */

public class Unit extends Entity
{
	private Unit targetUnit;
	
	private int currentAction;
	private int attackRange;
	private int attackSpeed;
	private int maxSpeed;
	private int damage;
	private int range;
	private int armor;
	private int timer;
	
	private Speed speed;
	
	public Unit(int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int range, int armor, Position position, int id, String description, Position destination, int hpMax, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{
		super(hp, hpMax, description, position, id, faction, sightRange, maxFrame, graphicsManager);
		
		this.currentAction = EntityConfiguration.IDDLE;
		this.attackRange = attackRange;
		this.setAttackSpeed(5);
		this.maxSpeed = maxSpeed;
		this.damage = damage;
		this.range = range;
		this.armor = armor;
		this.setDestination(destination);
		this.speed = new Speed(0, 0);
		this.timer = this.attackSpeed;
		
		if(destination != null) {
			System.out.println("calcul");
			calculateSpeed(destination);
		}
	}
	
	public void move(float vx, float vy)
	{
		if(vx > 0.0 && vx < 1.0) {
			vx = 1;
		}
		else if(vx < 0.0 && vx > -1.0) {
			vx = -1;
		}
		
		if(vy > 0.0 && vy < 1.0) {
			vy = 1;
		}
		else if(vy < 0.0 && vy > -1.0) {
			vy = -1;
		}
		
		this.getSpeed().setVx(vx);
		this.getSpeed().setVy(vy);
		if(vx != 0 || vy != 0) {
			if(vx < 0) {
				this.getAnimation().setDirection(GameConfiguration.LEFT);
			}
			else {
				this.getAnimation().setDirection(GameConfiguration.RIGHT);
			}
		}
	}
	
	public void attack(int damage)
	{
		if(this.getTarget() != null)
		{
			System.out.println("test");
			if(this.timer <= 0)
			{
				if(this.targetUnit != null && this.targetUnit.getHp() >= 0)
				{
					this.targetUnit.damage((damage - this.targetUnit.getArmor()));
					this.checkTarget();
				}
				
				else if(this.getTarget().getHp() >= 0)
				{
					this.getTarget().damage(damage);
					this.checkTarget();
				}
				this.timer = this.attackSpeed;
			}
			this.timer--;
		}
	}
	
	
	public void checkTarget()
	{
		if(this.getTarget().getHp() <= 0)
		{
			this.setTarget(null);
			this.targetUnit = null;
		}
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
		this.move((float)(this.maxSpeed * Math.cos(angle)), (float)(this.maxSpeed * Math.sin(angle)));
	}

	public void update()
	{
		super.update();
		Position p = this.getPosition();
		
		if(this.getTarget() != null && this.getDestination()!= null && !(this.getTarget().getPosition().equals(this.getDestination())))
		{	
			calculateSpeed(this.getTarget().getPosition());
		}
		else
		{	
			
			if(this.getDestination() != null)
			{
				//System.out.println("on a une destination");
				if(!this.getPosition().equals(this.getDestination()))   //!this.getPosition().equals(this.getDestination()) Collision.collideEntity(this, this.getDestination())
				{
					//System.out.println("pas egal on bouge");
					//System.out.println("speed : " + this.speed.getVx() + "," + this.speed.getVy());
					if( (this.getPosition().getX() < this.getDestination().getX() && speed.getVx() < 0) || (this.getPosition().getX() > this.getDestination().getX() && speed.getVx() > 0) )
					{
						this.getPosition().setX(this.getDestination().getX());
						speed.setVx(0);
					}
					else if( (this.getPosition().getY() < this.getDestination().getY() && speed.getVy() < 0) || (this.getPosition().getY() > this.getDestination().getY() && speed.getVy() > 0) )
					{
						this.getPosition().setY(this.getDestination().getY());
						speed.setVy(0);
					}
					else if( this.getPosition().equals(this.getDestination()))
					{
						this.setDestination(null);
					}
				}
			}
			else
			{
				this.setDestination(null);
				this.speed.reset();
			}
		}
		
		this.getPosition().setX(this.getPosition().getX() + (int)this.getSpeed().getVx());
		this.getPosition().setY(this.getPosition().getY() + (int)this.getSpeed().getVy());
		
		if((this.getAnimation().getFrameState() == EntityConfiguration.WALK || this.getAnimation().getFrameState() == EntityConfiguration.ATTACK) && this.getSpeed().getVx() == 0 && this.getSpeed().getVy() == 0) {
			this.getAnimation().setFrameState(EntityConfiguration.IDDLE);
		}
		
		
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
		

		if(this.getTarget() != null && this.getTarget().getFaction() != this.getFaction() && Collision.collideattack(this.getTarget(), this) ) //Collision.collideEntity(this, this.getTarget()) Collision.collideattack(this, this.getTarget())
		{
			this.checkTarget();
			System.out.println(this.getTarget());
			this.getSpeed().reset();
			this.attack(this.getDamage());
			System .out.println("timer: " + this.timer);
			
			if(this.getTarget() == null)
			{
				this.setCurrentAction(EntityConfiguration.IDDLE);
				this.timer = this.attackSpeed;
			}
			else
			{
				this.setCurrentAction(EntityConfiguration.ATTACK);
			}
		}
		
		manageState();
	}
	
	public void manageState() {

		if(speed.getVx() == 0f && speed.getVy() == 0f && currentAction != EntityConfiguration.ATTACK && currentAction != EntityConfiguration.HARVEST && currentAction != EntityConfiguration.REPAIR) {
			this.getAnimation().setFrameState(EntityConfiguration.IDDLE);
		}
		else if(speed.getVx() != 0f || speed.getVy() != 0f) {
			this.getAnimation().setFrameState(EntityConfiguration.WALK);
		}
		else if(currentAction == EntityConfiguration.ATTACK || currentAction == EntityConfiguration.HARVEST || currentAction == EntityConfiguration.REPAIR) {
			this.getAnimation().setFrameState(EntityConfiguration.ATTACK);
		}
		
		
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public int getTimer()
	{
		return this.timer;
	}
	
	public void setTimer(int timer)
	{
		this.timer = timer;
	}
	
	public void setTargetUnit(Unit targetUnit)
	{
		this.targetUnit = targetUnit;
	}
	public Unit getTargetUnit()
	{
		return this.targetUnit;
	}
}