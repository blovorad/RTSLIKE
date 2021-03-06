package engine.math;

import java.awt.Rectangle;
import java.awt.Toolkit;

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
 * this class is never instanciate and contain all collision function we need in the game
 * @author gautier
 */

public class Collision {
	
	/**
	 * require to collide when you use A*
	 * @param tile
	 * @param unit
	 * @return
	 */
	public static boolean collidePath(Position tile, Position unit) {
		Rectangle r1 = new Rectangle(tile.getX(), tile.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		Rectangle r2 = new Rectangle(unit.getX(), unit.getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * require to see if selectionRect collide with building
	 * @param p
	 * @param r
	 * @param camera
	 * @return
	 */
	public static boolean collide(Position p, SelectionRect r, Camera camera) {
		Rectangle r1 = new Rectangle(p.getX(), p.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		Rectangle r2 = new Rectangle(r.getX() + camera.getX(), r.getY() + camera.getY(), r.getW(), r.getH());
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * require to see if selectionRect collide with unit
	 * @param p
	 * @param r
	 * @param camera
	 * @return boolean
	 */
	public static boolean collideUnit(Position p, SelectionRect r, Camera camera) {
		Rectangle r1 = new Rectangle(p.getX(), p.getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		Rectangle r2 = new Rectangle(r.getX() + camera.getX(), r.getY() + camera.getY(), r.getW(), r.getH());
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * require to see if worker collide with storage building
	 * @param p
	 * @param r
	 * @return boolean
	 */
	public static boolean collideUnit(Position p, Worker r) {
		Rectangle r1 = new Rectangle(p.getX(), p.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		Rectangle r2 = new Rectangle(r.getPosition().getX(), r.getPosition().getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * to see if mouse is collide with ressource or a storage building
	 * @param mouse
	 * @param ressource
	 * @return boolean
	 */
	public static boolean collideEntity(Position mouse, Position ressource) {
		Rectangle r1 = new Rectangle(mouse.getX(), mouse.getY(), 1, 1);
		Rectangle r2 = new Rectangle(ressource.getX(), ressource.getY(), GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * use to see if mouse clic collide with a panel
	 * @param panel
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public static boolean collidePanel(JPanel panel, int x, int y) {
		Rectangle r1 = new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
		Rectangle r2 = new Rectangle(x, y, 1, 1);
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * to see if mouse clic collide specificaly with minimapPanel
	 * @param panel
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public static boolean collidePanelMinimap(JPanel panel, int x, int y) {
		Rectangle r1 = new Rectangle(panel.getX() + panel.getWidth() / 2, panel.getY(), panel.getWidth(), panel.getHeight());
		Rectangle r2 = new Rectangle(x, y, 1, 1);
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * it is use when a worker search a new ressource to gather
	 * @param worker
	 * @param ressource
	 * @return
	 */
	public static boolean collideRessource(Worker worker, Ressource  ressource) {
		if(worker.getFaction() == EntityConfiguration.PLAYER_FACTION) {
			
		}
		Rectangle r1 = new Rectangle(worker.getPosition().getX(), worker.getPosition().getY(), EntityConfiguration.UNIT_SIZE, EntityConfiguration.UNIT_SIZE);
		Rectangle r2 = new Rectangle(ressource.getPosition().getX() - 7*GameConfiguration.TILE_SIZE, ressource.getPosition().getY() - 7*GameConfiguration.TILE_SIZE, 14*GameConfiguration.TILE_SIZE, 14*GameConfiguration.TILE_SIZE);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * used to see if fog collide with a entity
	 * @param xTab
	 * @param yTab
	 * @param widthTab
	 * @param heightTab
	 * @param entity
	 * @return
	 */
	public static boolean collideFogEntity(int xTab, int yTab, int widthTab, int heightTab, Entity entity) {
		int tileSize = GameConfiguration.TILE_SIZE;
		int entitySize = GameConfiguration.TILE_SIZE;
		
		if(entity.getId() == EntityConfiguration.CAVALRY) {
			entitySize = EntityConfiguration.CAVALRY_SIZE;
		}
		else if(entity.getId() <= EntityConfiguration.WORKER) {
			entitySize = EntityConfiguration.UNIT_SIZE;
		}
		
		Rectangle r1 = new Rectangle(xTab * tileSize, yTab * tileSize, widthTab * tileSize - xTab * tileSize, heightTab * tileSize - yTab * tileSize);
		Rectangle r2 = new Rectangle(entity.getPosition().getX(), entity.getPosition().getY(), entitySize, entitySize);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * it is use for spacialisation of sound if false the sound won't be play
	 * @param entity
	 * @param camera
	 * @return
	 */
	public static boolean collideForFx(Entity entity, Camera camera) {
		int entitySize = EntityConfiguration.UNIT_SIZE;
		
		if(entity.getId() == EntityConfiguration.CAVALRY) {
			entitySize = EntityConfiguration.CAVALRY_SIZE;
		}
		
		int width = GameConfiguration.WINDOW_WIDTH;
		int height = GameConfiguration.WINDOW_HEIGHT;
		if(GameConfiguration.launchInFullScreen) {
			width = Toolkit.getDefaultToolkit().getScreenSize().width;
			height = Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		
		Rectangle r1 = new Rectangle(camera.getX(), camera.getY(), width, height);
		Rectangle r2 = new Rectangle(entity.getPosition().getX(), entity.getPosition().getY(), entitySize, entitySize);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * use to see if a unit is not in fog
	 * @param xTab
	 * @param yTab
	 * @param widthTab
	 * @param heightTab
	 * @param entity
	 * @return
	 */
	public static boolean collideFogUnit(int xTab, int yTab, int widthTab, int heightTab, Unit entity) {
		int tileSize = GameConfiguration.TILE_SIZE;
		int entitySize = GameConfiguration.TILE_SIZE;
		
		if(entity.getId() == EntityConfiguration.CAVALRY) {
			entitySize = EntityConfiguration.CAVALRY_SIZE;
		}
		else if(entity.getId() <= EntityConfiguration.WORKER) {
			entitySize = EntityConfiguration.UNIT_SIZE;
		}
		
		Rectangle r1 = new Rectangle(xTab * tileSize, yTab * tileSize, widthTab * tileSize - xTab * tileSize, heightTab * tileSize - yTab * tileSize);
		Rectangle r2 = new Rectangle(entity.getPosition().getX(), entity.getPosition().getY(), entitySize, entitySize);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * use to see if a entity is in range of a attacker
	 * @param target
	 * @param attacker
	 * @return
	 */
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
		Rectangle r2 = new Rectangle(attacker.getPosition().getX() - attacker.getAttackRange() - sizeAttacker / 6, attacker.getPosition().getY() - sizeAttacker / 6 - attacker.getAttackRange(), attacker.getAttackRange() * 2 + sizeAttacker, attacker.getAttackRange() * 2 + sizeAttacker);
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * use to see if a entity is in sightRange of an attackers
	 * @param target
	 * @param attacker
	 * @return
	 */
	public static boolean collideVision(Entity target , Unit attacker) 
	{
		//int sizeAttacker;
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
		
		/*if(target.getId() == EntityConfiguration.CAVALRY)
		{
			sizeAttacker = EntityConfiguration.CAVALRY_SIZE;
		}
		else
		{
			sizeAttacker = EntityConfiguration.UNIT_SIZE;
		}*/
		
		Rectangle r1 = new Rectangle(target.getPosition().getX(), target.getPosition().getY(), sizeTarget, sizeTarget);
		Rectangle r2 = new Rectangle(attacker.getPosition().getX() - attacker.getSightRange()/2, attacker.getPosition().getY() - attacker.getSightRange()/2, attacker.getSightRange(), attacker.getSightRange());
		//System.out.println("ma range est: " + r2.getHeight());
		
		if(r1.intersects(r2)) {
			return true;
		}
		
		return false;
	}
}