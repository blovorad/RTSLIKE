package factionConfiguration;

public class ForWorker extends Patron
{
	private int range;
	private int damage;
	private int armor;
	private int maxSpeed;
	private int timeToBuild;
	private int ressourceMax;
	private int ressourceGathering;
	
	public ForWorker(int attackRange, int attackSpeed, int sightRange, int range, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild, String description, int hpMax, int ressourceMax, int ressourceGathering)
	{
		super(hp, age, sightRange, attackSpeed, attackRange, description, hpMax);
		
		this.range = range;
		this.damage = damage;
		this.armor = armor;
		this.maxSpeed = maxSpeed;
		this.setTimeToBuild(timeToBuild);
		this.setSightRange(sightRange);
		this.setRessourceMax(ressourceMax);
		this.setRessourceGathering(ressourceGathering);
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

	public int getRessourceGathering() {
		return ressourceGathering;
	}

	public void setRessourceGathering(int ressourceGathering) {
		this.ressourceGathering = ressourceGathering;
	}
}
