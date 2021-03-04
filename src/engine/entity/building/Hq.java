package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForWorker;

public class Hq extends ProductionBuilding{

	private ForWorker worker;
	
	public Hq(Position position, ForWorker worker, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.worker = worker;
		this.setProductionId(EntityConfiguration.WORKER);
	}
	
	public void upgradeTier() {
		
	}

	@Override
	public int produce() {
		/*Unit u;
		
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
		
		return u;*/
		
		int id = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(worker.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return id;
	}

	@Override
	public void startProd(int id) {
		/*Unit u = null;

		if(id == EntityConfiguration.WORKER) {
			System.out.println("starting worker production");
			if(this.getDestination() == null) {
				u = new Unit(worker.getHp() , 0, worker.getAttackRange(), worker.getMaxSpeed(), worker.getDamage(), worker.getRange(), worker.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, worker.getDescription(), worker.getHpMax(), this.getFaction());
			}
			else {
				u = new Unit(worker.getHp() , 0, worker.getAttackRange(), worker.getMaxSpeed(), worker.getDamage(), worker.getRange(), worker.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, worker.getDescription(), this.getDestination(), worker.getHpMax(), this.getFaction());
				u.calculateSpeed(getDestination());
			}
		}
		else {
			System.out.println("Invalid id");
		}
		this.getElementCount().add(u);
		if(this.getIsProducing() == false) {
			this.setTimer(worker.getTimeToBuild());
			this.setIsProducing(true);
		}*/
		
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(worker.getTimeToBuild());
			this.setIsProducing(true);
		}
	}
	
}
