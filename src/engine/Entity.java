package engine;

import configuration.EntityConfiguration;
import engine.manager.GraphicsManager;
/**
 * 
 *	this class is core this is the first class of all entity of the game
 *	like fighter, worker, building and ressource
 *	not abstract because we need it to draw all entity
 *	@author gautier
 */
public class Entity 
{
	/**
	 * hp of entity
	 */
	private int hp;
	/**
	 * max hp entity can have
	 */
	private int hpMax;
	/**
	 * line of sight of entity
	 */
	private int sightRange;
	/**
	 * description of entity use to print on panel
	 */
	private String description;
	
	/**
	 * position of entity
	 */
	private Position position;
	/**
	 * if have a target
	 */
	private Entity target;
	/**
	 * destination use to move
	 */
	private Position destination;
	
	/**
	 * faction of the entity
	 */
	private int faction;
	/**
	 * id if this is a building, ressource, unit
	 */
	private int id;
	
	/**
	 * if entity getting damage
	 */
	private boolean isHit;
	/**
	 * how many before can be hit again
	 */
	private int timerHit;
	
	/**
	 * if entity is selected
	 */
	private boolean selected;
	/**
	 * if we need to remove it because hp < 0
	 */
	private boolean remove;
	
	/**
	 * stock the animation
	 */
	private Animation animation;
	
	/**
	 * constructor of entity
	 * @param hp hp of entity
	 * @param hpMax hpMax of entity
	 * @param description description use on panel
	 * @param position current position of entity
	 * @param id id of the entity
	 * @param faction faction of the entity
	 * @param sightRange sightRange of the entity
	 * @param maxFrame max frame use to this entity
	 * @param graphicsManager need to create animation
	 */
	public Entity(int hp, int hpMax, String description, Position position, int id, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{
		this.hp = hp;
		this.hpMax = hpMax;
		this.description = description;
		this.position = position;
		this.id = id;
		this.target = null;
		this.destination = null;
		this.setFaction(faction);
		this.setSightRange(sightRange);
		this.setSelected(false);
		if(maxFrame == 0) {
			this.animation = new Animation(maxFrame, false, graphicsManager, id, faction);
		}
		else {
			this.animation = new Animation(maxFrame, true, graphicsManager, id, faction);
		}
		this.setRemove(false);
	}
	
	/**
	 * core function of all entity it regards if the entity no need to be remove
	 * or if timerHit is 0
	 */
	public void update()
	{
		if(this.isHit) {
			timerHit--;
			if(timerHit < 0) {
				this.setHit(false);
			}
		}
		if(hp <= 0) {
			this.setSelected(false);
			this.remove = true;
		}

		animation.update();
	}
	
	/**
	 * active if the entity is getting damage
	 * @param damage number of damage soustract with hp
	 */
	public void damage(int damage)
	{
		this.setHp(this.getHp() - damage);
		this.setHit(true);
		this.setTimerHit(EntityConfiguration.MAX_TIME_HIT_ANIMATION);
	}
	
	/**
	 * active if the entity is getting heal
	 * @param hp number of hp add with hp
	 */
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

	public int getSightRange() {
		return sightRange;
	}

	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
}