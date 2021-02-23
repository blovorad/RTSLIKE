package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Stable extends Building{
	
	private ForUnit cavalry;
	
	public Stable(Position position, ForUnit cavalry) {
		super(position);
		this.setCavalry(cavalry);
		this.setProductionId(EntityConfiguration.CAVALRY);
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

	public ForUnit getCavalry() {
		return cavalry;
	}

	public void setCavalry(ForUnit cavalry) {
		this.cavalry = cavalry;
	}

}
