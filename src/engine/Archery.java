package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Archery extends Building{

	private ForUnit archer;
	
	public Archery(Position position, ForUnit archer) {
		super(position);
		this.archer = archer;
		this.setProductionId(EntityConfiguration.ARCHER);
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
	public void startProd(int id) {
		// TODO Auto-generated method stub
		
	}
}
