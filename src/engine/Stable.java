package engine;

import factionConfiguration.ForUnit;

public class Stable extends Building{
	
	private ForUnit horse;
	
	public Stable(Position position) {
		this.setPosition(position);
		this.setHp(100);
	}
	
	public void addUnit() {
		
	}

	@Override
	public void produce() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookForTarget(Position position, int range) {
		// TODO Auto-generated method stub
		
	}

}
