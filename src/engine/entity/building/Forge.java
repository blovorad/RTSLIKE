package engine.entity.building;

import engine.Position;
import engine.map.Tile;

public class Forge extends ProductionBuilding{

	public Forge(Position position, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.setProductionId(-1);
	}

	@Override
	public int produce() {
		// TODO Auto-generated method stub
		int u = 1;
		return u;
	}

	@Override
	public void startProd(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
