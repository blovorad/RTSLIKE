package factionConfiguration;

public class ForUnit extends Patron
{
	private int range;
	private int damage;
	private int armor;
	private int maxSpeed;
	private int timeToBuild;
	
	public ForUnit(int attackRange, int attackSpeed, int sightRange, int range, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild)
	{
		super(hp, age, sightRange, attackSpeed, attackRange);
		
		this.range = range;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.setTimeToBuild(timeToBuild);
		this.setSightRange(sightRange);
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
}
