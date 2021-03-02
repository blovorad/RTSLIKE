package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.ProductionBuilding;
import engine.Unit;
import factionConfiguration.ForUnit;

public class Castle extends ProductionBuilding{

	private ForUnit special;
	
	public Castle(Position position, ForUnit special, int id, String description, int hpMax) {
		super(position, id, description, hpMax);
		this.special = special;
		this.setProductionId(EntityConfiguration.SPECIAL_UNIT);
		/*this.setCanAttak(false);
		this.setCanHeal(true);
		this.setDamage(10);
		this.setAttakRange(80);
		this.setAttackSpeed(10);
		this.setAttackCooldown(this.getAttackSpeed());*/
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
				u = new Unit(special.getHp(), 0, special.getAttackRange(), special.getMaxSpeed(), special.getDamage(), special.getRange(), special.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, special.getDescription(), special.getHpMax());
			}
			else {
				u = new Unit(special.getHp(), 0, special.getAttackRange(), special.getMaxSpeed(), special.getDamage(), special.getRange(), special.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, special.getDescription(), this.getDestination(), special.getHpMax());
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
