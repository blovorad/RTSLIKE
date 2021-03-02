package engine;

public class Tower extends Building{

	public Tower(Position position, int id, String description) {
		super(position, id, description);
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
