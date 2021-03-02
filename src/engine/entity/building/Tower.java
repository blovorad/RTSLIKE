package engine.entity.building;

import engine.Position;
import engine.Unit;

public class Tower extends AttackBuilding{

	public Tower(Position position, int id, String description, int hpMax) {
		super(position, id, description, hpMax);
		this.setDamage(5);
		this.setAttakRange(80);
		this.setAttackSpeed(10);
		this.setAttackCooldown(this.getAttackSpeed());
	}
	
}
