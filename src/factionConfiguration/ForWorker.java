package factionConfiguration;
/**
 * 
 *	extends patron to fit specificaly with worker needs
 *	@author gautier
 */
public class ForWorker extends Patron
{
	private int damage;
	private int armor;
	private int maxSpeed;
	private int timeToBuild;
	/**
	 * max ressource can be stock in inventory
	 */
	private int ressourceMax;
	/**
	 * how many ressource stock in inventory at each frame
	 */
	private int harvest;
	
	/**
	 * speed of harvest
	 */
	private int harvestSpeed;

	private int attackSpeed;
	private int attackRange;
	/**
	 * how many hp will repair at each frame
	 */
	private int repair;
	
	/**
	 * speed of repairing
	 */
	private int repairSpeed;

	
	public ForWorker(int attackRange, int attackSpeed, int sightRange, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild, String description, int hpMax, int ressourceMax, int harvest, int repair, int harvestSpeed, int repairSpeed, int cost)
	{
		super(hp, age, description, hpMax, sightRange, cost);
		
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.timeToBuild = timeToBuild;		
		this.ressourceMax = ressourceMax;
		this.harvest = harvest;
		this.repair = repair;
		this.harvestSpeed = harvestSpeed;
		this.repairSpeed = repairSpeed;
	}

	public int getDamage() 
	{
		return damage;
	}


	public int getArmor() 
	{
		return armor;
	}

	public int getMaxSpeed() 
	{
		return maxSpeed;
	}

	public int getTimeToBuild() 
	{
		return timeToBuild;
	}
	
	public int getRessourceMax() {
		return ressourceMax;
	}
	public int getHarvest() {
		return harvest;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public int getRepair() {
		return repair;
	}

	public int getHarvestSpeed() {
		return harvestSpeed;
	}


	public int getRepairSpeed() {
		return repairSpeed;
	}
}
