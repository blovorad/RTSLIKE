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
	private List<Position> lst_destinations = new ArrayList<Position>();
	private Position destination;
	private Entity target;
	
	private int currentAction;
	private int attackRange;
	private int attackSpeed;
	private int maxSpeed;
	private int damage;
	private int armor;
	private int timer;
	private int unitSize;
	
	private Speed speed;
	private boolean generatePath;
	private boolean foundPath;
	
	public Unit(int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int armor, Position position, int id, String description, Position destination, int hpMax, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{
		super(hp, hpMax, description, position, id, faction, sightRange, maxFrame, graphicsManager);
		
		this.currentAction = EntityConfiguration.IDDLE;
		this.attackRange = attackRange;
		this.setAttackSpeed(attackSpeed);
		this.maxSpeed = maxSpeed;
		this.damage = damage;
		this.armor = armor;
		this.destination = destination;
		this.speed = new Speed(0, 0);
		this.timer = this.attackSpeed;
		this.finalPosition = null;
		this.generatePath = false;
		this.target = null;
		
		if(id == EntityConfiguration.CAVALRY) {
			unitSize = EntityConfiguration.CAVALRY_SIZE;
		}
		else {
			unitSize = EntityConfiguration.UNIT_SIZE;
		}
		
		if(destination != null) {
			//System.out.println("calcul");
			this.setFinalDestination(destination);
		}
	}
	
	public void setFinalDestination(Position position) {
		//System.out.println(" this " + this);
		if(position == null) {
			this.setDestination(null);
			this.finalNode = null;
			this.finalPosition = null;
			this.lst_destinations.clear();
			this.getSpeed().reset();
		}
		else {
			this.getSpeed().reset();
			this.finalPosition = new Position(position.getX(), position.getY());
			finalNode = new Node(new Position(position.getX() / GameConfiguration.TILE_SIZE, position.getY() / GameConfiguration.TILE_SIZE));
			Node currentNode = new Node(new Position(this.getPosition().getX() / GameConfiguration.TILE_SIZE, this.getPosition().getY() / GameConfiguration.TILE_SIZE));
			if(currentNode.getPosition().equals(finalNode.getPosition())) {
				this.calculateSpeed(position);
				this.finalNode = null;
				this.finalPosition = null;
				generatePath = false;
			}
			else {
				this.setDestination(null);
				generatePath = true;
			}
		}
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
			this.lst_destinations.clear();
			this.finalPosition = null;
			this.finalNode = null;
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
	
	/**
	 * method who calculate a speed with atan2
	 * @param p where you need to move
	 */
	public void calculateSpeed(Position p){	
		this.setDestination(new Position(p.getX(), p.getY()));
		double angle = Math.atan2( (p.getY() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getY() + GameConfiguration.TILE_SIZE /2), (p.getX() + GameConfiguration.TILE_SIZE /2) - (this.getPosition().getX() + GameConfiguration.TILE_SIZE));
		this.move((float)(this.maxSpeed * Math.cos(angle)), (float)(this.maxSpeed * Math.sin(angle)));
	}

	public boolean isCloser(Position asking1, Position compared, Position wanted){
		int x1 = Math.abs(asking1.getX() - wanted.getX());
		int y1 = Math.abs(asking1.getY() - wanted.getY());
		int x2 = Math.abs(compared.getX() - wanted.getX());
		int y2 = Math.abs(compared.getY() - wanted.getY());
		if(x1 + y1 < x2 + y2){
			return true;
		}
		return false;
	}
	

	/**
	 * core function of A* algorithm that make unit know where to go
	 * @param map of the game
	 */
	public boolean generatePath(Map map) {
		Position p = this.getPosition();
		Path path = new Path();
		Node currentNode = new Node(new Position((p.getX() + EntityConfiguration.UNIT_SIZE / 2) / GameConfiguration.TILE_SIZE, (p.getY() + EntityConfiguration.UNIT_SIZE / 2) / GameConfiguration.TILE_SIZE));
		if(this.targetUnit != null){
			Position target = new Position(targetUnit.getPosition().getX(), targetUnit.getPosition().getY());
			target.setX((target.getX() + EntityConfiguration.UNIT_SIZE / 2) / GameConfiguration.TILE_SIZE);
			target.setY((target.getY() + EntityConfiguration.UNIT_SIZE / 2) / GameConfiguration.TILE_SIZE);
			Position closerNode = this.getPosition();
			if(lst_destinations.isEmpty() == false){
				closerNode = lst_destinations.get(0);
			}
			for(Position pos : lst_destinations){
				if(isCloser(closerNode, pos, target)){
					closerNode = pos;
					currentNode.setPosition(pos);
				}
			}
		}
		List<Node> nodes = new ArrayList<Node>();
		Tile[][] tiles = map.getTiles();
		
		int sightRangeX = (p.getX() - this.getSightRange() / 3) / GameConfiguration.TILE_SIZE;
		int sightRangeY = (p.getX() - this.getSightRange() / 3) / GameConfiguration.TILE_SIZE;
		if(sightRangeX < 0) {
			sightRangeX = 0;
		}
		else if(sightRangeX >= GameConfiguration.COLUMN_COUNT) {
			sightRangeX = GameConfiguration.COLUMN_COUNT - 1;
		}
		if(sightRangeY < 0) {
			sightRangeY = 0;
		}
		else if(sightRangeY >= GameConfiguration.LINE_COUNT) {
			sightRangeY = GameConfiguration.LINE_COUNT - 1;
		}
		int sightRangeW = (sightRangeX + this.getSightRange()) / GameConfiguration.TILE_SIZE;
		int sightRangeH = (sightRangeY + this.getSightRange()) / GameConfiguration.TILE_SIZE;
		if(sightRangeW >= GameConfiguration.COLUMN_COUNT) {
			sightRangeW = GameConfiguration.COLUMN_COUNT - 1;
		}
		if(sightRangeH >= GameConfiguration.LINE_COUNT) {
			sightRangeH = GameConfiguration.LINE_COUNT - 1;
		}
		
		Node endNode = finalNode;
		int distance = Math.abs(finalNode.getPosition().getX() - p.getX() / GameConfiguration.TILE_SIZE) + Math.abs(finalNode.getPosition().getY() - p.getY() / GameConfiguration.TILE_SIZE);
		int sightRangeInTile = sightRangeX + sightRangeW - sightRangeX;
		
		//si distance trop lointaine cherche a decoupe le chemin pour eviter un calcul trop direct du chemin et donc un gros lag
		if(distance > sightRangeInTile) {
			boolean found = false;
			int pathDivide;
			int diviseur = 1;
			int distanceDivision = distance;
			while(diviseur < distanceDivision) {
				distanceDivision = distance;
				distanceDivision /= diviseur;
				diviseur++;
			}
			pathDivide = diviseur;
			while(found == false && pathDivide > 0) {
				int midX = Math.abs(finalNode.getPosition().getX() - p.getX() / GameConfiguration.TILE_SIZE) / pathDivide;
				int midY = Math.abs(finalNode.getPosition().getY() - p.getY() / GameConfiguration.TILE_SIZE) / pathDivide;
				if(p.getX() / GameConfiguration.TILE_SIZE < finalNode.getPosition().getX()) {
					midX += p.getX() / GameConfiguration.TILE_SIZE;
				}
				else {
					midX += finalNode.getPosition().getX();
				}
				if(p.getY() / GameConfiguration.TILE_SIZE < finalNode.getPosition().getY()) {
					midY += p.getY() / GameConfiguration.TILE_SIZE;
				}
				else {
					midY += finalNode.getPosition().getY();
				}
				if(tiles[midY][midX].isSolid() == false) {
					endNode = new Node(new Position(midX, midY));
					while(endNode.getPosition().equals(currentNode.getPosition())) {
						/*if(midY - 2 >= 0 && tiles[midY - 2][midX].isSolid() == false) {
							endNode = new Node(new Position(midX, midY - 2));
						}
						else if(midY + 2 < GameConfiguration.LINE_COUNT && tiles[midY + 2][midX].isSolid() == false){
							endNode = new Node(new Position(midX, midY + 2));
						}*/
						if(midX - 2 >= 0 && tiles[midY][midX - 2].isSolid() == false) {
							endNode = new Node(new Position(midX - 2, midY));
						}
						else if(midX + 2 < GameConfiguration.COLUMN_COUNT && tiles[midY][midX + 2].isSolid() == false) {
							endNode = new Node(new Position(midX + 2, midY));
						}
					}
					found = true;
				}
				else {
					pathDivide--;
				}
			}
		}
		else {
			if(tiles[finalNode.getPosition().getY()][finalNode.getPosition().getX()].isSolid() == true && this.getTarget() == null && this.getCurrentAction() != EntityConfiguration.HARVEST) {
				currentNode = null;
			}
		}
		
		if(this.getCurrentAction() == EntityConfiguration.HARVEST && (finalNode.getPosition().getX() - 1 > 0 && tiles[finalNode.getPosition().getY()][finalNode.getPosition().getX() - 1].isSolid() == true)
			&& (finalNode.getPosition().getX() + 1 < GameConfiguration.COLUMN_COUNT && tiles[finalNode.getPosition().getY()][finalNode.getPosition().getX() + 1].isSolid())
			&& (finalNode.getPosition().getY() - 1 > 0 && tiles[finalNode.getPosition().getY() - 1][finalNode.getPosition().getX()].isSolid())
			&& (finalNode.getPosition().getY() + 1 < GameConfiguration.LINE_COUNT && tiles[finalNode.getPosition().getY() + 1][finalNode.getPosition().getX()].isSolid())) {
			currentNode = null;
		}
		
		boolean searchingPath = false;
		while(currentNode != null && endNode != null && !currentNode.getPosition().equals(endNode.getPosition())) {
			searchingPath = true;
			Position bis = currentNode.getPosition();
			if(bis.getY() - 1 >= 0 && bis.getX() >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() < GameConfiguration.COLUMN_COUNT) {
				if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
					if(tiles[bis.getY() - 1][bis.getX()].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX(), bis.getY() - 1))) {
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
					if(tiles[bis.getY()][bis.getX() - 1].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX() - 1, bis.getY()))) {
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
					if(tiles[bis.getY()][bis.getX() + 1].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX() + 1, bis.getY()))) {
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
					if(tiles[bis.getY() + 1][bis.getX()].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX(), bis.getY() + 1))) {
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
			if(bis.getY() + 1 >= 0 && bis.getX() + 1 >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
				if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
					if(tiles[bis.getY() + 1][bis.getX() + 1].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX() + 1, bis.getY() + 1))) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() + 1));
						nodes.add(node);
					}
				}
				else {
					if(tiles[bis.getY() + 1][bis.getX() + 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() + 1));
						nodes.add(node);
					}
				}
			}
			
			if(bis.getY() + 1 >= 0 && bis.getX() - 1 >= 0 && bis.getY() + 1 < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
				if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
					if(tiles[bis.getY() + 1][bis.getX() - 1].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX() - 1, bis.getY() + 1))) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() + 1));
						nodes.add(node);
					}
				}
				else {
					if(tiles[bis.getY() + 1][bis.getX() - 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() + 1));
						nodes.add(node);
					}
				}
			}
			
			if(bis.getY() - 1 >= 0 && bis.getX() - 1 >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() - 1 < GameConfiguration.COLUMN_COUNT) {
				if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
					if(tiles[bis.getY() - 1][bis.getX() - 1].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX() - 1, bis.getY() - 1))) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
				else {
					if(tiles[bis.getY() - 1][bis.getX() - 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() - 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
			}
			
			if(bis.getY() - 1 >= 0 && bis.getX() + 1 >= 0 && bis.getY() - 1 < GameConfiguration.LINE_COUNT && bis.getX() + 1 < GameConfiguration.COLUMN_COUNT) {
				if(this.getCurrentAction() == EntityConfiguration.HARVEST || this.getTarget() != null) {
					if(tiles[bis.getY() - 1][bis.getX() + 1].isSolid() == false || endNode.getPosition().equals(new Position(bis.getX() + 1, bis.getY() - 1))) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
				else {
					if(tiles[bis.getY() - 1][bis.getX() + 1].isSolid() == false) {
						Node node = new Node(new Position(bis.getX() + 1, bis.getY() - 1));
						nodes.add(node);
					}
				}
			}
				
			currentNode = path.generatePath(nodes, endNode, currentNode);
			nodes.clear();
		}
		
		generatePath = false;
		if(currentNode != null) {
			if(searchingPath) {
				lst_destinations.clear();
				this.destination = null;
				lst_destinations = path.reversePath(currentNode);
			}
			else {
				this.calculateSpeed(this.finalPosition);
			}
			return true;
		}
		else {
			this.speed.reset();
			finalPosition = null;
			finalNode = null;
			this.setDestination(null);
			lst_destinations.clear();
			return false;
		}
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
		Position wanted = new Position(p.getX() + unitSize / 2, p.getY() + unitSize / 2);
		
		if(generatePath) {
			if(finalNode != null) {
				foundPath = generatePath(map);
			}
			//System.out.println("adresse : " + this + ", generation path : " + generatePath + " noeudFinale : " + finalNode);
			if(lst_destinations.isEmpty() == true) {
				if(this.getCurrentAction() == EntityConfiguration.HARVEST) {
					currentAction = EntityConfiguration.IDDLE;
				}
			}
		}
		
		if(targetUnit != null) {
			if(Collision.collideVision(targetUnit, this) == true && Collision.collideAttack(targetUnit, this) == false) {
				if(lst_destinations.isEmpty() == true){
					this.calculateSpeed(targetUnit.getPosition());
				}
				else if(finalPosition != null && !finalPosition.equals(targetUnit.getPosition())) {
					this.setFinalDestination(targetUnit.getPosition());
				}
			}
		}
		
		if(finalNode != null && finalPosition != null) {
			if(this.getDestination() == null) {
				this.calculateSpeed(lst_destinations.get(0));
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
						if(lst_destinations.isEmpty() == false) {
							lst_destinations.remove(0);
						}
						if(lst_destinations.isEmpty() == false) {
							this.calculateSpeed(lst_destinations.get(0));
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
								this.setFinalDestination(finalPosition);
							}
						}
					}
				}
				else {
					if(lst_destinations.isEmpty() == false) {
						lst_destinations.remove(0);
					}
					if(lst_destinations.isEmpty() == false) {
						this.calculateSpeed(lst_destinations.get(0));
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
							this.setFinalDestination(finalPosition);
						}
					}
				}
			}
		}
		else if(finalNode == null && this.getDestination() != null) {
			if(!wanted.equals(this.getDestination())){
				if( (wanted.getX()  < this.getDestination().getX() && speed.getVx() < 0) || (wanted.getX() > this.getDestination().getX() && speed.getVx() > 0) ){
					p.setX(this.getDestination().getX() - unitSize / 2);
					speed.setVx(0);
				}
				if((wanted.getY() < this.getDestination().getY() && speed.getVy() < 0) || (wanted.getY() > this.getDestination().getY() && speed.getVy() > 0) ){
					p.setY(this.getDestination().getY() - unitSize / 2);
					speed.setVy(0);
				}
				if( wanted.equals(this.getDestination())){
					this.setDestination(null);
				}
			}
			else {
				this.speed.reset();
				this.setDestination(null);
			}
		}
	
		this.getPosition().setX(this.getPosition().getX() + (int)this.getSpeed().getVx());
		this.getPosition().setY(this.getPosition().getY() + (int)this.getSpeed().getVy());
		
		if((this.getAnimation().getFrameState() == EntityConfiguration.WALK || this.getAnimation().getFrameState() == EntityConfiguration.ATTACK) && this.getSpeed().getVx() == 0 && this.getSpeed().getVy() == 0) {
			this.getAnimation().setFrameState(EntityConfiguration.IDDLE);
		}
		
		
		if(p.getX() < 0){
			p.setX(0);
			this.getSpeed().setVx(0);
		}
		else if(p.getX() + GameConfiguration.TILE_SIZE > GameConfiguration.COLUMN_COUNT * GameConfiguration.TILE_SIZE){
			p.setX(GameConfiguration.COLUMN_COUNT * GameConfiguration.TILE_SIZE - GameConfiguration.TILE_SIZE);
			this.getSpeed().setVx(0);
		}
		
		if(p.getY() < 0){
			p.setY(0);
			this.getSpeed().setVy(0);
		}
		else if(p.getY() + GameConfiguration.TILE_SIZE > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT){
			p.setY(GameConfiguration.LINE_COUNT * GameConfiguration.TILE_SIZE - GameConfiguration.TILE_SIZE);
			this.getSpeed().setVy(0);
		}
		
		if(this.getTarget() != null && this.getTarget().getFaction() != this.getFaction() && this.getCurrentAction() != EntityConfiguration.HARVEST){
			if(Collision.collideAttack(this.getTarget(), this)){
				this.checkTarget();
				this.getSpeed().reset();
				this.attack(this.getDamage());
				
				if(this.getTarget() == null){
					this.setCurrentAction(EntityConfiguration.IDDLE);
					this.timer = this.attackSpeed;
				}
				else{
					this.setCurrentAction(EntityConfiguration.ATTACK);
				}
			}
			else{
				this.setCurrentAction(EntityConfiguration.IDDLE);
				this.timer = this.attackSpeed;
			}
		}
		
		if(!GameConfiguration.debug_mod) {
			if(playerFog != null) {
				if(targetUnit != null) {
					Position targetPos = this.targetUnit.getPosition();
					FogCase[][] fog = playerFog.getDynamicFog();
					if(fog[targetPos.getY() / GameConfiguration.TILE_SIZE][targetPos.getX() / GameConfiguration.TILE_SIZE].getVisible() == true) {
						this.setTarget(null);
						targetUnit = null;
					}
					else if(Collision.collideVision(targetUnit, this) == false){
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

		if(speed.getVx() == 0f && speed.getVy() == 0f) {
			if(currentAction == EntityConfiguration.ATTACK || currentAction == EntityConfiguration.HARVEST || currentAction == EntityConfiguration.REPAIR || currentAction == EntityConfiguration.CONSTRUCT) {
				this.getAnimation().setFrameState(EntityConfiguration.ATTACK);
			}
			else {
				this.getAnimation().setFrameState(EntityConfiguration.IDDLE);
			}
		}
		else if(speed.getVx() != 0f || speed.getVy() != 0f) {
			this.getAnimation().setFrameState(EntityConfiguration.WALK);
		}
	}
	
	public Position getDestination() {
		return this.destination;
	}
	
	public void setDestination(Position p) {
		this.destination = p;
	}
	
	public Entity getTarget() {
		return this.target;
	}
	
	public void setTarget(Entity entity) {
		this.target = entity;
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
	
	public void setTargetUnit(Unit targetUnit)
	{
		this.targetUnit = targetUnit;
	}
	public Unit getTargetUnit()
	{
		return this.targetUnit;
	}
	
	public Node getFinalNode() {
		return this.finalNode;
	}
	
	public List<Position> getLst_destinations(){
		return this.lst_destinations;
	}
}