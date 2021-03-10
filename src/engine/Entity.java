package engine;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;

public class Entity 
{
	private int hp;
	private int hpMax;
	private String description;
	private Position position;
	private Entity target;
	private Position destination;
	private int faction;
	private int id;
	private boolean isHit;
	private int timerHit;
	private BufferedImage texture;
	private int sightRange;
	
	public Entity(int hp, int hpMax, String description, Position position, int id, int faction, BufferedImage texture, int sightRange)
	{
		this.hp = hp;
		this.hpMax = hpMax;
		this.description = description;
		this.position = position;
		this.id = id;
		this.target = null;
		this.destination = null;
		this.setFaction(faction);
		this.texture = texture;
		this.setSightRange(sightRange);
	}
	
	public Entity(int hp, String description, Position position, Position destination, int id, int faction)
	{
		this.hp = hp;
		this.description = description;
		this.position = position;
		this.id = id;
		this.destination = destination;
		this.target = null;
		this.setFaction(faction);
	}
	
	public void update()
	{
		if(this.isHit) {
			timerHit--;
			if(timerHit < 0) {
				this.setHit(false);
			}
		}
	}
	
	public void damage(int damage)
	{
		this.setHp(this.getHp() - damage);
		this.setHit(true);
		this.setTimerHit(EntityConfiguration.MAX_TIME_HIT_ANIMATION);
	}

	public void heal(int hp)
	{
		this.setHp(this.getHp() + hp);
		if(this.getHp() > this.getHpMax())
		{
			this.setHp(this.getHpMax());
		}
	}
	
	//getter & setter
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public Position getDestination() {
		return destination;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	public int getFaction() {
		return faction;
	}

	public void setFaction(int faction) {
		this.faction = faction;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

	public int getTimerHit() {
		return timerHit;
	}

	public void setTimerHit(int timerHit) {
		this.timerHit = timerHit;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public int getSightRange() {
		return sightRange;
	}

	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
	}
}
