package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForFighter;

public class Barrack extends Building{

	private ForFighter infantry;
	
	public Barrack(Position position, ForFighter infantry, int id, String description, int hpMax) {
		super(position, id, description, hpMax);
		this.infantry = infantry;
		this.setProductionId(EntityConfiguration.INFANTRY);
	}
	
	public void addUnit() {
		
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
			this.setTimer(infantry.getTimeToBuild());
		}
		System.out.println("producing infantry final");
		
		return u;
	}

	@Override
	public void startProd(int id) {
		Unit u = null;
		
		if(id == EntityConfiguration.INFANTRY) {
			System.out.println("starting infatry production");
			if(this.getDestination() == null) {
				u = new Unit(infantry.getHp(), 0, infantry.getAttackRange(), infantry.getMaxSpeed(), infantry.getDamage(), infantry.getRange(), infantry.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, infantry.getDescription(), infantry.getHpMax());
			}
			else {
				u = new Unit(infantry.getHp(), 0, infantry.getAttackRange(), infantry.getMaxSpeed(), infantry.getDamage(), infantry.getRange(), infantry.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, infantry.getDescription(), this.getDestination(), infantry.getHpMax());
				u.calculateSpeed(getDestination());
			}
		}
		else {
			System.out.println("Invalid id");
		}
		this.getElementCount().add(u);
		this.setTimer(infantry.getTimeToBuild());
		this.setIsProducing(true);
	}
	
}
