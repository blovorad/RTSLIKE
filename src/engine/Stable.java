package engine;

import factionConfiguration.ForUnit;

public class Stable extends Building{
	
	private ForUnit cavalry;
	
	public Stable(Position position, ForUnit cavalry) {
		super(position);
		this.cavalry = cavalry;
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
