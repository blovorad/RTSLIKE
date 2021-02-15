package factionConfiguration;

public class ForUnit extends Patron
{
	private int attackRange;
	private int range;
	private int damage;
	private int armor;
	private int maxSpeed;
	
	public ForUnit(int attackRange, int range, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild)
	{
		this.setAttackRange(range);
		this.range = range;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.setHp(hp);
		this.setAge(age);
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

	public int getAttackRange() 
	{
		return attackRange;
	}

	public void setAttackRange(int attackRange) 
	{
		this.attackRange = attackRange;
	}
}
