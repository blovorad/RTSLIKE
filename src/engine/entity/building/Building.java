package engine.entity.building;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.EntityConfiguration;
import engine.Entity;
import engine.Position;
import engine.Unit;
import engine.Upgrades;

public abstract class Building extends Entity{

	private List<Unit> elementCount;
	private int timer;
	private boolean isProducing;
	private int productionId;
	private boolean canAttak;
	private boolean canHeal;
	private List<Upgrades> upgrades;
	private int sightRange;
	private int attakRange;
	private int attackSpeed;
	private int damage;
	private int attackCooldown;
	
	public Building(Position position, int id, String description, int hpMax) {
		super(100, hpMax, description , position, id);
		elementCount = new ArrayList<Unit>();
	}
	
	public void update(List<Unit> units) {
		if(canAttak == true) {
			if(this.getAttackCooldown() > 0) {
				this.setAttackCooldown(this.getAttackCooldown()-1);
				//System.out.println(this.getDescription() + " attack cooldown is " + this.getAttackCooldown());
			}
			if(this.getTarget() != null) {
				if(isInRange(this.getTarget())) {
					if(this.getAttackCooldown() <= 0) {
						if(this.getTarget().getHp() <= 0) {
							this.setTarget(null);
						}else{
							attak();
							this.setAttackCooldown(this.getAttackSpeed());
							System.out.println(this.getDescription() + " attacked " + this.getTarget().getDescription() + " for " + this.getDamage() + " !");
							System.out.println(this.getTarget().getDescription() + " is now " + this.getTarget().getHp());
						}
					}
				}
			}else {
				lookForTarget(units);
			}
		}
		if(canHeal == true) {
			if(this.getAttackCooldown() > 0) {
				this.setAttackCooldown(this.getAttackCooldown()-1);
				//System.out.println(this.getDescription() + " heal cooldown is " + this.getAttackCooldown());
			}
			if(this.getTarget() != null) {
				if(isInRange(this.getTarget())) {
					if(this.getAttackCooldown() <= 0) {
						if(this.getTarget().getHp() <= 0) {
							this.setTarget(null);
						}else {
							System.out.println("hp target ; " + this.getTarget().getHp() + ", " + this.getTarget().getHpMax());
							if(this.getTarget().getHp() < this.getTarget().getHpMax()) {
								heal();
								this.setAttackCooldown(this.getAttackSpeed());
								System.out.println(this.getDescription() + " healed " + this.getTarget().getDescription() + " for " + this.getDamage() + " !");
								System.out.println(this.getTarget().getDescription() + " is now " + this.getTarget().getHp());
							}
						}
					}
				}
			}else {
				lookForTarget(units);
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
	
	public  void attak() {
		this.getTarget().damage(damage);
	}
	
	public void heal() {
		this.getTarget().heal(this.getDamage());
	}
	
	public void lookForTarget(List<Unit> units)
	{
		AbstractMap<Double,Unit> unitsInRange = new HashMap<Double,Unit>();
		for(Unit unit : units) {
			if(this.isInRange(unit)) {
				unitsInRange.put(calculateDistance(this, unit), unit); // met toutes les units inRange dans une map
			}
		}
		if(!unitsInRange.isEmpty()) {
			Double minKey = null;
			for(Double key : unitsInRange.keySet()) { // on recupere la plus petite distance
				if(minKey == null) {
					minKey = key;
				}
				else if(key<minKey) {
					minKey = key;
				}
			}
			this.setTarget(unitsInRange.get(minKey)); // on set la target sur l unit la plus proche
		}
	}

	public boolean isInRange(Entity target) { // methode qui calcule si la target est dans la range
		if(target == null) {
			return false;
		}
		else if(calculateDistance(this, target) <= this.attakRange) { // compare distance a la range
			return true;
		}
		else {
			return false;
		}
	}
	
	public double calculateDistance(Entity ent1, Entity ent2) {
		return Math.sqrt(Math.pow(ent1.getPosition().getX() - ent2.getPosition().getX(), 2) + Math.pow(ent1.getPosition().getY() - ent2.getPosition().getY(), 2)); //calcule distance entre 2 points
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

	public boolean isCanAttak() {
		return canAttak;
	}

	public void setCanAttak(boolean canAttak) {
		this.canAttak = canAttak;
	}

	public int getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(int attackCooldown) {
		this.attackCooldown = attackCooldown;
	}

	public boolean isCanHeal() {
		return canHeal;
	}

	public void setCanHeal(boolean canHeal) {
		this.canHeal = canHeal;
	}
	
}