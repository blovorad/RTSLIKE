package factionConfiguration;

public abstract class Patron 
{
	private int hp;
	private int age;
	private int hpMax;
	private String description;
	
	public Patron(int hp, int age, String description, int hpMax)
	{
		this.hp = hp;
		this.age = age;
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
