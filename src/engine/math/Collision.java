package engine.math;

import java.awt.Rectangle;

import javax.swing.JPanel;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;

import engine.Camera;
import engine.Entity;
import engine.Position;
import engine.Ressource;
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
	
	public static boolean collidePanel(JPanel panel, int x, int y) {
		Rectangle r1 = new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
		Rectangle r2 = new Rectangle(x, y, 1, 1);
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean collidePanelMinimap(JPanel panel, int x, int y) {
		Rectangle r1 = new Rectangle(panel.getX() + panel.getWidth() / 2, panel.getY(), panel.getWidth(), panel.getHeight());
		Rectangle r2 = new Rectangle(x, y, 1, 1);
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
	
	public static boolean collideRessource(Worker worker, Ressource  ressource) {
		Rectangle r1 = new Rectangle(worker.getPosition().getX(), worker.getPosition().getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		Rectangle r2 = new Rectangle(ressource.getPosition().getX() - 7*GameConfiguration.TILE_SIZE, ressource.getPosition().getY() - 7*GameConfiguration.TILE_SIZE, 14*GameConfiguration.TILE_SIZE, 14*GameConfiguration.TILE_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	public static boolean collideFogEntity(int xTab, int yTab, int widthTab, int heightTab, Entity botEntity) {
		int tileSize = GameConfiguration.TILE_SIZE;
		int entitySize = GameConfiguration.TILE_SIZE;
		if(botEntity.getId() == EntityConfiguration.CAVALRY) {
			entitySize = EntityConfiguration.CAVALRY_SIZE;
		}
		else if(botEntity.getId() <= EntityConfiguration.WORKER) {
			entitySize = EntityConfiguration.UNIT_SIZE;
		}
		
		Rectangle r1 = new Rectangle(xTab * tileSize, yTab * tileSize, widthTab * tileSize - xTab * tileSize, heightTab * tileSize - yTab * tileSize);
		Rectangle r2 = new Rectangle(botEntity.getPosition().getX(), botEntity.getPosition().getY(), entitySize, entitySize);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean collideAttack(Entity target , Unit attacker) 
	{
		int sizeAttacker;
		int sizeTarget;
		
		if(target.getId() >= EntityConfiguration.FORGE && target.getId() <= EntityConfiguration.ARCHERY)
		{
			sizeTarget = GameConfiguration.TILE_SIZE;
		}
		else
		{
			if(target.getId() == EntityConfiguration.CAVALRY)
			{
				sizeTarget = EntityConfiguration.CAVALRY_SIZE;
			}
			else
			{
				sizeTarget = EntityConfiguration.UNIT_SIZE;
			}
		}
		
		if(target.getId() == EntityConfiguration.CAVALRY)
		{
			sizeAttacker = EntityConfiguration.CAVALRY_SIZE;
		}
		else
		{
			sizeAttacker = EntityConfiguration.UNIT_SIZE;
		}
		
		Rectangle r1 = new Rectangle(target.getPosition().getX(), target.getPosition().getY(), sizeTarget, sizeTarget);
		Rectangle r2 = new Rectangle(attacker.getPosition().getX() - attacker.getAttackRange()/2*sizeAttacker, attacker.getPosition().getY() - attacker.getAttackRange()/2*sizeAttacker, attacker.getAttackRange()*2*sizeAttacker, attacker.getAttackRange()*2*sizeAttacker);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
}
