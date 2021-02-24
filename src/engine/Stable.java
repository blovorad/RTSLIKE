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
	public Unit produce() {
		// TODO Auto-generated method stub
		Unit u = null;
		return u;
	}

	@Override
	public void attak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookForTarget(Position position, int range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StartProd(int id) {
		// TODO Auto-generated method stub
		
	}

}
