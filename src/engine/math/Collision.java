package engine.math;

import java.awt.Rectangle;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;

import engine.Camera;
import engine.Entity;
import engine.Position;
import engine.entity.unit.Unit;
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
	
	public static boolean collideEntity(Position mouse, Position ressource) {
		Rectangle r1 = new Rectangle(mouse.getX(), mouse.getY(), 1, 1);
		Rectangle r2 = new Rectangle(ressource.getX(), ressource.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean collideEntity(Unit unit, Position destination) {
		Rectangle r1 = new Rectangle(unit.getPosition().getX(), unit.getPosition().getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		Rectangle r2 = new Rectangle(destination.getX(), destination.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean collideEntity(Unit unit, Entity  target) {
		Rectangle r1 = new Rectangle(unit.getPosition().getX(), unit.getPosition().getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		Rectangle r2 = new Rectangle(target.getPosition().getX(), target.getPosition().getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
}
