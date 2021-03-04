package engine.entity.building;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import engine.Entity;
import engine.Position;
import engine.entity.unit.Unit;
import engine.map.Tile;

public abstract class AttackBuilding extends Entity{

	private int sightRange;
	private int attakRange;
	private int attackSpeed;
	private int damage;
	private int attackCooldown;
	private Tile tile;
	
	public AttackBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile) {
		super(100, hpMax, description , position, id, faction);
		this.setTile(tile);
	}
	
	public void update(List<Unit> units) {
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
	
	public  void attak() {
		this.getTarget().damage(damage);
	}
	
	public void lookForTarget(List<Unit> units)
	{
		AbstractMap<Double,Unit> unitsInRange = new HashMap<Double,Unit>();
		for(Unit unit : units) {
			if(unit.getFaction() != this.getFaction()) {
				if(this.isInRange(unit)) {
					unitsInRange.put(calculateDistance(this, unit), unit); // met toutes les units inRange dans une map
				}
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

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(int attackCooldown) {
		this.attackCooldown = attackCooldown;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
}
