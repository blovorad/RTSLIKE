package engine.entity.building;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import engine.Entity;
import engine.Position;
import engine.entity.unit.Unit;
import engine.manager.GraphicsManager;
import engine.map.Tile;

/**
 * Class etendant Entity qui definis les batiments capable d'attaquer.
 * @author Maxime Grodet
 * @see Entity
 */
public abstract class AttackBuilding extends Building{

	/**
	 * Entier definissant la taille du champ de vision du batiment.
	 */
	private int sightRange;
	
	/**
	 * Entier definissant la distance a laquelle le batiment peut attaquer.
	 */
	private int attakRange;
	
	/**
	 * Entier definissantla vitesse d'attaque du batiment.
	 */
	private int attackSpeed;
	
	/**
	 * Entier definissant les dommages qu'inflige le batiment a chaque attaque.
	 */
	private int damage;
	
	/**
	 * Entier definissant le compteur de temps entre chaque attaque.
	 */
	private int attackCooldown;
	
	/**
	 * Case ou se situe le batiment.
	 */
	private Tile tile;
	
	private Entity target;
	
	/**
	 * Constructeur de l'archery ou tous ses parametres y sont definis.
	 * 
	 * @param position Position du batiment.
	 * @param id Id du batiment.
	 * @param description Desciption du batiment. 
	 * @param hpMax Quantite d'hp maximum du batiment.
	 * @param faction Faction du batiment.
	 * @param tile Case ou est le batiment.
	 * @param sightRange Taille du chanmp de vision du batiment.
	 * @param graphicsManager Lien vers le GraphicsManager
	 */ 
	public AttackBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile, int sightRange, GraphicsManager graphicsManager) {
		super(hpMax, hpMax, description, position, id, faction, sightRange, 0, graphicsManager);
		this.setTile(tile);
	}
	
	/**
	 * Methode apelle dans EntitiesManager et executee a chaque iteration du jeu.
	 * <p>
	 * Elle gere le comportement du batiment et met a jour ses variables.
	 * @param units Liste des unites presente sur la map.
	 * @see Unit
	 */
	public void update(List<Unit> units) {
		super.update();
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
						//System.out.println(this.getDescription() + " attacked " + this.getTarget().getDescription() + " for " + this.getDamage() + " !");
						//System.out.println(this.getTarget().getDescription() + " is now " + this.getTarget().getHp());
					}
				}
			}
			else {
				this.setTarget(null);
			}
		}else {
			lookForTarget(units);
		}
	}
	
	/**
	 * Methode qui gere l'attque du batiment.
	 */
	public  void attak() {
		this.getTarget().damage(damage);
	}
	
	/**
	 * Methode qui gere la recherche de cible pour le batiment.
	 * @param units Liste des unites presente sur la map.
	 * @see Unit
	 */
	public void lookForTarget(List<Unit> units) {
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
	
	/**
	 * Methode qui calcule si la cible est dans la rayon d'attaque du batiment.
	 * @param target Cible du batiment.
	 * @return {@code true} si elle la target est a distance d'attque sinon {@code false}.
	 * @see Entity
	 */
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
	
	/**
	 * Methode qui calcule la distance entre deux position.
	 * @param ent1 Entite de reference.
	 * @param ent2 Entite a comparer.
	 * @return La distance entre les deux entite.
	 * @see Position
	 */
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
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public Entity getTarget() {
		return this.target;
	}
	
}