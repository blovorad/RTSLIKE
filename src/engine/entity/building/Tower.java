package engine.entity.building;

import engine.Position;
import engine.entity.unit.Unit;
import engine.map.Tile;

public class Tower extends AttackBuilding{

	public Tower(Position position, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.setDamage(5);
		this.setAttakRange(80);
		this.setAttackSpeed(10);
		this.setAttackCooldown(this.getAttackSpeed());
	}
	
}
