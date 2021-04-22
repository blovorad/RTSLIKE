package factionConfiguration;

/**
 * 
 * @author gautier
 * extends patron and specificaly stat of tower need
 */
public class ForAttackBuilding extends Patron{
	
	/**
	 * attack damage
	 */
	private int attackDamage;
	/**
	 * attack range
	 */
	private int attackRange;
	/**
	 * attack speed
	 */
	private int attackSpeed;
	
	public ForAttackBuilding(int hp, int sightRange, int attackDamage, int attackRange, int attackSpeed, int age, String description, int cost)
	{
		super(hp, age, description, hp, sightRange, cost);
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}	

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
}
