package factionConfiguration;

/**
 * 
 * @author gautier
 *
 */

public class ForFighter extends Patron
{
	private int range;
	private int damage;
	private int armor;
	private int maxSpeed;
	private int timeToBuild;
	private int attackSpeed;
	private int attackRange;
	
	public ForFighter(int attackRange, int attackSpeed, int sightRange, int range, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild, String description, int hpMax, int cost)
	{
		super(hp, age, description, hpMax, sightRange, cost);
		
		this.range = range;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.setTimeToBuild(timeToBuild);
	}

	public int getRange() 
	{
		return range;
	}

	public void setRange(int range) 
	{
		this.range = range;
	}

	public int getDamage() 
	{
		return damage;
	}

	public void setDamage(int damage) 
	{
		this.damage = damage;
	}

	public int getArmor() 
	{
		return armor;
	}

	public void setArmor(int armor) 
	{
		this.armor = armor;
	}

	public int getMaxSpeed() 
	{
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) 
	{
		this.maxSpeed = maxSpeed;
	}
	
	public int getTimeToBuild() 
	{
		return timeToBuild;
	}
	
	public void setTimeToBuild(int timeToBuild) 
	{
		this.timeToBuild = timeToBuild;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
}
