package engine.map;

public class FogCase {
	private boolean visible;
	private boolean locked;
	
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
