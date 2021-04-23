package factionConfiguration;

/**
 * 
 * extends patron, create to fit with fighter needs
 * @author gautier
 */

public class ForFighter extends Patron
{
	/**
	 * damage
	 */
	private int damage;
	/**
	 * armor
	 */
	private int armor;
	/**
	 * max speed of infantry
	 */
	private int maxSpeed;
	/**
	 * how many time before building
	 */
	private int timeToBuild;
	/**
	 * speed of attack
	 */
	private int attackSpeed;
	/**
	 * attack range
	 */
	private int attackRange;
	
	public ForFighter(int attackRange, int attackSpeed, int sightRange, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild, String description, int hpMax, int cost)
	{
		super(hp, age, description, hpMax, sightRange, cost);
		
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.setTimeToBuild(timeToBuild);
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
