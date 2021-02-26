package engine;

public class Forge extends Building{

	public Forge(Position position, int id, String description) {
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
	public void attak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lookForTarget(Position position, int range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startProd(int id) {
		// TODO Auto-generated method stub
		
	}
	
}
