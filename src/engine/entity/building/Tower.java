package engine.entity.building;

import java.awt.image.BufferedImage;

import engine.Position;
import engine.map.Tile;
/**
 * 
 * @author maxime
 *
 */
public class Tower extends AttackBuilding{

	public Tower(Position position, int id, String description, int hpMax, int faction, Tile tile, BufferedImage texture, int sightRange, int damage, int attackSpeed, int attackRange) {
		super(position, id, description, hpMax, faction, tile, texture, sightRange);
		this.setDamage(damage);
		this.setAttakRange(attackRange);
		this.setAttackSpeed(attackSpeed);
		this.setAttackCooldown(this.getAttackSpeed());
	}
	
}
