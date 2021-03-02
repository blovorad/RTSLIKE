package factionConfiguration;

public abstract class Patron 
{
	private int hp;
	private int age;
	private int sightRange;
	private int attackSpeed;
	private int attackRange;
	private int hpMax;
	private String description;
	
	public Patron(int hp, int age, int sightRange, int attackSpeed, int attackRange, String description, int hpMax)
	{
		this.hp = hp;
		this.age = age;
		this.sightRange = sightRange;
		this.attackSpeed = attackSpeed;
		this.attackRange = attackRange;
		this.description = description;
		this.setHpMax(hpMax);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
}
