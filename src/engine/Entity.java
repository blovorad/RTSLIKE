package engine;

import configuration.EntityConfiguration;
import engine.manager.GraphicsManager;
/**
 * 
 * @author gautier
 *
 */
public class Entity 
{
	private int hp;
	private int hpMax;
	private int sightRange;
	private String description;
	
	private Position position;
	private Entity target;
	private Position destination;
	
	private int faction;
	private int id;
	
	private boolean isHit;
	private int timerHit;
	
	private boolean selected;
	private boolean remove;
	
	private Animation animation;
	
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
		if(hp <= 0) {
			this.remove = true;
		}

		animation.update();
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
