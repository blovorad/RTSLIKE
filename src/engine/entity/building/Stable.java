package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Stable extends ProductionBuilding{
	
	private ForFighter cavalry;
	
	public Stable(Position position, ForFighter cavalry, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile, null);
		this.cavalry = cavalry;
		this.setProductionId(EntityConfiguration.CAVALRY);
	}
	
	public void addUnit() {
		
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
			this.setTimer(cavalry.getTimeToBuild());
		}
		System.out.println("producing cavalry final");
		
		return id;
	}

	@Override
	public void startProd(int id) {
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(cavalry.getTimeToBuild());
			this.setIsProducing(true);
		}
	}

}
