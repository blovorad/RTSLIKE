package factionConfiguration;

public class ForWorker extends Patron
{
	private int range;
	private int damage;
	private int armor;
	private int maxSpeed;
	private int timeToBuild;
	private int ressourceMax;
	private int harvest;
	private int harvestSpeed;
	private int attackSpeed;
	private int attackRange;
	private int repair;
	private int repairSpeed;
	
	public ForWorker(int attackRange, int attackSpeed, int sightRange, int range, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild, String description, int hpMax, int ressourceMax, int harvest, int repair, int harvestSpeed, int repairSpeed, int cost)
	{
		super(hp, age, description, hpMax, cost, sightRange);
		
		this.range = range;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.setTimeToBuild(timeToBuild);
		this.setRessourceMax(ressourceMax);
		this.setHarvest(harvest);
		this.repair = repair;
		this.setHarvestSpeed(harvestSpeed);
		this.setRepairSpeed(repairSpeed);
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

	public int getRessourceMax() {
		return ressourceMax;
	}

	public void setRessourceMax(int ressourceMax) {
		this.ressourceMax = ressourceMax;
	}

	public int getHarvest() {
		return harvest;
	}

	public void setHarvest(int harvest) {
		this.harvest = harvest;
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

	public int getRepair() {
		return repair;
	}

	public void setRepair(int repair) {
		this.repair = repair;
	}

	public int getHarvestSpeed() {
		return harvestSpeed;
	}

	public void setHarvestSpeed(int harvestSpeed) {
		this.harvestSpeed = harvestSpeed;
	}

	public int getRepairSpeed() {
		return repairSpeed;
	}

	public void setRepairSpeed(int repairSpeed) {
		this.repairSpeed = repairSpeed;
	}
}
