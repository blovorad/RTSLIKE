package factionConfiguration;

public abstract class Patron 
{
	private int hp;
	private int age;
	private int sightRange;
	private int attackSpeed;
	private int attackRange;
	
	public Patron(int hp, int age, int sightRange, int attackSpeed, int attackRange)
	{
		this.hp = hp;
		this.age = age;
		this.sightRange = sightRange;
		this.attackSpeed = attackSpeed;
		this.attackRange = attackRange;
	}
	
	public int getHp() 
	{
		return hp;
	}
	
	public void setHp(int hp) 
	{
		this.hp = hp;
	}
	
	public int getAge() 
	{
		return age;
	}
	
	public void setAge(int age) 
	{
		this.age = age;
	}

	public int getSightRange() {
		return sightRange;
	}

	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
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
