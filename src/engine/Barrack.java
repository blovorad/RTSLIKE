package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Barrack extends Building{

	private ForUnit infantry;
	
	public Barrack(Position position, ForUnit infantry) {
		super(position);
		this.infantry = infantry;
		this.setProductionId(EntityConfiguration.INFANTRY);
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
