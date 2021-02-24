package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Castle extends Building{

	private ForUnit special;
	
	public Castle(Position position, ForUnit special) {
		super(position);
		this.special = special;
		this.setProductionId(EntityConfiguration.SPECIAL_UNIT);
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
	public void startProd(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
