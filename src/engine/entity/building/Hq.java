package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForWorker;

public class Hq extends ProductionBuilding{

	private ForWorker worker;
	
	public Hq(Position position, ForWorker worker, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.worker = worker;
		this.setProductionId(EntityConfiguration.WORKER);
	}
	
	public void upgradeTier() {
		
	}

	@Override
	public int produce() {		
		int id = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(worker.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return id;
	}

	@Override
	public void startProd(int id) {		
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(worker.getTimeToBuild());
			this.setIsProducing(true);
		}
	}
	
}
