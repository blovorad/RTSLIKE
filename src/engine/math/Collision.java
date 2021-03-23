package engine.math;

import java.awt.Rectangle;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;

import engine.Camera;
import engine.Position;
import engine.entity.unit.Worker;

/**
 * 
 * @author gautier
 *
 */

public class Collision {
	
	public static boolean collide(Position p, SelectionRect r, Camera camera) {
		Rectangle r1 = new Rectangle(p.getX(), p.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		Rectangle r2 = new Rectangle(r.getX() + camera.getX(), r.getY() + camera.getY(), r.getW(), r.getH());
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean collideUnit(Position p, SelectionRect r, Camera camera) {
		Rectangle r1 = new Rectangle(p.getX(), p.getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		Rectangle r2 = new Rectangle(r.getX() + camera.getX(), r.getY() + camera.getY(), r.getW(), r.getH());
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean collideUnit(Position p, Worker r) {
		Rectangle r1 = new Rectangle(p.getX(), p.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		Rectangle r2 = new Rectangle(r.getPosition().getX(), r.getPosition().getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
}
