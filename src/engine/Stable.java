package engine;

import configuration.EntityConfiguration;
import factionConfiguration.ForUnit;

public class Stable extends Building{
	
	private ForUnit cavalry;
	
	public Stable(Position position, ForUnit cavalry, int id) {
		super(position, id);
		this.setCavalry(cavalry);
		this.setProductionId(EntityConfiguration.CAVALRY);
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
			this.setTimer(cavalry.getTimeToBuild());
		}
		System.out.println("producing cavalry final");
		
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

	public ForUnit getCavalry() {
		return cavalry;
	}

	public void setCavalry(ForUnit cavalry) {
		this.cavalry = cavalry;
	}

	@Override
	public void startProd(int id) {
		Unit u = null;
		
		if(id == EntityConfiguration.CAVALRY) {
			System.out.println("starting cavalry production");
			u = new Unit(cavalry.getHp(), 0, cavalry.getAttackRange(), cavalry.getMaxSpeed(), cavalry.getDamage(), cavalry.getRange(), cavalry.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, cavalry.getDescription());
		}
		else {
			System.out.println("Invalid id");
		}
		this.getElementCount().add(u);
		this.setTimer(cavalry.getTimeToBuild());
		this.setIsProducing(true);
	}

}
