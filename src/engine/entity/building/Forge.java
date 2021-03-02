package engine.entity.building;

import engine.Position;
import engine.Unit;

public class Forge extends ProductionBuilding{

	public Forge(Position position, int id, String description, int hpMax) {
		super(position, id, description, hpMax);
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
