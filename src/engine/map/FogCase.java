package engine.map;

import engine.Entity;
/**
 * 
 * @author gautier
 *
 */
public class FogCase {
	private boolean visible;
	private boolean locked;
	private Entity lockerEntity;
	
	public FogCase() {
		this.visible = false;
		this.locked = false;
		this.lockerEntity = null;
	}
	
	public void checkFogCase() {
		if(lockerEntity.getHp() <= 0) {
			lockerEntity = null;
			locked = false;
		}
	}
	
	public void lockedCaseFog(Entity entity) {
		this.lockerEntity = entity;
		this.locked = true;
	}
	
	public boolean getLocked() {
		return this.locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
