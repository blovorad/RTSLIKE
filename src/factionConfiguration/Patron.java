package factionConfiguration;
/**
 * 
 * @author gautier
 * not instanciate use to all entity
 */
public abstract class Patron 
{
	/**
	 * hp of the entity
	 */
	private int hp;
	/**
	 * age of entity can be build
	 */
	private int age;
	/**
	 * max hp of the entity
	 */
	private int hpMax;
	/**
	 * description of the entity
	 */
	private String description;
	/**
	 * cost of the production or building
	 */
	private int cost;
	/**
	 * line of sight of entity
	 */
	private int sightRange;
	
	public Patron(int hp, int age, String description, int hpMax, int sightRange, int cost)
	{
		this.hp = hp;
		this.age = age;
		this.description = description;
		this.setHpMax(hpMax);
		this.setCost(cost);
		this.sightRange = sightRange;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getSightRange() {
		return sightRange;
	}

	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
	}
}
