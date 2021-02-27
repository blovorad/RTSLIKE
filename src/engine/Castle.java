package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Castle extends Building{

	private ForUnit special;
	
	public Castle(Position position, ForUnit special, int id, String description) {
		super(position, id, description);
		this.special = special;
		this.setProductionId(EntityConfiguration.SPECIAL_UNIT);
	}

	@Override
	public Unit produce() {
		Unit u;
		
		u = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(special.getTimeToBuild());
		}
		System.out.println("producing special final");
		
		return u;
	}

	@Override
	public void startProd(int id) {
		Unit u = null;
		
		if(id == EntityConfiguration.SPECIAL_UNIT) {
			System.out.println("starting special unit production");
			if(this.getDestination() == null) {
				u = new Unit(special.getHp(), 0, special.getAttackRange(), special.getMaxSpeed(), special.getDamage(), special.getRange(), special.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, special.getDescription());
			}
			else {
				u = new Unit(special.getHp(), 0, special.getAttackRange(), special.getMaxSpeed(), special.getDamage(), special.getRange(), special.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, special.getDescription(), this.getDestination());
				u.calculateSpeed(getDestination());
			}
		}
		else {
			System.out.println("Invalid id");
		}
		this.getElementCount().add(u);
		this.setTimer(special.getTimeToBuild());
		this.setIsProducing(true);
	}
	
}
