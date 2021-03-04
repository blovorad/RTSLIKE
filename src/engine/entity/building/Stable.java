package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Stable extends ProductionBuilding{
	
	private ForFighter cavalry;
	
	public Stable(Position position, ForFighter cavalry, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.cavalry = cavalry;
		this.setProductionId(EntityConfiguration.CAVALRY);
	}
	
	public void addUnit() {
		
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
			this.setTimer(cavalry.getTimeToBuild());
		}
		System.out.println("producing cavalry final");
		
		return u;*/
		int id = this.getElementCount().get(0);
		
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(cavalry.getTimeToBuild());
		}
		System.out.println("producing cavalry final");
		
		return id;
	}

	@Override
	public void startProd(int id) {
		//Unit u = null;
		
		/*if(id == EntityConfiguration.CAVALRY) {
			System.out.println("starting cavalry production");
			if(this.getDestination() == null) {
				u = new Unit(cavalry.getHp(), 0, cavalry.getAttackRange(), cavalry.getMaxSpeed(), cavalry.getDamage(), cavalry.getRange(), cavalry.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, cavalry.getDescription(), cavalry.getHpMax(), this.getFaction());
		
			}
			else {
				u = new Unit(cavalry.getHp(), 0, cavalry.getAttackRange(), cavalry.getMaxSpeed(), cavalry.getDamage(), cavalry.getRange(), cavalry.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, cavalry.getDescription(), this.getDestination(), cavalry.getHpMax(), this.getFaction());
				u.calculateSpeed(getDestination());
			}
		}
		else {
			System.out.println("Invalid id");
		}*/
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(cavalry.getTimeToBuild());
			this.setIsProducing(true);
		}
	}

}
