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
