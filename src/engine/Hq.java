package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Hq extends Building{

	private ForUnit worker;
	
	public Hq(Position position, ForUnit worker) {
		super(position);
		this.worker = worker;
		this.setProductionId(EntityConfiguration.WORKER);
	}
	
	public void update()
	{
		
	}
	
	public void addUnit() {
		
	}
	
	public void upgradeTier() {
		
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
