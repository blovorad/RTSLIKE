package engine;

public class Tower extends Building{

	public Tower(Position position, int id, String description, int hpMax) {
		super(position, id, description, hpMax);
		this.setProductionId(-1);
		this.setCanAttak(true);
		this.setDamage(10);
		this.setAttakRange(20);
		this.setAttackSpeed(10);
		this.setAttackCooldown(this.getAttackSpeed());
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
