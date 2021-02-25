package engine;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;

public abstract class Building extends Entity{

	private List<Unit> elementCount;
	private int timer;
	private boolean isProducing;
	private int productionId;
	private boolean canAttak;
	private List<Upgrades> upgrades;
	private int sightRange;
	private int attakRange;
	private int attackSpeed;
	private int damage;
	
	/*
	 * private int hp;
	private String description;
	private Position position;
	private Entity target;
	private Position destination;
	private int id;
	 */
	public Building(Position position, int id) {
		super(100, "batiment type", position, id);
		elementCount = new ArrayList<Unit>();
	}
	
	//coder un produce et un launchProduction
	//produce return l'élément produit
	
	public void update() {
		/*Random rand = new Random();
		int lessHp = rand.nextInt(5);
		this.setHp(this.getHp() - lessHp);
		System.out.println("we remove : " + lessHp);*/
		if(canAttak == true) {
			if(this.getTarget() != null) {
				if(isInRange(attakRange, this.getTarget())) {
					attak();
				}
			}else {
				lookForTarget(this.getPosition(), attakRange);
			}
		}
		if(timer > 0)
		{
			timer--;
			if(this.getProductionId() == EntityConfiguration.INFANTRY) {
				System.out.println("updating infantry production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.ARCHER) {
				System.out.println("updating archer production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.CAVALRY) {
				System.out.println("updating cavalry production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.SPECIAL_UNIT) {
				System.out.println("updating special production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.WORKER) {
				System.out.println("updating worker production time remaning : " + timer);
			}
			else {
				System.out.println("Invalid id");
			}
		}
	}
	
	public abstract Unit produce();
	public abstract void startProd(int id);
	public abstract void attak(); 
	public abstract void lookForTarget(Position position, int range);

	public boolean isInRange(int attakRange, Entity target) { // methode qui calcule si la target est dans la range
		
		return false;
	}
	
	//getter setter
	public List<Unit> getElementCount() {
		return elementCount;
	}
	public void setElementCount(List<Unit> elementCount) {
		this.elementCount = elementCount;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean getIsProducing() {
		return isProducing;
	}

	public void setIsProducing(boolean isProducing) {
		this.isProducing = isProducing;
	}

	public List<Upgrades> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(List<Upgrades> upgrades) {
		this.upgrades = upgrades;
	}

	public int getProductionId() {
		return productionId;
	}

	public void setProductionId(int productionId) {
		this.productionId = productionId;
	}

	public int getSightRange() {
		return sightRange;
	}

	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
	}

	public int getAttakRange() {
		return attakRange;
	}

	public void setAttakRange(int attakRange) {
		this.attakRange = attakRange;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
}
