package engine.entity.building;

import engine.Position;
import engine.entity.unit.Unit;
import engine.map.Tile;

public class Forge extends ProductionBuilding{

	public Forge(Position position, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.setProductionId(-1);
	}

	@Override
	public Unit produce() {
		// TODO Auto-generated method stub
		Unit u = null;
		return u;
	}

	@Override
	public void startProd(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
