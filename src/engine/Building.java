package engine;

import java.util.List;

public class Building extends Entity{

	private int elementCount;
	private int timer;
	private int isProducing;
	private int productionId;
	private int canAttak;
	private List<Upgrades> upgrades;
	private int sightRange;
	private int attakRange;
	private int damage;
	
	public Building() {
		
	}
	
	public void update() {
		if(canAttak == 1) {
			if(this.getTarget() != null) {
				if(isInRange(attakRange, this.getTarget())) {
					attak();
				}
			}else {
				lookForTarget(this.getPosition(), attakRange);
			}
		}
	}
	
	public boolean isInRange(int attakRange, Entity target) { // methode qui calcule si la target est dans la range
		
		return false;
	}
	
	public void attak() { // methode pour attaquer une target
		
	}
	
	public void lookForTarget(Position position, int range) {
		
	}
	
	//getter setter
	public int getElementCount() {
		return elementCount;
	}
	public void setElementCount(int elementCount) {
		this.elementCount = elementCount;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getIsProducing() {
		return isProducing;
	}

	public void setIsProducing(int isProducing) {
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
	
}
