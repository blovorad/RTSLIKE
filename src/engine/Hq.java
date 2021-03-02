package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Hq extends Building{

	private ForUnit worker;
	
	public Hq(Position position, ForUnit worker, int id, String description, int hpMax) {
		super(position, id, description, hpMax);
		this.worker = worker;
		this.setProductionId(EntityConfiguration.WORKER);
	}
	
	public void upgradeTier() {
		
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
			this.setTimer(worker.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return u;
	}

	@Override
	public void startProd(int id) {
		Unit u = null;

		if(id == EntityConfiguration.WORKER) {
			System.out.println("starting worker production");
			if(this.getDestination() == null) {
				u = new Unit(worker.getHp() , 0, worker.getAttackRange(), worker.getMaxSpeed(), worker.getDamage(), worker.getRange(), worker.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, worker.getDescription(), worker.getHpMax());
			}
			else {
				u = new Unit(worker.getHp() , 0, worker.getAttackRange(), worker.getMaxSpeed(), worker.getDamage(), worker.getRange(), worker.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, worker.getDescription(), this.getDestination(), worker.getHpMax());
				u.calculateSpeed(getDestination());
			}
		}
		else {
			System.out.println("Invalid id");
		}
		this.getElementCount().add(u);
		this.setTimer(worker.getTimeToBuild());
		this.setIsProducing(true);
	}
	
}
