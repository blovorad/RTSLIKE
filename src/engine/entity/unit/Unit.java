package engine.entity.unit;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Position;
import engine.Speed;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.FogCase;
import engine.map.Map;
import engine.map.Tile;
import engine.math.Collision;
import engine.math.Node;
import engine.math.Path;

/**
 *
 * création:16/02/2021
 * @author Girard 
 * @version:16/02/2021
 * 
 */

public class Unit extends Entity
{
	/**
	 * targetUnit, l'unite ciblee par l'unité
	 * currentAction, l'action en cours
	 * attackRange, la range d'attaque  de l'unite
	 * attackSpeed, la vitesse d'attaque de l'unite
	 * maxSpeed, la vitesse de deplacement de l'unite
	 * damage, les dégâts qu'inflige l'unite
	 * armor, l'armure de l'unite
	 * timer, le timer avant lequ'elle l'unite fait sont action
	 * state, l'état de la troupe
	 */
	private Unit targetUnit;
	private Position finalPosition;
	private Node finalNode;
	private List<Position> destination = new ArrayList<Position>();
	
	private int currentAction;
	private int attackRange;
	private int attackSpeed;
	private int maxSpeed;
	private int damage;
	private int armor;
	private int timer;
	private int state;
	
	private Speed speed;
	private boolean generatePath;
	
	public Unit(int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int armor, Position position, int id, String description, Position destination, int hpMax, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager, int state)
	{
		super(hp, hpMax, description, position, id, faction, sightRange, maxFrame, graphicsManager);
		
		this.currentAction = EntityConfiguration.IDDLE;
		this.attackRange = attackRange;
		this.setAttackSpeed(attackSpeed);
		this.maxSpeed = maxSpeed;
		this.damage = damage;
		this.armor = armor;
		this.setDestination(destination);
		this.speed = new Speed(0, 0);
		this.timer = this.attackSpeed;
		this.state = state;
		this.finalPosition = null;
		this.generatePath = false;
		
		if(destination != null) {
			//System.out.println("calcul");
			this.setFinalDestination(destination);
		}
	}
	
	public void setFinalDestination(Position position) {
		this.finalPosition = position;
		finalNode = new Node(new Position(position.getX() / GameConfiguration.TILE_SIZE, position.getY() / GameConfiguration.TILE_SIZE));
		this.setDestination(null);
		generatePath = true;
		//System.out.println("GENERATION PATH : " + generatePath);
	}
	
	public void move(float vx, float vy)
	{
		if(vx > 0.0 && vx < 1.0) {
			vx = 1;
		}
		else if(vx < 0.0 && vx > -1.0) {
			vx = -1;
		}
		
		if(vy > 0.0 && vy < 1.0) {
			vy = 1;
		}
		else if(vy < 0.0 && vy > -1.0) {
			vy = -1;
		}
		
		this.getSpeed().setVx(vx);
		this.getSpeed().setVy(vy);
		if(vx != 0 || vy != 0) {
			if(vx < 0) {
				this.getAnimation().setDirection(GameConfiguration.LEFT);
			}
			else {
				this.getAnimation().setDirection(GameConfiguration.RIGHT);
			}
		}
	}
	
	/**
	 * Fonction qui implemente l'attaque d'une unite, qui inflige les damages à l'unité selectionnée dans target Unit
	 * @param damage, le nombre de degat a inflige a l'unite
	 */
	
	public void attack(int damage)
	{
		if(this.getTarget() != null)
		{
			//System.out.println("test");
			if(this.timer <= 0)
			{
				if(this.targetUnit != null && this.targetUnit.getHp() >= 0)
				{
					if(damage > this.targetUnit.getArmor()) {
						this.targetUnit.damage((damage - this.targetUnit.getArmor()));
					}
					this.checkTarget();
				}
				
				else if(this.getTarget().getHp() >= 0)
				{
					this.getTarget().damage(damage);
					this.checkTarget();
				}
				this.timer = this.attackSpeed;
			}
			this.timer--;
		}
	}
	

	/**
	 * Fonction qui permet de vérifier si la cible na plus de vie et si c'est le cas l'enlève de target
	 */
	public void checkTarget()
	{
		if(this.getTarget() != null && this.getTarget().getHp() <= 0)
		{
			this.setTarget(null);
			this.targetUnit = null;
			this.setDestination(null);
		}
	}
	
