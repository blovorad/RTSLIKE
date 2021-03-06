package engine.map;

/**
 * 
 * this class is need to manage dynamic fog that represent each case(tile) of fog
 * @author gautier
 */
public class FogCase {
	/**
	 * if we see this case, true if we can't see this case
	 */
	private boolean visible;
	/**
	 * if the case is locked, already analyze
	 */
	private boolean locked;
	
	/**
	 * constructor
	 */
	public FogCase() {
		this.visible = false;
		this.locked = false;
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