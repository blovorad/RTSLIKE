package factionConfiguration;

public class ForAttackBuilding extends Patron{
	
	private int sightRange;
	private int attackDamage;
	private int attackRange;
	private int attackSpeed;
	
	public ForAttackBuilding(int hp, int sightRange, int attackDamage, int attackRange, int attackSpeed, int age, String description, int hpMax)
	{
		super(hp, age, description, hpMax);
		this.sightRange = sightRange;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}	
	
	public int getSightRange() {
		return sightRange;
	}

	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
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
