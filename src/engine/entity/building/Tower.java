package engine.entity.building;

import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
/**
 * 
 * @author maxime
 *
 */
public class Tower extends AttackBuilding{

	public Tower(Position position, int id, String description, int hpMax, int faction, Tile tile, int sightRange, int damage, int attackSpeed, int attackRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, sightRange, graphicsManager);
		this.setDamage(damage);
		this.setAttakRange(attackRange);
		this.setAttackSpeed(attackSpeed);
		this.setAttackCooldown(this.getAttackSpeed());
	}
	
}
