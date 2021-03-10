package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Archery extends ProductionBuilding{

	private ForFighter archer;
	
	public Archery(Position position, ForFighter archer, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile, null);
		this.archer = archer;
		this.setProductionId(EntityConfiguration.ARCHER);
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
			this.setTimer(archer.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return id;
	}

	@Override
	public int startProd(int id, int moneyCount) {
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(archer.getTimeToBuild());
			this.setIsProducing(true);
		}
		return 0;
	}
}
