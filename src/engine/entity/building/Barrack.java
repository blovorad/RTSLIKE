package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Barrack extends ProductionBuilding{

	private ForFighter infantry;
	
	public Barrack(Position position, ForFighter infantry, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.infantry = infantry;
		this.setProductionId(EntityConfiguration.INFANTRY);
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
			this.setTimer(infantry.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return id;
	}

	@Override
	public void startProd(int id) {
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(infantry.getTimeToBuild());
			this.setIsProducing(true);
		}
	}
	
}