	public int getDamage() 
	{
		return damage;
	}

	public void setDamage(int damage) 
	{
		this.damage = damage;
	}

	public int getArmor() 
	{
		return armor;
	}

	public void setArmor(int armor) 
	{
		this.armor = armor;
	}

	public int getAttackRange() 
	{
		return attackRange;
	}

	public void setAttackRange(int attackRange) 
	{
		this.attackRange = attackRange;
	}

	public int getCurrentAction() 
	{
		return  currentAction;
	}

	public void setCurrentAction(int currentAction) 
	{
		this.currentAction = currentAction;
	}

	public Speed getSpeed() 
	{
		return speed;
	}

	public void setSpeed(Speed speed) 
	{
		this.speed = speed;
	}
	
	public void calculateSpeed(Position p){	
		this.setDestination(new Position(p.getX(), p.getY()));
		double angle = Math.atan2( (p.getY() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getY() + GameConfiguration.TILE_SIZE /2), (p.getX() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getX() + GameConfiguration.TILE_SIZE));
		this.move((float)(this.maxSpeed * Math.cos(angle)), (float)(this.maxSpeed * Math.sin(angle)));
		//System.out.println("calcul d'une vitesse");
	}
	
	public void generatePath(Map map, Fog fog) {
		destination.clear();
		Position p = this.getPosition();
		Path path = new Path();
		Node currentNode = new Node(new Position((p.getX() + EntityConfiguration.UNIT_SIZE / 2) / GameConfiguration.TILE_SIZE, (p.getY() + EntityConfiguration.UNIT_SIZE / 2) / GameConfiguration.TILE_SIZE));
		List<Node> nodes = new ArrayList<Node>();
		Tile[][] tiles = map.getTiles();
		//System.out.println("Pos moi : " + currentNode.getPosition().getX() + "," + currentNode.getPosition().getY());
		//System.out.println("POS dest : " + finalNode.getPosition().getX() + "," + finalNode.getPosition().getY());
		
		if(fog != null) {
			boolean[][] brouillard = fog.getFog();
			while(currentNode != finalNode && currentNode != null) {// && brouillard[currentNode.getPosition().getY()][currentNode.getPosition().getX()] == false) {
				//System.out.println("ITERATION");
				//int maxX = currentNode.getPosition().getX() + 2;
				//int maxY = currentNode.getPosition().getY() + 2;
				Position bis = currentNode.getPosition();
				if(bis.getY() - 1 >= 0 && bis.getX() >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY() - 1][bis.getX()].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX(), bis.getY() - 1))) {
							Node node = new Node(new Position(bis.getX(), bis.getY() - 1));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY() - 1][bis.getX()].isSolid() == false) {
							Node node = new Node(new Position(bis.getX(), bis.getY() - 1));
							nodes.add(node);
						}
					}
				}
				if(bis.getY() >= 0 && bis.getX() - 1 >= 0 && bis.getY() < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY()][bis.getX() - 1].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX() - 1, bis.getY()))) {
							Node node = new Node(new Position(bis.getX() - 1, bis.getY()));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY()][bis.getX() - 1].isSolid() == false) {
							Node node = new Node(new Position(bis.getX() - 1, bis.getY()));
							nodes.add(node);
						}
					}
				}
				if(bis.getY() >= 0 && bis.getX() + 1 >= 0 && bis.getY() < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY()][bis.getX() + 1].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX() + 1, bis.getY()))) {
							Node node = new Node(new Position(bis.getX() + 1, bis.getY()));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY()][bis.getX() + 1].isSolid() == false) {
							Node node = new Node(new Position(bis.getX() + 1, bis.getY()));
							nodes.add(node);
						}
					}
				}
				if(bis.getY() + 1 >= 0 && bis.getX() >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY() + 1][bis.getX()].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX(), bis.getY() + 1))) {
							Node node = new Node(new Position(bis.getX(), bis.getY() + 1));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY() + 1][bis.getX()].isSolid() == false) {
							Node node = new Node(new Position(bis.getX(), bis.getY() + 1));
							nodes.add(node);
						}
					}
				}
				
				/*if(bis.getY() - 1 >= 0 && bis.getX() - 1 >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() - 1][bis.getX() - 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
				if(bis.getY() + 1 >= 0 && bis.getX() - 1 >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() + 1][bis.getX() - 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() + 1));
						nodes.add(node);			
					}
				}
				if(bis.getY() - 1 >= 0 && bis.getX() + 1 >= 0 && bis.getY()  - 1 < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() - 1][bis.getX() + 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
				if(bis.getY() + 1 >= 0 && bis.getX() + 1 >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() + 1][bis.getX() + 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() + 1));
						nodes.add(node);
					}
				}*/
				/*for(int j = currentNode.getPosition().getY() - 1; j < maxY; j++) {
					for(int i = currentNode.getPosition().getX() - 1; i < maxX; i++) {
						if(tiles[j][i].isSolid() == false) {
							if(j != bis.getY() / GameConfiguration.TILE_SIZE && i != bis.getX() / GameConfiguration.TILE_SIZE) {
								Node node = new Node(new Position(i, j));
								nodes.add(node);
							}
						}
					}
				}*/
				//System.out.println("FINAL NODE POS : " + finalNode.getPosition().getX() + "," + finalNode.getPosition().getY());
				currentNode = path.generatePath(nodes, finalNode, currentNode);
				//System.out.println("currentNOde pos :" + currentNode.getPosition().getX() + "," + currentNode.getPosition().getY());
				nodes.clear();
			}
		}
		else {
			while(currentNode != finalNode && currentNode != null) {// && brouillard[currentNode.getPosition().getY()][currentNode.getPosition().getX()] == false) {
				//System.out.println("ITERATION");
				//int maxX = currentNode.getPosition().getX() + 2;
				//int maxY = currentNode.getPosition().getY() + 2;
				Position bis = currentNode.getPosition();
				if(bis.getY() - 1 >= 0 && bis.getX() >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY() - 1][bis.getX()].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX(), bis.getY() - 1))) {
							Node node = new Node(new Position(bis.getX(), bis.getY() - 1));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY() - 1][bis.getX()].isSolid() == false) {
							Node node = new Node(new Position(bis.getX(), bis.getY() - 1));
							nodes.add(node);
						}
					}
				}
				if(bis.getY() >= 0 && bis.getX() - 1 >= 0 && bis.getY() < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY()][bis.getX() - 1].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX() - 1, bis.getY()))) {
							Node node = new Node(new Position(bis.getX() - 1, bis.getY()));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY()][bis.getX() - 1].isSolid() == false) {
							Node node = new Node(new Position(bis.getX() - 1, bis.getY()));
							nodes.add(node);
						}
					}
				}
				if(bis.getY() >= 0 && bis.getX() + 1 >= 0 && bis.getY() < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY()][bis.getX() + 1].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX() + 1, bis.getY()))) {
							Node node = new Node(new Position(bis.getX() + 1, bis.getY()));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY()][bis.getX() + 1].isSolid() == false) {
							Node node = new Node(new Position(bis.getX() + 1, bis.getY()));
							nodes.add(node);
						}
					}
				}
				if(bis.getY() + 1 >= 0 && bis.getX() >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() < GameConfiguration.COLUMN_COUNT) {
					if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
						if(tiles[bis.getY() + 1][bis.getX()].isSolid() == false || finalNode.getPosition().equals(new Position(bis.getX(), bis.getY() + 1))) {
							Node node = new Node(new Position(bis.getX(), bis.getY() + 1));
							nodes.add(node);
						}
					}
					else {
						if(tiles[bis.getY() + 1][bis.getX()].isSolid() == false) {
							Node node = new Node(new Position(bis.getX(), bis.getY() + 1));
							nodes.add(node);
						}
					}
				}
				
				/*if(bis.getY() - 1 >= 0 && bis.getX() - 1 >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() - 1][bis.getX() - 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
				if(bis.getY() + 1 >= 0 && bis.getX() - 1 >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() + 1][bis.getX() - 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() + 1));
						nodes.add(node);			
					}
				}
				if(bis.getY() - 1 >= 0 && bis.getX() + 1 >= 0 && bis.getY()  - 1 < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() - 1][bis.getX() + 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
				if(bis.getY() + 1 >= 0 && bis.getX() + 1 >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
					if(tiles[bis.getY() + 1][bis.getX() + 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() + 1));
						nodes.add(node);
					}
				}*/
				/*for(int j = currentNode.getPosition().getY() - 1; j < maxY; j++) {
					for(int i = currentNode.getPosition().getX() - 1; i < maxX; i++) {
						if(tiles[j][i].isSolid() == false) {
							if(j != bis.getY() / GameConfiguration.TILE_SIZE && i != bis.getX() / GameConfiguration.TILE_SIZE) {
								Node node = new Node(new Position(i, j));
								nodes.add(node);
							}
						}
					}
				}*/
				//System.out.println("FINAL NODE POS : " + finalNode.getPosition().getX() + "," + finalNode.getPosition().getY());
				currentNode = path.generatePath(nodes, finalNode, currentNode);
				//System.out.println("currentNOde pos :" + currentNode.getPosition().getX() + "," + currentNode.getPosition().getY());
				nodes.clear();
			}
		}
		
		//System.out.println("On reverse");
		if(currentNode != null) {
			destination = path.reversePath(currentNode);
			for(Position pos : destination) {
				//System.out.println("Les position : " + pos.getX() / GameConfiguration.TILE_SIZE + "," + pos.getY() / GameConfiguration.TILE_SIZE);
			}
		}
		else {
			finalPosition = null;
			finalNode = null;
			this.setDestination(null);
			destination.clear();
		}
 		generatePath = false;
 		//System.out.println("GENERATION PATH : " + generatePath);
	}

	/**
	 * Mets a jour toutes les unites, permet leurs deplacements, leur attaque, et toutes leurs actions
	 * @param units, tableaux des unites presents sur la map
	 * @param playerFog
	 * @param map
	 */
	public void update(List<Unit> units, Fog playerFog, Map map){
		super.update();
		Position p = this.getPosition();
		
		if(generatePath) {
			generatePath(map, playerFog);
		}
		
		/*if(playerFog == null) {
		
			if(this.getTarget() != null  && this.getDestination() != null && this.currentAction != EntityConfiguration.ATTACK && !(this.getTarget().getPosition().equals(this.getDestination())))
			{
				calculateSpeed(this.getTarget().getPosition());
			}
			else
			{	
				if(this.getDestination() != null)
				{
					if(!this.getPosition().equals(this.getDestination()))
					{
						if( (this.getPosition().getX() < this.getDestination().getX() && speed.getVx() < 0) || (this.getPosition().getX() > this.getDestination().getX() && speed.getVx() > 0) )
						{
							this.getPosition().setX(this.getDestination().getX());
							speed.setVx(0);
						}
						if( (this.getPosition().getY() < this.getDestination().getY() && speed.getVy() < 0) || (this.getPosition().getY() > this.getDestination().getY() && speed.getVy() > 0) )
						{
							this.getPosition().setY(this.getDestination().getY());
							speed.setVy(0);
						}
						if( this.getPosition().equals(this.getDestination()))
						{
							this.setDestination(null);
						}
					}
					else {
						this.speed.reset();
						this.setDestination(null);
					}
				}
				else
				{
					this.setDestination(null);
					this.speed.reset();
				}
			}
		}*/
		//else {
			if(finalNode != null && finalPosition != null) {
				if(this.getDestination() == null) {
					this.calculateSpeed(destination.get(0));
				}
				else {	
					if(!this.getPosition().equals(this.getDestination()))
					{
						if( (this.getPosition().getX() < this.getDestination().getX() && speed.getVx() < 0) || (this.getPosition().getX() > this.getDestination().getX() && speed.getVx() > 0) )
						{
							this.getPosition().setX(this.getDestination().getX());
							speed.setVx(0);
						}
						if( (this.getPosition().getY() < this.getDestination().getY() && speed.getVy() < 0) || (this.getPosition().getY() > this.getDestination().getY() && speed.getVy() > 0) )
						{
							this.getPosition().setY(this.getDestination().getY());
							speed.setVy(0);
						}
						if( this.getPosition().equals(this.getDestination()))
						{
							//System.out.println("remove destination 1");
							destination.remove(0);
							if(destination.isEmpty() == false) {
								this.calculateSpeed(destination.get(0));
							}
							else {
								this.setDestination(null);
								Position posFornode = new Position(p.getX() / GameConfiguration.TILE_SIZE, p.getY() / GameConfiguration.TILE_SIZE);
								if(finalNode.getPosition().equals(posFornode)) {
									finalNode = null;
									this.getSpeed().reset();
									this.calculateSpeed(finalPosition);
									finalPosition = null;
								}
								else {
									//System.out.println("GENERATION PATH : " + generatePath);
									generatePath = true;
								}
							}
						}
					}
					else {
						destination.remove(0);
						//System.out.println("remove destination 2");
						if(destination.isEmpty() == false) {
							this.calculateSpeed(destination.get(0));
						}
						else {
							this.setDestination(null);
							Position posFornode = new Position(p.getX() / GameConfiguration.TILE_SIZE, p.getY() / GameConfiguration.TILE_SIZE);
							if(finalNode.getPosition().equals(posFornode)) {
								finalNode = null;
								this.getSpeed().reset();
								this.calculateSpeed(finalPosition);
								finalPosition = null;
							}
							else {
								//System.out.println("GENERATION PATH : " + generatePath);
								generatePath = true;
							}
						}
					}
				}
			}
			else if(finalNode == null && this.getDestination() != null) {
				if(!this.getPosition().equals(this.getDestination()))
				{
					if( (this.getPosition().getX() < this.getDestination().getX() && speed.getVx() < 0) || (this.getPosition().getX() > this.getDestination().getX() && speed.getVx() > 0) )
					{
						this.getPosition().setX(this.getDestination().getX());
						speed.setVx(0);
					}
					if( (this.getPosition().getY() < this.getDestination().getY() && speed.getVy() < 0) || (this.getPosition().getY() > this.getDestination().getY() && speed.getVy() > 0) )
					{
						this.getPosition().setY(this.getDestination().getY());
						speed.setVy(0);
					}
					if( this.getPosition().equals(this.getDestination()))
					{
						this.setDestination(null);
					}
				}
				else {
					this.speed.reset();
					this.setDestination(null);
				}
			}
		//}
		
		this.getPosition().setX(this.getPosition().getX() + (int)this.getSpeed().getVx());
		this.getPosition().setY(this.getPosition().getY() + (int)this.getSpeed().getVy());
		
		if((this.getAnimation().getFrameState() == EntityConfiguration.WALK || this.getAnimation().getFrameState() == EntityConfiguration.ATTACK) && this.getSpeed().getVx() == 0 && this.getSpeed().getVy() == 0) {
			this.getAnimation().setFrameState(EntityConfiguration.IDDLE);
		}
		
		
		if(p.getX() < 0)
		{
			p.setX(0);
			this.getSpeed().setVx(0);
		}
		else if(p.getX() + GameConfiguration.TILE_SIZE > GameConfiguration.COLUMN_COUNT * GameConfiguration.TILE_SIZE)
		{
			p.setX(GameConfiguration.COLUMN_COUNT * GameConfiguration.TILE_SIZE - GameConfiguration.TILE_SIZE);
			this.getSpeed().setVx(0);
		}
		
		if(p.getY() < 0)
		{
			p.setY(0);
			this.getSpeed().setVy(0);
		}
		else if(p.getY() + GameConfiguration.TILE_SIZE > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT)
		{
			p.setY(GameConfiguration.LINE_COUNT * GameConfiguration.TILE_SIZE - GameConfiguration.TILE_SIZE);
			this.getSpeed().setVy(0);
		}
		
		if(this.getTarget() != null && this.getTarget().getFaction() != this.getFaction() && this.getCurrentAction() != EntityConfiguration.HARVEST)
		{
			if(Collision.collideAttack(this.getTarget(), this))
			{
				this.checkTarget();
				//System.out.println(this.getTarget());
				this.getSpeed().reset();
				this.attack(this.getDamage());
				//System .out.println("timer: " + this.timer);
				
				if(this.getTarget() == null)
				{
					this.setCurrentAction(EntityConfiguration.IDDLE);
					this.timer = this.attackSpeed;
				}
				else
				{
					this.setCurrentAction(EntityConfiguration.ATTACK);
				}
			}
			else
			{
				this.setCurrentAction(EntityConfiguration.IDDLE);
				this.timer = this.attackSpeed;
			}
		}
		
		if(this.state == EntityConfiguration.AGRESSIF_STATE && this.getTarget() == null && this.targetUnit == null && (this.getFaction() == EntityConfiguration.BOT_FACTION || this.getDestination() == null ))
		{
			
			if(!units.isEmpty())
			{
				//int distanceUnit;
				
				for(Unit value: units)
				{
					//distanceUnit = calculate(value.getPosition());
					
					if(Collision.collideVision(value, this) && value.getFaction() != this.getFaction())
					{
						this.setFinalDestination(value.getPosition());
						this.setCurrentAction(EntityConfiguration.WALK);
						this.setTarget(value);
						this.setTargetUnit(value);
						
						/*this.targetUnit = value;
						this.setTarget(value);
						this.setDestination(new Position(value.getPosition().getX(), value.getPosition().getY()));
						this.calculateSpeed(value.getPosition());*/
					}
					
				}
			}
		}
		else if(this.state == EntityConfiguration.DEFENSIF_STATE && this.getTarget() == null && this.getDestination() == null && this.isHit())
		{
			if(!units.isEmpty())
			{	
				for(Unit value: units)
				{	
					if(Collision.collideVision(value, this) && value.getFaction() != this.getFaction() && value.getTarget() == this)
					{
						this.setFinalDestination(value.getPosition());
						this.setCurrentAction(EntityConfiguration.WALK);
						this.setTarget(value);
						this.setTargetUnit(value);
						
						break;
					}
					
				}
			}
		}
		
		/*if(this.getId() == EntityConfiguration.CAVALRY && this.getFaction() == EntityConfiguration.PLAYER_FACTION)
		{
			//System.out.println("Ma target est: " + this.getTarget());
			System.out.println("Ma targetUnir est: " + this.targetUnit);
			if(this.getTargetUnit() != null)
			System.out.println("La vie de ma target: " + this.targetUnit.getHp());
			
			System.out.println("Ma destination est : " + this.getDestination());
		}*/
		
		if(!GameConfiguration.debug_mod) {
			if(playerFog != null) {
				if(targetUnit != null) {
					Position targetPos = this.targetUnit.getPosition();
					FogCase[][] fog = playerFog.getDynamicFog();
					if(fog[targetPos.getY() / GameConfiguration.TILE_SIZE][targetPos.getX() / GameConfiguration.TILE_SIZE].getVisible() == false) {
						this.setTarget(null);
						targetUnit = null;
					}
				}
			}
		}
		
		manageState();
	}
	
	public int calculate(Position position)
	{
		return (int) Math.sqrt(Math.pow(position.getX() - this.getPosition().getX(), 2) + Math.pow(position.getY() - this.getPosition().getY(), 2));
	}
	
	/**
	 * Permets de modifier l'etat des troupes selon certains criteres
	 */
	public void manageState() {

		if(speed.getVx() == 0f && speed.getVy() == 0f && currentAction != EntityConfiguration.ATTACK && currentAction != EntityConfiguration.HARVEST && currentAction != EntityConfiguration.REPAIR) {
			this.getAnimation().setFrameState(EntityConfiguration.IDDLE);
		}
		else if(speed.getVx() != 0f || speed.getVy() != 0f) {
			this.getAnimation().setFrameState(EntityConfiguration.WALK);
		}
		else if(currentAction == EntityConfiguration.ATTACK || currentAction == EntityConfiguration.HARVEST || currentAction == EntityConfiguration.REPAIR) {
			this.getAnimation().setFrameState(EntityConfiguration.ATTACK);
		}
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public int getTimer()
	{
		return this.timer;
	}
	
	public void setTimer(int timer)
	{
		this.timer = timer;
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public void setTargetUnit(Unit targetUnit)
	{
		this.targetUnit = targetUnit;
	}
	public Unit getTargetUnit()
	{
		return this.targetUnit;
	}
}