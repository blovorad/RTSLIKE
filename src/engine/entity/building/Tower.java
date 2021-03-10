package engine.entity.building;

import java.awt.image.BufferedImage;

import engine.Position;
import engine.map.Tile;

public class Tower extends AttackBuilding{

	public Tower(Position position, int id, String description, int hpMax, int faction, Tile tile, BufferedImage texture, int sightRange) {
		super(position, id, description, hpMax, faction, tile, texture, sightRange);
		this.setDamage(5);
		this.setAttakRange(80);
		this.setAttackSpeed(10);
		this.setAttackCooldown(this.getAttackSpeed());
	}
	
}
