package engine.entity.building;

import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
/**
 * Methode qui definie une tour. Une tour attaque les unite ennemeie autour d'elle.
 * @author maxime
 * @see AttackBuilding
 */
public class Tower extends AttackBuilding{

	/**
	 * Constructeur de la tour ou tous ses parametres sont definis.
	 * @param position Position de la tour.
	 * @param id Id de la tour.
	 * @param description Desciption de la tour.
	 * @param hpMax Point de vie maximale de la tour.
	 * @param faction Faction de la tour.
	 * @param tile Case ou se situe la tour.
	 * @param sightRange Champ de vision de la tour.
	 * @param damage Damage qu'inflige la tour.
	 * @param attackSpeed Vitesse d'attaque de la tour.
	 * @param attackRange Rayon d'attaque de la tour.
	 * @param graphicsManager Lien vers le graphicsManager.
	 */
	public Tower(Position position, int id, String description, int hpMax, int faction, Tile tile, int sightRange, int damage, int attackSpeed, int attackRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, sightRange, graphicsManager);
		this.setDamage(damage);
		this.setAttakRange(attackRange);
		this.setAttackSpeed(attackSpeed);
		this.setAttackCooldown(this.getAttackSpeed());
	}
	
}